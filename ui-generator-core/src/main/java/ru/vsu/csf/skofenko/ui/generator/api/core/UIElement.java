package ru.vsu.csf.skofenko.ui.generator.api.core;

/**
 * Base interface for elements that will be generated in UI.
 */
public interface UIElement {

    /**
     * Specifies name of the element that will be shown to the users.
     */
    String getDisplayName();

    /**
     * Specifies name of the element that will be used for file naming.
     */
    String getFileName();

    /**
     * Specifies name of the element that will be used in script files.
     */
    String getScriptName();
}
