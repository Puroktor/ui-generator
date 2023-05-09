package ru.vsu.csf.skofenko.ui.generator.spring.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.vsu.csf.skofenko.ui.generator.api.core.UI;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.AutoGenerateUI;
import ru.vsu.csf.skofenko.ui.generator.spring.config.UIGeneratorProperties;
import ru.vsu.csf.skofenko.ui.generator.spring.factory.UIFactory;

import java.util.Collection;

@Component
public class UIGeneratorContextListener {
    private final Logger logger = LoggerFactory.getLogger(UIGeneratorContextListener.class);
    private final UIGeneratorProperties uiGeneratorProperties;

    public UIGeneratorContextListener(UIGeneratorProperties uiGeneratorProperties) {
        this.uiGeneratorProperties = uiGeneratorProperties;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void onContextRefreshedEvent(ContextRefreshedEvent e) {
        Collection<Object> controllers = e.getApplicationContext().getBeansWithAnnotation(AutoGenerateUI.class).values();
        Environment environment = e.getApplicationContext().getEnvironment();
        String baseUrl = getBaseUrl(environment);
        UI ui = UIFactory.createUI(baseUrl, controllers);
        ui.create(uiGeneratorProperties.isOverride());
        logger.info("UI was automatically generated");
        if (uiGeneratorProperties.isStartup()) {
            new Thread(ui).start();
            logger.info("Generated UI is starting");
        }
    }

    private String getBaseUrl(Environment environment) {
        String serverPort = environment.getProperty("local.server.port");
        return "http://localhost:%s".formatted(serverPort);
    }
}
