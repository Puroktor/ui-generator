package ru.vsu.csf.skofenko.ui.generator.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.csf.skofenko.ui.generator.spring.component.ContextListener;
import ru.vsu.csf.skofenko.ui.generator.spring.postprocessor.UIGeneratorPostProcessor;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(UIGeneratorProperties.class)
public class StarterUIGeneratorConfig {

    private static final Logger logger = LoggerFactory.getLogger(StarterUIGeneratorConfig.class);
    @Bean
    public UIGeneratorPostProcessor uiGeneratorPostProcessor() {
        return new UIGeneratorPostProcessor();
    }

    @Bean
    public ContextListener contextListener() {
        logger.info("UI Generator enabled");
        return new ContextListener();
    }
}
