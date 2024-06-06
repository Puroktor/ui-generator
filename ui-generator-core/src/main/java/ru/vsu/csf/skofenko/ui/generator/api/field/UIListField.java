package ru.vsu.csf.skofenko.ui.generator.api.field;

/**
 * Represents list field in the UI.
 */
public interface UIListField extends UIField  {

    /**
     * Description of element of the list.
     */
    UIField getElement();
}
