package ru.vsu.csf.skofenko.ui.generator.api;

/**
 * Main user interface class.
 * Provides ability to create and startup UI based on configuration.
 */
public interface UI extends Runnable {

    /**
     * Returns server base url.
     * It is used for configuration of request proxying on tje frontend.
    */
    String getBaseUrl();

    /**
     * Creates user interface based on params specified on initialization.
     * @param overrideUI specifies whether previously generated UI will be overriden
     */
    boolean create(boolean overrideUI);
}
