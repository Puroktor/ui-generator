package ru.vsu.csf.skofenko.ui.generator.core;

import lombok.Getter;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestBody;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestType;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

@Getter
public class AngularEndpoint implements UIEndpoint {
    private final String displayName;
    private final String fileName;
    private final String scriptName;
    private final String mapping;
    private final UIRequestType requestType;
    private final List<UIField> queryParams;
    private final UIRequestBody requestBody;
    private final List<UIField> responseBody;

    public AngularEndpoint(String displayName, String mapping, UIRequestType requestType,
                           List<UIField> queryParams, UIRequestBody requestBody, List<UIField> responseBody) {
        this.displayName = displayName;
        this.fileName = displayName.replaceAll("\\s", "").toLowerCase();
        this.scriptName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
        this.mapping = mapping;
        this.requestType = requestType;
        this.queryParams = queryParams;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }
}
