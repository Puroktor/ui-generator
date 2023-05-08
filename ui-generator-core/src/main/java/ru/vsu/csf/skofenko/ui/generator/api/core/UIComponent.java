package ru.vsu.csf.skofenko.ui.generator.api.core;

import java.util.List;

/**
 * Represents UI component - element where endpoints are stored.
 */
public interface UIComponent extends UIElement {

    /**
     * Adds endpoint to the current component.
     * @param uiEndpoint endpoint to add
     */
    boolean addEndpoint(UIEndpoint uiEndpoint);

    /**
     * Returns all endpoints belonging to the component.
     */
    List<UIEndpoint> getEndpoints();
}
