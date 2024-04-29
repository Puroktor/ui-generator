package ru.vsu.csf.skofenko.ui.generator.api;

import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

/**
 * Represents endpoint in the UI.
 * Stores info about request mapping, type, query params, request and response body.
 */
public interface UIEndpoint extends UIElement {

    /**
     * Returns request mapping of the endpoint.
     */
    String getMapping();

    /**
     * Returns list of UI fields, representing response body.
     */
    UIRequestType getRequestType();

    /**
     * Returns list of UI fields, representing path params.
     */
    List<UIField> getPathParams();

    /**
     * Returns list of UI fields, representing query params.
     */
    List<UIField> getQueryParams();

    /**
     * Returns request body. Can be absent, then will return null.
     */
    UIRequestBody getRequestBody();

    /**
     * Returns list of UI fields, representing response body.
     * If response body is absent, it will return empty list.
     */
    List<UIField> getResponseBody();
}
