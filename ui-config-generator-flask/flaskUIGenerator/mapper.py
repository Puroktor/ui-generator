import dataclasses
import json
from enum import Enum
from json import JSONEncoder

from .config import UIConfig


def snake_to_camel(s: str) -> str:
    a = s.split('_')
    a[0] = a[0].lower()
    if len(a) > 1:
        a[1:] = [u.title() for u in a[1:]]
    return ''.join(a)


def convert_to_camel(snake_dict: dict) -> dict:
    camel_dict = dict()
    for key, value in snake_dict.items():
        if value is None:
            continue
        camel_key = snake_to_camel(key)
        if isinstance(value, dict) and not key == "submit_to_display_values":
            value = convert_to_camel(value)
        if isinstance(value, list):
            camel_values = []
            for element in value:
                if isinstance(element, dict):
                    element = convert_to_camel(element)
                camel_values.append(element)
            value = camel_values
        camel_dict[camel_key] = value
    return camel_dict


class ConfigEncoder(JSONEncoder):
    def default(self, o):
        if dataclasses.is_dataclass(o):
            snake_dict = dataclasses.asdict(o)
            return convert_to_camel(snake_dict)
        if isinstance(o, Enum):
            return o.name
        return super().default(o)


class ConfigMapper:
    @staticmethod
    def encode(config: UIConfig) -> str:
        return json.dumps(config, cls=ConfigEncoder, indent=4)
