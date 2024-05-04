import typing
from dataclasses import field
from typing import Optional


def display_name(value: str):
    def decorator(cls_or_func):
        cls_or_func.display_name = value
        return cls_or_func

    return decorator


def request_body(value: type):
    def decorator(func):
        func.request_body = value
        return func

    return decorator


def path_param(submit_name: str, display_name: str, **options: typing.Any):
    def decorator(func):
        if not hasattr(func, 'path_params'):
            func.path_params = []
        func.path_params.append({
            'submit_name': submit_name,
            'display_name': display_name,
            'validations': options
        })
        return func

    return decorator


def query_param(value: type, submit_name: str, display_name: Optional[str] = None, **options: typing.Any):
    def decorator(func):
        if not hasattr(func, 'query_params'):
            func.query_params = []
        func.query_params.append({
            'submit_name': submit_name,
            'display_name': submit_name if display_name is None else display_name,
            'type': value,
            'validations': options
        })
        return func

    return decorator


def response_body(value: type):
    def decorator(func):
        func.response_body = value
        return func

    return decorator


def ui_field(name: str, **options: typing.Any):
    options['display_name'] = name
    return field(metadata=options)
