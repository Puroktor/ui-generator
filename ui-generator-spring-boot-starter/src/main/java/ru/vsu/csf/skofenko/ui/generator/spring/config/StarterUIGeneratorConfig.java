package ru.vsu.csf.skofenko.ui.generator.spring.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.vsu.csf.skofenko.ui.generator.spring.component.UIGeneratorContextListener;

@AutoConfiguration
@ConditionalOnWebApplication
@EnableConfigurationProperties(UIGeneratorProperties.class)
public class StarterUIGeneratorConfig {

    @Bean
    public UIGeneratorContextListener contextListener(UIGeneratorProperties uiGeneratorProperties) {
        return new UIGeneratorContextListener(uiGeneratorProperties);
    }
}
