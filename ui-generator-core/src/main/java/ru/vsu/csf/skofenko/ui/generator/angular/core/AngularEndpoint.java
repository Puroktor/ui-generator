package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.angular.field.AngularField;
import ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.api.UIRequestBody;
import ru.vsu.csf.skofenko.ui.generator.api.UIRequestType;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AngularEndpoint implements UIEndpoint {
    @JsonProperty
    private String displayName;
    @JsonProperty
    private String mapping;
    @JsonProperty
    private UIRequestType requestType;
    @JsonProperty
    @JsonDeserialize(as = AngularRequestBody.class)
    private UIRequestBody requestBody;
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> pathParams;
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> queryParams;
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> responseBody;

    /* Internal values*/
    private String fileName;
    private String scriptName;

    @Override
    public void createNames() {
        this.fileName = displayName.replaceAll("\\s", "").toLowerCase();
        this.scriptName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
    }
}
