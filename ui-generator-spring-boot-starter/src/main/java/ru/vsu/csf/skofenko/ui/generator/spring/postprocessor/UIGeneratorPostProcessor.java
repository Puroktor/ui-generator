package ru.vsu.csf.skofenko.ui.generator.spring.postprocessor;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.vsu.csf.skofenko.ui.generator.api.core.UIRequestType;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.AutoGenerateUI;

import java.lang.reflect.Method;

public class UIGeneratorPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Controller controllerAnnotation = clazz.getDeclaredAnnotation(Controller.class);
        AutoGenerateUI generateUIAnnotation = clazz.getDeclaredAnnotation(AutoGenerateUI.class);
        System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        if (generateUIAnnotation == null) {
            return bean;
        }
        if (controllerAnnotation == null) {
            throw new BeanDefinitionValidationException("Not a controller annotated with @AutoGenerateUI");
        }
        for (Method method : clazz.getDeclaredMethods()) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            PutMapping putMapping = method.getAnnotation(PutMapping.class);
            DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
            String mapping = null;
            UIRequestType requestType = null;
            if (getMapping != null) {
                mapping = createMapping(controllerAnnotation.value(), getMapping.value()[0]);
                requestType = UIRequestType.GET;
            } else if (postMapping != null) {
                mapping = createMapping(controllerAnnotation.value(), postMapping.value()[0]);
                requestType = UIRequestType.POST;
            } else if (putMapping != null) {
                mapping = createMapping(controllerAnnotation.value(), putMapping.value()[0]);
                requestType = UIRequestType.PUT;
            } else if (deleteMapping != null) {
                mapping = createMapping(controllerAnnotation.value(), deleteMapping.value()[0]);
                requestType = UIRequestType.DELETE;
            }
        }
        return bean;
    }

    private String createMapping(String baseMapping, String endpointMapping) {
        return StringUtils.isBlank(endpointMapping) ? baseMapping : "%s/%s".formatted(baseMapping, endpointMapping);
    }
}
