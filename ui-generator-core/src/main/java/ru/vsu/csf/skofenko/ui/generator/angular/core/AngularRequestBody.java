package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.angular.field.AngularField;
import ru.vsu.csf.skofenko.ui.generator.api.UIRequestBody;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AngularRequestBody implements UIRequestBody {
    @JsonProperty
    private String entityName;
    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> fields;
}
