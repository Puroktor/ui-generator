package ru.vsu.csf.skofenko.ui.generator.api.field;

import lombok.Getter;

import java.util.Map;

/**
 * Represents enum field in the UI.
 */
@Getter
public class EnumField extends UIField {

    /**
     * Map of all possible enum values.
     * Key is name of the value that will be used when submitting,
     * and value will be used for displaying to users.
     */
    private final Map<String, String> submitToDisplayValues;

    public EnumField(String displayName, String submitName, boolean required, Map<String, String> submitToDisplayValues) {
        super(displayName, submitName, FieldType.ENUM, required);
        this.submitToDisplayValues = submitToDisplayValues;
    }
}
