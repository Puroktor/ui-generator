package ru.vsu.csf.skofenko.ui.generator.api.field;

import lombok.Getter;

import java.util.List;

/**
 * Represents class entity field in the UI.
 */
@Getter
public class ClassField extends UIField {

    /**
     * Inner fields descriptions of the class.
     */
    private final List<UIField> innerFields;

    public ClassField(String displayName, String submitName, boolean required, List<UIField> innerFields) {
        super(displayName, submitName, FieldType.CLASS, required);
        this.innerFields = innerFields;
    }
}
