package ru.vsu.csf.skofenko.ui.generator.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ui-generator")
public class UIGeneratorProperties {
    private boolean override;
    private boolean startup;

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public boolean isStartup() {
        return startup;
    }

    public void setStartup(boolean startup) {
        this.startup = startup;
    }
}
