package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIDateField;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AngularDateField extends AngularField implements UIDateField {

    @JsonProperty
    private String dateFormat;

    public AngularDateField(String displayName, String codeName, boolean required, String dateFormat) {
        super(displayName, codeName, FieldType.DATE, required);
        this.dateFormat = dateFormat;
    }
}
