import dataclasses
import inspect
import typing
from dataclasses import fields
from datetime import date
from enum import Enum
from itertools import repeat
from typing import get_args, Optional

from flask import Blueprint, Flask

from .config import UIEndpoint, UIComponent, UIRequestType, UIField, UIFieldType, UIRequestBody, UIEnumField, \
    UIListField, UIClassField, UITextField, UINumberField, UIDateField


class UIComponentFactory:
    @staticmethod
    def create_component(blueprint: Blueprint) -> UIComponent:
        temp_app = Flask(__name__)
        temp_app.register_blueprint(blueprint)
        ui_endpoints = []
        for rule in temp_app.url_map.iter_rules():
            if rule.endpoint == 'static':
                continue
            mapping = rule.rule
            request_body, path_params, query_params, response_body = repeat(None, 4)
            func = temp_app.view_functions[rule.endpoint]
            if hasattr(func, 'display_name'):
                display_name = func.display_name
            else:
                display_name = rule.endpoint[len(blueprint.name) + 1:]
            if hasattr(func, 'request_body'):
                body_type = func.request_body
                ui_fields = UIFieldsFactory.create_fields(body_type)
                if hasattr(body_type, 'display_name'):
                    entity_name = body_type.display_name
                else:
                    entity_name = body_type.__name__
                request_body = UIRequestBody(entity_name=entity_name, fields=ui_fields)
            if hasattr(func, 'query_params'):
                query_params = []
                for param in func.query_params:
                    query_param = UIFieldsFactory.create_field(param['type'], param['display_name'],
                                                               param['submit_name'], param['options'])
                    query_params.append(query_param)
            if hasattr(func, 'response_body'):
                response_body = UIFieldsFactory.create_fields(func.response_body)
            func_params = inspect.signature(func).parameters
            if len(func_params) > 0:
                path_params = []
                param_display_names = []
                if hasattr(func, 'path_params'):
                    param_display_names = func.path_params
                for name, param in inspect.signature(func).parameters.items():
                    field_type = type(str) if param.annotation is None else param.annotation
                    param_info = next((x for x in param_display_names if x['submit_name'] == name), {})
                    display_name = param_info.get('display_name', None)
                    ui_field = UIFieldsFactory.create_field(field_type, display_name, name, param_info['options'])
                    mapping = mapping.replace(f'<{name}>', '{' + name + '}')
                    path_params.append(ui_field)
            for method in rule.methods:
                if method == 'OPTIONS' or method == 'PATCH' or method == 'HEAD':
                    continue
                ui_endpoint = UIEndpoint(name=display_name, mapping=mapping, request_type=UIRequestType[method],
                                         request_body=request_body,
                                         path_params=path_params,
                                         query_params=query_params,
                                         response_body=response_body)
                ui_endpoints.append(ui_endpoint)
        return UIComponent(name=blueprint.name, endpoints=ui_endpoints)


class UIFieldsFactory:
    @staticmethod
    def create_fields(dto: type) -> list[UIField]:
        if not dataclasses.is_dataclass(dto):
            raise ValueError(f'Only data classes are supported for UI generation, got {dto}')
        ui_fields = []
        for field in fields(dto):
            name = field.metadata.get('display_name')
            if name is None:
                name = field.name
            options = dict(field.metadata.items())
            ui_field = UIFieldsFactory.create_field(field.type, name, field.name, options)
            ui_fields.append(ui_field)
        return ui_fields

    @staticmethod
    def create_field(field_type: type, display_name: Optional[str], code_name: Optional[str],
                     options: Optional[dict[str, typing.Any]]) -> UIField:
        # Check if field is optional
        if hasattr(field_type, '__args__') and len(field_type.__args__) == 2 and field_type.__args__[-1] is type(None):
            required = False
            field_type = field_type.__args__[0]
        else:
            required = True
        if hasattr(field_type, '__args__') and len(field_type.__args__) == 2 and field_type.__args__[-1] is type(None):
            required = False
            field_type = field_type.__args__[0]
        if options is None:
            options = {}
        if issubclass(field_type, bool):
            return UIField(display_name=display_name, code_name=code_name, field_type=UIFieldType.BOOL,
                           required=required)
        if issubclass(field_type, str) and 'date_format' not in options:
            return UITextField(display_name=display_name, code_name=code_name, field_type=UIFieldType.TEXT,
                               required=required, pattern=options.get('pattern', None),
                               min_length=options.get('min_length', None),
                               max_length=options.get('max_length', None))
        if issubclass(field_type, int) or issubclass(field_type, float):
            return UINumberField(display_name=display_name, code_name=code_name, field_type=UIFieldType.NUMBER,
                                 required=required, min=options.get('min', None), max=options.get('max', None))
        if issubclass(field_type, date) or (issubclass(field_type, str) and 'date_format' in options):
            return UIDateField(display_name=display_name, code_name=code_name, field_type=UIFieldType.DATE,
                               required=required, date_format=options.get('date_format', 'yyyy-MM-dd'))
        if issubclass(field_type, Enum):
            values = dict()
            for value in field_type:
                values[value.name] = value.value
            return UIEnumField(display_name=display_name, code_name=code_name, field_type=UIFieldType.ENUM,
                               required=required, submit_to_display_values=values)
        if hasattr(field_type, '__iter__'):
            element_type = get_args(field_type)[0]
            element = UIFieldsFactory.create_field(element_type, None, None, None)
            return UIListField(display_name=display_name, code_name=code_name, field_type=UIFieldType.LIST,
                               required=required, element=element)
        else:
            if not dataclasses.is_dataclass(field_type):
                raise ValueError(f'Only data classes are supported for UI generation, got {field_type}')
            inner_fields = UIFieldsFactory.create_fields(field_type)
            return UIClassField(display_name=display_name, code_name=code_name, field_type=UIFieldType.CLASS,
                                required=required, inner_fields=inner_fields)
