package ru.vsu.csf.skofenko.ui.generator.api.field;

import java.util.Map;

public interface EnumField extends UIField {
    Map<String, String> getSubmitToDisplayValues();
}
