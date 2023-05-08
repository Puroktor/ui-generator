package ru.vsu.csf.skofenko.ui.generator.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestBody;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class AngularRequestBody implements UIRequestBody {
    private final String entityName;
    private final List<UIField> fields;
}
