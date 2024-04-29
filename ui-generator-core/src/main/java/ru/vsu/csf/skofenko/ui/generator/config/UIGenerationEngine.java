package ru.vsu.csf.skofenko.ui.generator.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.angular.core.AngularUI;
import ru.vsu.csf.skofenko.ui.generator.api.UI;

@Getter
@RequiredArgsConstructor
public enum UIGenerationEngine {
    ANGULAR(AngularUI.class);

    private final Class<? extends UI> uiClass;
}
