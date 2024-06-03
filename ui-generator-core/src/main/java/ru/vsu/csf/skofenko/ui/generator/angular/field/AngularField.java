package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property="fieldType",
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value=AngularField.class, name="BOOL"),
        @JsonSubTypes.Type(value=AngularNumberField.class, name="NUMBER"),
        @JsonSubTypes.Type(value=AngularTextField.class, name="TEXT"),
        @JsonSubTypes.Type(value=AngularClassField.class, name="CLASS"),
        @JsonSubTypes.Type(value=AngularDateField.class, name="DATE"),
        @JsonSubTypes.Type(value=AngularEnumField.class, name="ENUM"),
        @JsonSubTypes.Type(value=AngularListField.class, name="LIST"),
})
public class AngularField implements UIField {
    @JsonProperty
    private String displayName;
    @JsonProperty
    private String codeName;
    @JsonProperty
    private FieldType fieldType;
    @JsonProperty
    private boolean required;
}
