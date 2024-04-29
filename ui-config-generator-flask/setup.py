from setuptools import setup, find_packages

setup(
    name="flaskUIGenerator",
    description="UI configuration generator for Flask REST applications",
    author="Kirill Skofenko",
    version="1.0",
    packages=find_packages(),
    install_requires=[
        "Flask",
    ]
)
