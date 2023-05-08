package ru.vsu.csf.skofenko.ui.generator.render;

import freemarker.template.TemplateException;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.core.AngularComponent;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@UtilityClass
public class AngularProjectGenerator {

    private final File baseAngularProject;
    private final File cssComponentTemplate;
    private final File cssEndpointTemplate;

    static {
        baseAngularProject = getFolderFile("/angular-base");
        cssComponentTemplate = getFolderFile("/angular-templates/css/component.css");
        cssEndpointTemplate = getFolderFile("/angular-templates/css/endpoint.css");
    }

    private File getFolderFile(String resourceFolder) {
        try {
            URI uri = AngularProjectGenerator.class.getResource(resourceFolder).toURI();
            if ("jar".equals(uri.getScheme())) {
                try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap(), null)){
                    return fileSystem.getPath(resourceFolder).toFile();
                }
            } else {
                return new File(uri);
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBaseProject(File destinationProjectDir) throws IOException {
        FileUtils.copyDirectory(baseAngularProject, destinationProjectDir, false);
    }

    public File createComponent(UIComponent component, File projectDir) throws TemplateException, IOException {
        File componentDir = new File(projectDir, "src/app/%s".formatted(component.getFileName()));
        componentDir.mkdirs();

        File componentCSS = new File(componentDir, "%s.component.css".formatted(component.getFileName()));
        FileUtils.copyFile(cssComponentTemplate, componentCSS);

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
        FileUtils.copyFile(cssEndpointTemplate, endpointCSS);

        File endpointTS = new File(endpointDir, "%s.component.ts".formatted(endpoint.getFileName()));
        AngularTemplateRenderer.renderTemplate(endpointTS,
                AngularTemplateRenderer.TemplateFile.ENDPOINT_TS,
                Map.of("componentName", component.getFileName(), "endpoint", endpoint));

        File endpointHTML = new File(endpointDir, "%s.component.html".formatted(endpoint.getFileName()));
        AngularTemplateRenderer.renderTemplate(endpointHTML,
                AngularTemplateRenderer.TemplateFile.ENDPOINT_HTML, Map.of("endpoint", endpoint));
    }

    public void createRouting(Collection<UIComponent> components, File projectDir) throws TemplateException, IOException {
        File routingModule = new File(projectDir, "src/app/app-routing.module.ts");
        AngularTemplateRenderer.renderTemplate(routingModule,
                AngularTemplateRenderer.TemplateFile.ROUTING_MODULE, Map.of("components", components));

        File headerTS = new File(projectDir, "src/app/header/header.component.ts");
        AngularTemplateRenderer.renderTemplate(headerTS,
                AngularTemplateRenderer.TemplateFile.COMPONENT_TS, Map.of("component", new AngularComponent("header")));

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
}
