package ru.vsu.csf.skofenko.ui.generator.spring.factory;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIEndpoint;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestBody;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestType;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;
import ru.vsu.csf.skofenko.ui.generator.core.AngularEndpoint;
import ru.vsu.csf.skofenko.ui.generator.core.AngularRequestBody;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.DisplayName;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UIEndpointFactory {

    public static UIEndpoint createEndpoint(String mapping, UIRequestType requestType, Method method) {
        List<UIField> pathParams = new ArrayList<>();
        List<UIField> queryParams = new ArrayList<>();
        UIRequestBody requestBody = null;
        DisplayName nameAnnotation = method.getDeclaredAnnotation(DisplayName.class);
        String methodDisplayName = nameAnnotation == null ? method.getName() : nameAnnotation.value();
        for (Parameter parameter : method.getParameters()) {
            PathVariable pathParamAnnotation = parameter.getDeclaredAnnotation(PathVariable.class);
            RequestParam queryParamAnnotation = parameter.getDeclaredAnnotation(RequestParam.class);
            RequestBody requestBodyAnnotation = parameter.getDeclaredAnnotation(RequestBody.class);
            if (pathParamAnnotation != null) {
                UIField uiField = UIFieldFactory.createUIField(parameter, parameter.getParameterizedType(), pathParamAnnotation.value());
                pathParams.add(uiField);
            } else if (queryParamAnnotation != null) {
                UIField uiField = UIFieldFactory.createUIField(parameter, parameter.getParameterizedType(), queryParamAnnotation.value());
                queryParams.add(uiField);
            } else if (requestBodyAnnotation != null) {
                List<UIField> fields = getUIFields(parameter.getType());
                String bodyName = UIFieldFactory.getFieldDisplayName(parameter, parameter.getParameterizedType(), parameter.getName());
                requestBody = new AngularRequestBody(bodyName, fields);
            }
        }
        List<UIField> responseFields = getUIFields(method.getReturnType());
        return new AngularEndpoint(methodDisplayName, mapping, requestType, pathParams, queryParams, requestBody, responseFields);
    }

    private static List<UIField> getUIFields(Class<?> typeClass) {
        return Arrays.stream(typeClass.getDeclaredFields())
                .map(field -> UIFieldFactory.createUIField(field, field.getGenericType(), field.getName()))
                .toList();
    }
}
