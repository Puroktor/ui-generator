package ru.vsu.csf.skofenko.ui.generator.api.field;

import java.util.List;

/**
 * Represents class entity field in the UI.
 */
public interface UIClassField extends UIField {

    /**
     * Inner fields descriptions of the class.
     */
    List<UIField> getInnerFields();
}
