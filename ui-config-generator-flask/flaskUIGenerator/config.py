from dataclasses import dataclass
from enum import Enum
from typing import Optional, Dict


class UIRequestType(Enum):
    GET = 1
    POST = 2
    PUT = 3
    DELETE = 4
    OTHER = 5


class UIFieldType(Enum):
    TEXT = 1
    NUMBER = 2
    BOOL = 3
    ENUM = 4
    LIST = 5
    CLASS = 6


@dataclass
class UIField:
    display_name: Optional[str]
    code_name: Optional[str]
    field_type: UIFieldType
    required: bool


@dataclass
class UINumberField(UIField):
    min: Optional[int]
    max: Optional[int]


@dataclass
class UITextField(UIField):
    pattern: Optional[str]
    min_length: Optional[int]
    max_length: Optional[int]


@dataclass
class UIEnumField(UIField):
    submit_to_display_values: Dict[str, str]


@dataclass
class UIListField(UIField):
    element: UIField


@dataclass
class UIClassField(UIField):
    inner_fields: list[UIField]


@dataclass
class UIRequestBody:
    entity_name: str
    fields: list[UIField]


@dataclass
class UIEndpoint:
    name: str
    mapping: str
    request_type: UIRequestType
    request_body: Optional[UIRequestBody]
    path_params: Optional[list[UIField]]
    query_params: Optional[list[UIField]]
    response_body: Optional[list[UIField]]


@dataclass
class UIComponent:
    name: str
    endpoints: list[UIEndpoint]


@dataclass
class UIConfig:
    base_url: str
    components: list[UIComponent]
