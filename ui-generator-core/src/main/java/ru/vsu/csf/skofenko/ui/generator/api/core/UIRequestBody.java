package ru.vsu.csf.skofenko.ui.generator.api.core;

import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

public interface UIRequestBody {
    String getEntityName();
    List<UIField> getFields();
}
