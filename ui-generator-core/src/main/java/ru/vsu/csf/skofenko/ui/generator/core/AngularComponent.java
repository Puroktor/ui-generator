package ru.vsu.csf.skofenko.ui.generator.core;

import lombok.Getter;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AngularComponent implements UIComponent {
    private final List<UIEndpoint> uiEndpoint = new ArrayList<>();
    private final String displayName;
    private final String fileName;
    private final String scriptName;

    public AngularComponent(String displayName) {
        this.displayName = displayName;
        this.fileName = displayName.replaceAll("\\s", "").toLowerCase();
        this.scriptName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
    }

    @Override
    public boolean addEndpoint(UIEndpoint uiEndpoint) {
        return this.uiEndpoint.add(uiEndpoint);
    }

    @Override
    public List<UIEndpoint> getEndpoints() {
        return List.copyOf(uiEndpoint);
    }
}
