package ru.vsu.csf.skofenko.ui.generator.api.field;

import java.util.Map;

/**
 * Represents enum field in the UI.
 */
public interface UIEnumField extends UIField {

    /**
     * Map of all possible enum values.
     * Key is name of the value that will be used when submitting,
     * and value will be used for displaying to users.
     */
    Map<String, String> getSubmitToDisplayValues();
}
