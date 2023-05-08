package ru.vsu.csf.skofenko.ui.generator.spring.factory;

import ru.vsu.csf.skofenko.ui.generator.api.core.UI;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.core.AngularUI;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class UIFactory {
    public static UI createUI(String baseUrl, Collection<Object> controllers) {
        List<UIComponent> uiComponents = controllers.stream()
                .map(obj -> UIComponentFactory.createUIComponent(obj.getClass()))
                .toList();
        return new AngularUI(baseUrl, Paths.get(""), uiComponents);
    }
}
