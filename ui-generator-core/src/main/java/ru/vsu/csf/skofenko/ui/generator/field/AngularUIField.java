package ru.vsu.csf.skofenko.ui.generator.field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

@RequiredArgsConstructor
@Getter
public class AngularUIField implements UIField {
    private final String displayName;
    private final String submitName;
    private final UIField.FieldType fieldType;
    private final boolean required;
}
