package ru.vsu.csf.skofenko.ui.generator.api.field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents field of any entity that will be used in UI.
 */
@RequiredArgsConstructor
@Getter
public class UIField {
    /**
     * Name that will be shown for the users
     */
    private final String displayName;
    /**
     * Name that will be used for form submitting
     */
    private final String submitName;
    /**
     * Type of this field.
     * It is used in template renderer.
     */
    private final FieldType fieldType;
    /**
     * Specifies whether this field is required
     */
    private final boolean required;

    /**
     *  Enumerates all supported UI field types
     */
    public enum FieldType {
        TEXT,
        NUMBER,
        BOOL,
        ENUM,
        LIST,
        CLASS
    }
}
