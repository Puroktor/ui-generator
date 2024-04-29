package ru.vsu.csf.skofenko.ui.generator.api;

import java.util.List;

/**
 * Represents UI component - element where endpoints are stored.
 */
public interface UIComponent extends UIElement {

    /**
     * Returns all endpoints belonging to the component.
     */
    List<UIEndpoint> getEndpoints();
}
