package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIClassField;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AngularClassField extends AngularField implements UIClassField {

    @JsonProperty
    @JsonDeserialize(contentAs = AngularField.class)
    private List<UIField> innerFields;

    public AngularClassField(String displayName, String submitName, boolean required, List<UIField> innerFields) {
        super(displayName, submitName, UIField.FieldType.CLASS, required);
        this.innerFields = innerFields;
    }
}
