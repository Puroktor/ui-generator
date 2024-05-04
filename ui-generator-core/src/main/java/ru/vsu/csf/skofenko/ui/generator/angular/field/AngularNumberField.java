package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AngularNumberField extends AngularField {
    @JsonProperty
    private Integer min;
    @JsonProperty
    private Integer max;

    public AngularNumberField(String displayName, String codeName, boolean required, Integer min, Integer max) {
        super(displayName, codeName, FieldType.NUMBER, required);
        this.min = min;
        this.max = max;
    }
}
