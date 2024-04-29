package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import freemarker.template.TemplateException;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.skofenko.ui.generator.angular.render.AngularProjectGenerator;
import ru.vsu.csf.skofenko.ui.generator.api.UI;
import ru.vsu.csf.skofenko.ui.generator.api.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@Data
@NoArgsConstructor
public class AngularUI implements UI {

    public static final String FRONTEND_DIR_NAME = "frontend";
    public static final String FRONTEND_LOGS_FILE = "frontend-log.txt";
    public static final Path RESOURCES_PATH = Paths.get("");

    @JsonProperty
    @JsonDeserialize(contentAs = AngularComponent.class)
    private Collection<UIComponent> components;
    @JsonProperty
    private String baseUrl;

    @Override
    public boolean create(boolean overrideUI) {
        if (components.isEmpty()) {
            return false;
        }
        try {
            File projectDir = RESOURCES_PATH.resolve(FRONTEND_DIR_NAME).toFile();
            if (projectDir.exists() && !overrideUI) {
                return true;
            }
            projectDir.mkdir();
            AngularProjectGenerator.createBaseProject(projectDir);
            AngularProjectGenerator.createProxyConfig(getBaseUrl(), projectDir);
            createElementNames(components);
            for (UIComponent component : components) {
                File componentDir = AngularProjectGenerator.createComponent(component, projectDir);
                for (UIEndpoint endpoint : component.getEndpoints()) {
                    AngularProjectGenerator.createEndpoint(endpoint, component, componentDir);
                }
            }
            AngularProjectGenerator.createRouting(components, projectDir);
            return true;
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException("Couldn't create Angular project", e);
        }
    }

    private void createElementNames(Collection<UIComponent> components) {
        for (UIComponent component : components) {
            component.createNames();
            component.getEndpoints().forEach(UIEndpoint::createNames);
        }
    }

    @Override
    public void run() {
        try {
            File directory = RESOURCES_PATH.resolve(FRONTEND_DIR_NAME).toFile();
            File logs = RESOURCES_PATH.resolve(FRONTEND_LOGS_FILE).toFile();
            new FileWriter(logs).close();
            new ProcessBuilder("npm.cmd", "install").directory(directory).start().waitFor();
            Process process = new ProcessBuilder("ng.cmd", "serve")
                    .directory(directory)
                    .redirectOutput(logs)
                    .redirectError(logs)
                    .start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                process.toHandle().children().forEach(ProcessHandle::destroy);
                process.destroy();
            }));
        } catch (Exception e) {
            throw new RuntimeException("Error during frontend startup", e);
        }
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }
}
