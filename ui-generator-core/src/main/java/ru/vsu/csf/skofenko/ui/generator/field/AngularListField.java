package ru.vsu.csf.skofenko.ui.generator.field;

import lombok.Getter;
import ru.vsu.csf.skofenko.ui.generator.api.field.ListField;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

@Getter
public class AngularListField extends AngularUIField implements ListField {

    private final UIField element;

    public AngularListField(String displayName, String submitName, boolean required, UIField element) {
        super(displayName, submitName, FieldType.LIST, required);
        this.element = element;
    }
}
