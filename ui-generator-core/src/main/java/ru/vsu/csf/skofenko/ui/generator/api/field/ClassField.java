package ru.vsu.csf.skofenko.ui.generator.api.field;

import java.util.List;

/**
 * Represents class entity field in the UI.
 */
public interface ClassField {

    /**
     * Returns inner fields descriptions of the class.
     */
    List<UIField> getInnerFields();
}
