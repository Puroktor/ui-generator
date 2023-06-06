package ru.vsu.csf.skofenko.ui.generator.spring.factory;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestType;
import ru.vsu.csf.skofenko.ui.generator.core.AngularComponent;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.DisplayName;

import java.lang.reflect.Method;

public class UIComponentFactory {
    public static UIComponent createUIComponent(Class<?> clazz) {
        Controller controller = clazz.getDeclaredAnnotation(Controller.class);
        RestController restController = clazz.getDeclaredAnnotation(RestController.class);
        if (controller == null && restController == null) {
            throw new BeanDefinitionValidationException("Not a controller annotated with @AutoGenerateUI");
        }
        RequestMapping requestMapping = clazz.getDeclaredAnnotation(RequestMapping.class);
        String controllerUrl = requestMapping == null ? "" : requestMapping.value()[0];
        DisplayName controllerName = clazz.getDeclaredAnnotation(DisplayName.class);
        UIComponent uiComponent = new AngularComponent(controllerName != null ? controllerName.value() : clazz.getSimpleName());
        for (Method method : clazz.getDeclaredMethods()) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            PutMapping putMapping = method.getAnnotation(PutMapping.class);
            DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
            String mapping = null;
            UIRequestType requestType = null;
            if (getMapping != null) {
                mapping = createMapping(controllerUrl, getMapping.value().length == 0 ? "" : getMapping.value()[0]);
                requestType = UIRequestType.GET;
            } else if (postMapping != null) {
                mapping = createMapping(controllerUrl, postMapping.value().length == 0 ? "" : postMapping.value()[0]);
                requestType = UIRequestType.POST;
            } else if (putMapping != null) {
                mapping = createMapping(controllerUrl, putMapping.value().length == 0 ? "" : putMapping.value()[0]);
                requestType = UIRequestType.PUT;
            } else if (deleteMapping != null) {
                mapping = createMapping(controllerUrl, deleteMapping.value().length == 0 ? "" : deleteMapping.value()[0]);
                requestType = UIRequestType.DELETE;
            }
            UIEndpoint uiEndpoint = UIEndpointFactory.createEndpoint(mapping, requestType, method);
            uiComponent.addEndpoint(uiEndpoint);
        }
        return uiComponent;
    }

    private static String createMapping(String baseMapping, String endpointMapping) {
        return StringUtils.isBlank(endpointMapping) ? baseMapping : "%s/%s".formatted(baseMapping, endpointMapping);
    }
}
