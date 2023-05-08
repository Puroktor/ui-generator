package ru.vsu.csf.skofenko.ui.generator.api.core;

import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;

import java.util.List;

/**
 * Represents body of the request for {@link UIEndpoint}.
 */
public interface UIRequestBody {

    /**
     *  Returns display name of the request body.
     */
    String getEntityName();

    /**
     *  Provides list of UI fields, representing request body.
     *  Returned list must not be empty.
     */
    List<UIField> getFields();
}
