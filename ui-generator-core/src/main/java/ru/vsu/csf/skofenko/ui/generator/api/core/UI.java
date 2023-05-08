package ru.vsu.csf.skofenko.ui.generator.api.core;

public interface UI extends Runnable {

    String getBaseUrl();

    boolean create(boolean overrideUI);
}
