package ru.vsu.csf.skofenko.ui.generator.api.field;

/**
 * Represents field of array or collection in the UI.
 */
public interface ListField {

    /**
     * Returns description of element of the list.
     */
    UIField getElement();
}
