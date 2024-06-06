package ru.vsu.csf.skofenko.ui.generator.angular.render;

import freemarker.template.TemplateException;
import lombok.experimental.UtilityClass;
import ru.vsu.csf.skofenko.ui.generator.api.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIDateField;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;
import ru.vsu.csf.skofenko.ui.generator.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@UtilityClass
public class AngularProjectGenerator {

    private static final String BASE_ANGULAR_PROJECT_PATH = "/angular-base";
    private static final String CSS_COMPONENT_TEMPLATE_PATH = "/angular-templates/css/component.css";
    private static final String CSS_ENDPOINT_TEMPLATE_PATH = "/angular-templates/css/endpoint.css";

    public void createBaseProject(File destinationProjectDir) throws IOException {
        FileUtils.copyTo(BASE_ANGULAR_PROJECT_PATH, destinationProjectDir.toPath());
    }

    public File createComponent(UIComponent component, File projectDir) throws TemplateException, IOException {
        File componentDir = new File(projectDir, "src/app/%s".formatted(component.getFileName()));
        componentDir.mkdirs();

        File componentCSS = new File(componentDir, "%s.component.css".formatted(component.getFileName()));
        FileUtils.copyTo(CSS_COMPONENT_TEMPLATE_PATH, componentCSS.toPath());

        File componentTS = new File(componentDir, "%s.component.ts".formatted(component.getFileName()));
        AngularTemplateRenderer.renderTemplate(componentTS,
                AngularTemplateRenderer.TemplateFile.COMPONENT_TS, Map.of("component", component));

        File componentHTML = new File(componentDir, "%s.component.html".formatted(component.getFileName()));
        AngularTemplateRenderer.renderTemplate(componentHTML,
                AngularTemplateRenderer.TemplateFile.COMPONENT_HTML, Map.of("component", component));

        return componentDir;
    }

    public void createEndpoint(UIEndpoint endpoint, UIComponent component, File componentDir) throws TemplateException, IOException {
        File endpointDir = new File(componentDir, endpoint.getFileName());
        endpointDir.mkdirs();

        File endpointCSS = new File(endpointDir, "%s.component.css".formatted(endpoint.getFileName()));
        FileUtils.copyTo(CSS_ENDPOINT_TEMPLATE_PATH, endpointCSS.toPath());

        File endpointTS = new File(endpointDir, "%s.component.ts".formatted(endpoint.getFileName()));
        Map<String, Object> variablesMap = new java.util.HashMap<>();
        variablesMap.put("componentName", component.getFileName());
        variablesMap.put("endpoint", endpoint);
        variablesMap.put("dateFormat", getDateFormat(endpoint));
        AngularTemplateRenderer.renderTemplate(endpointTS, AngularTemplateRenderer.TemplateFile.ENDPOINT_TS, variablesMap);

        File endpointHTML = new File(endpointDir, "%s.component.html".formatted(endpoint.getFileName()));
        AngularTemplateRenderer.renderTemplate(endpointHTML, AngularTemplateRenderer.TemplateFile.ENDPOINT_HTML, variablesMap);
    }

    public void createRouting(Collection<UIComponent> components, File projectDir) throws TemplateException, IOException {
        File routingModule = new File(projectDir, "src/app/app-routing.module.ts");
        AngularTemplateRenderer.renderTemplate(routingModule,
                AngularTemplateRenderer.TemplateFile.ROUTING_MODULE, Map.of("components", components));

        File headerHTML = new File(projectDir, "src/app/header/header.component.html");
        AngularTemplateRenderer.renderTemplate(headerHTML,
                AngularTemplateRenderer.TemplateFile.HEADER_HTML, Map.of("components", components));

        File appModule = new File(projectDir, "src/app/app.module.ts");
        AngularTemplateRenderer.renderTemplate(appModule,
                AngularTemplateRenderer.TemplateFile.APP_MODULE, Map.of("components", components));
    }

    public void createProxyConfig(String baseUrl, File projectDir) throws TemplateException, IOException {
        File proxyConfig = new File(projectDir, "src/proxy.conf.json");
        AngularTemplateRenderer.renderTemplate(proxyConfig, AngularTemplateRenderer.TemplateFile.PROXY_CONFIG, Map.of("baseUrl", baseUrl));
    }

    private String getDateFormat(UIEndpoint endpoint) {
        Stream<UIField> fieldStream = Stream.concat(
                endpoint.getPathParams().stream(),
                endpoint.getQueryParams().stream());
        if (endpoint.getRequestBody() != null) {
            fieldStream = Stream.concat(fieldStream, endpoint.getRequestBody().getFields().stream());
        }
        Optional<UIField> dateField = fieldStream
                .filter(uiField -> uiField.getFieldType() == UIField.FieldType.DATE)
                .findAny();
        return dateField.map(field -> ((UIDateField) (field)).getDateFormat()).orElse(null);
    }
}
