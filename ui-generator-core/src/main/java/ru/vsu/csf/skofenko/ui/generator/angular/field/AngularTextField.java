package ru.vsu.csf.skofenko.ui.generator.angular.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AngularTextField extends AngularField {
    @JsonProperty
    private Integer minLength;
    @JsonProperty
    private Integer maxLength;
    @JsonProperty
    private String pattern;

    public AngularTextField(String displayName, String codeName, boolean required, Integer minLength, Integer maxLength, String pattern) {
        super(displayName, codeName, FieldType.TEXT, required);
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.pattern = pattern;
    }
}
