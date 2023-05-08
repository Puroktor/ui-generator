package ru.vsu.csf.skofenko.ui.generator.render;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@UtilityClass
public class AngularTemplateRenderer {
    public enum TemplateFile {
        PROXY_CONFIG("proxy-config-json.ftl"),
        ROUTING_MODULE("routing-module.ftl"),
        HEADER_HTML("header-html.ftl"),
        COMPONENT_TS("component-ts.ftl"),
        COMPONENT_HTML("component-html.ftl"),
        ENDPOINT_HTML("endpoint-html.ftl"),
        ENDPOINT_TS("endpoint-ts.ftl"),
        APP_MODULE("app-module-ts.ftl");
        private final String fileName;
        TemplateFile(String fileName) {
            this.fileName = fileName;
        }
    }

    private final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

    static {
        ClassTemplateLoader loader = new ClassTemplateLoader(AngularTemplateRenderer.class, "/angular-templates");
        CONFIGURATION.setTemplateLoader(loader);
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void renderTemplate(File destinationFile, TemplateFile templateFile, Map<String, Object> variablesMap) throws TemplateException, IOException {
        Template template = CONFIGURATION.getTemplate(templateFile.fileName);
        try (FileWriter fileWriter = new FileWriter(destinationFile)) {
            template.process(variablesMap, fileWriter);
        }
    }
}
