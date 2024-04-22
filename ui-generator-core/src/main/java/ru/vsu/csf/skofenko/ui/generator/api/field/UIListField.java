package ru.vsu.csf.skofenko.ui.generator.api.field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents list field in the UI.
 */
public interface UIListField extends UIField  {

    /**
     * Description of element of the list.
     */
    @JsonProperty
    UIField getElement();
}
