package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIListField;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AngularListField extends AngularField implements UIListField {

    @JsonProperty
    @JsonDeserialize(as = AngularField.class)
    private UIField element;

    public AngularListField(String displayName, String submitName, boolean required, UIField element) {
        super(displayName, submitName, FieldType.LIST, required);
        this.element = element;
    }
}
