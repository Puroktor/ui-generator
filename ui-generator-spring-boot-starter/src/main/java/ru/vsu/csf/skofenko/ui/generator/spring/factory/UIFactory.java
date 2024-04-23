package ru.vsu.csf.skofenko.ui.generator.spring.factory;

import ru.vsu.csf.skofenko.ui.generator.angular.core.AngularUI;
import ru.vsu.csf.skofenko.ui.generator.api.UI;
import ru.vsu.csf.skofenko.ui.generator.api.UIComponent;

import java.util.Collection;
import java.util.List;

public class UIFactory {
    public static UI createUI(String baseUrl, Collection<Object> controllers) {
        List<UIComponent> uiComponents = controllers.stream()
                .map(obj -> UIComponentFactory.createUIComponent(obj.getClass()))
                .toList();
        AngularUI angularUI = new AngularUI();
        angularUI.setBaseUrl(baseUrl);
        angularUI.setComponents(uiComponents);
        return angularUI;
    }
}
