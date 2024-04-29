package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import ru.vsu.csf.skofenko.ui.generator.angular.field.AngularField;
import ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.api.UIRequestBody;
import ru.vsu.csf.skofenko.ui.generator.api.UIRequestType;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class AngularEndpoint extends AngularElement implements UIEndpoint {
    @JsonProperty
    private String mapping;
    @JsonProperty
    private UIRequestType requestType;
    @JsonProperty
    @JsonDeserialize(as = AngularRequestBody.class)
    private UIRequestBody requestBody;
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> pathParams = new ArrayList<>();
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> queryParams = new ArrayList<>();
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> responseBody = new ArrayList<>();
}
