package ru.vsu.csf.skofenko.ui.generator.api.field;

import lombok.Getter;

@Getter
public class ListField extends UIField  {

    /**
     * Description of element of the list.
     */
    private final UIField element;

    public ListField(String displayName, String submitName, boolean required, UIField element) {
        super(displayName, submitName, FieldType.LIST, required);
        this.element = element;
    }
}
