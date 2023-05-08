package ru.vsu.csf.skofenko.ui.generator.core;

import freemarker.template.TemplateException;
import ru.vsu.csf.skofenko.ui.generator.api.core.UI;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.render.AngularProjectGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class AngularUI implements UI {

    public static final String FRONTEND_DIR_NAME = "frontend";
    public static final String FRONTEND_LOGS_FILE = "frontend-log.txt";
    private final Collection<UIComponent> components;
    private final Path resourcePath;
    private final String baseUrl;

    public AngularUI(String baseUrl,Path resourcePath, Collection<UIComponent> components) {
        this.resourcePath = resourcePath;
        this.components = components;
        this.baseUrl = baseUrl;
    }

    @Override
    public boolean create(boolean overrideUI) {
        if (components.isEmpty()) {
            return false;
        }
        try {
            File projectDir = resourcePath.resolve(FRONTEND_DIR_NAME).toFile();
            if (projectDir.exists() && !overrideUI) {
                return true;
            }
            projectDir.mkdir();
            AngularProjectGenerator.createBaseProject(projectDir);
            AngularProjectGenerator.createProxyConfig(getBaseUrl(), projectDir);
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

    @Override
    public void run() {
        try {
            File directory = resourcePath.resolve(FRONTEND_DIR_NAME).toFile();
            File logs = resourcePath.resolve(FRONTEND_LOGS_FILE).toFile();
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
