import typing
from typing import Final
from flask import Blueprint
from .config import UIConfig
from .mapper import ConfigMapper
from .factories import UIComponentFactory

DEFAULT_CONFIG_FILENAME: Final[str] = "ui-config.json"


class FlaskUIGenerator:
    def __init__(self):
        self.ui_components = []

    def register_blueprint(self, blueprint: Blueprint):
        ui_component = UIComponentFactory.create_component(blueprint)
        self.ui_components.append(ui_component)

    def write_config(self, base_url: str, **options: typing.Any):
        config = UIConfig(base_url=base_url, components=self.ui_components)
        filename = options.pop('filename', DEFAULT_CONFIG_FILENAME)
        with open(filename, 'w', encoding='utf-8') as file:
            json_str = ConfigMapper.encode(config)
            file.write(json_str)
