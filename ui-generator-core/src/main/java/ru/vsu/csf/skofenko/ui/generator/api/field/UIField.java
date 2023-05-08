package ru.vsu.csf.skofenko.ui.generator.api.field;

/**
 * Represents field of any entity that will be used in UI.
 */
public interface UIField {

    /**
     * Returns name that will be shown for the users
     */
    String getDisplayName();

    /**
     * Returns name that will be used for form submitting
     */
    String getSubmitName();

    /**
     * Returns type of this field.
     * It is used in template renderer.
     */
    FieldType getFieldType();

    /**
     * Specifies whether this field is required
     */
    boolean isRequired();

    /**
     *  Enumerates all supported UI field types
     */
    enum FieldType {
        TEXT,
        NUMBER,
        BOOL,
        ENUM,
        LIST,
        CLASS
    }
}
