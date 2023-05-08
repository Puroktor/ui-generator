package ru.vsu.csf.skofenko.ui.generator.field;

import lombok.Getter;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;
import java.util.List;

@Getter
public class AngularClassField extends AngularUIField {
    private final List<UIField> innerFields;

    public AngularClassField(String displayName, String submitName, boolean required, List<UIField> innerFields) {
        super(displayName, submitName, FieldType.CLASS, required);
        this.innerFields = innerFields;
    }
}
