package ru.vsu.csf.skofenko.ui.generator.api.core;

import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

public interface UIEndpoint {
    String getDisplayName();
    String getFileName();
    String getScriptName();
    String getMapping();
    UIRequestType getRequestType();
    List<UIField> getQueryParams();
    UIRequestBody getRequestBody();
    List<UIField> getResponseBody();
}
