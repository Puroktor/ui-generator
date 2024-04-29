package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIEnumField;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AngularEnumField extends AngularField implements UIEnumField {

    @JsonProperty
    private Map<String, String> submitToDisplayValues;

    public AngularEnumField(String displayName, String submitName, boolean required, Map<String, String> submitToDisplayValues) {
        super(displayName, submitName, FieldType.ENUM, required);
        this.submitToDisplayValues = submitToDisplayValues;
    }
}
