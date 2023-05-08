package ru.vsu.csf.skofenko.ui.generator.api.field;

import java.util.Map;

/**
 * Represents enum field in the UI.
 */
public interface EnumField extends UIField {

    /**
     * Provides map of all possible enum values.
     * Where key is name of the value that will be used when submitting,
     * and value will be used for displaying to users.
     */
    Map<String, String> getSubmitToDisplayValues();
}
