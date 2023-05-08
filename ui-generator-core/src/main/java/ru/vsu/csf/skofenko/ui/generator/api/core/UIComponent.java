package ru.vsu.csf.skofenko.ui.generator.api.core;

import java.util.List;

public interface UIComponent {
    String getDisplayName();
    String getFileName();
    String getScriptName();
    boolean addEndpoint(UIEndpoint uiEndpoint);
    List<UIEndpoint> getEndpoints();
}
