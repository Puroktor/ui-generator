package ru.vsu.csf.skofenko.ui.generator.spring.factory;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.lang.Nullable;
import ru.vsu.csf.skofenko.ui.generator.angular.field.*;
import ru.vsu.csf.skofenko.ui.generator.api.field.UIField;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.DisplayName;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.NumberField;
import ru.vsu.csf.skofenko.ui.generator.spring.annotation.TextField;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class UIFieldFactory {

    public static UIField createUIField(AnnotatedElement field, Type filedType, String submitName) {
        return createUIField(field, filedType, submitName, new HashSet<>(), true);
    }

    public static UIField createUIField(AnnotatedElement field, Type filedType, String submitName,
                                 Set<Class<?>> parsedClassesSet, boolean isParentRequired) {
        Class<?> filedClass = getClassFromType(filedType);
        if (parsedClassesSet.contains(filedClass)) {
            throw new IllegalArgumentException("Circular element link: " + filedClass);
        }
        String displayName = getFieldDisplayName(field, filedClass, submitName);
        boolean isRequired = isParentRequired && (field == null || !field.isAnnotationPresent(Nullable.class));
        if (Number.class.isAssignableFrom(filedClass)) {
            Integer min = null, max = null;
            if (field != null && field.isAnnotationPresent(NumberField.class)) {
                NumberField annotation = field.getDeclaredAnnotation(NumberField.class);
                min = annotation.min();
                max = annotation.max();
            }
            return new AngularNumberField(displayName, submitName, isRequired, min, max);
        } else if (String.class.isAssignableFrom(filedClass)) {
            Integer minLength = null, maxLength = null;
            String pattern = null;
            if (field != null && field.isAnnotationPresent(TextField.class)) {
                TextField annotation = field.getDeclaredAnnotation(TextField.class);
                pattern = annotation.pattern();
                minLength = annotation.minLength();
                maxLength = annotation.maxLength();
            }
            return new AngularTextField(displayName, submitName, isRequired, minLength, maxLength, pattern);
        } else if (filedClass.isEnum()) {
            Map<String, String> submitToDisplayValues = Arrays.stream(filedClass.getFields()).collect(
                    Collectors.toMap(
                            Field::getName, enumField -> getFieldDisplayName(enumField, enumField.getType(), enumField.getName())
                    ));
            return new AngularEnumField(displayName, submitName, isRequired, submitToDisplayValues);
        } else if (Boolean.class.isAssignableFrom(filedClass)) {
            return new AngularField(displayName, submitName, UIField.FieldType.BOOL, isRequired);
        } else if (filedClass.isArray()) {
            Class<?> elementClass = filedClass.getComponentType();
            UIField element = createUIField(null, elementClass, elementClass.getSimpleName(), parsedClassesSet, isRequired);
            return new AngularListField(displayName, submitName, isRequired, element);
        } else if (Iterable.class.isAssignableFrom(filedClass)) {
            Type genericType = ((ParameterizedType) filedType).getActualTypeArguments()[0];
            UIField element = createUIField(null, genericType, genericType.getTypeName(), parsedClassesSet, isRequired);
            return new AngularListField(displayName, submitName, isRequired, element);
        } else {
            List<Field> innerFields = getAllFields(filedClass);
            List<UIField> uiInnerFields = new ArrayList<>();
            parsedClassesSet.add(filedClass);
            for (Field innerField : innerFields) {
                UIField uiInnerField = createUIField(innerField, innerField.getGenericType(), innerField.getName(),
                        parsedClassesSet, isRequired);
                uiInnerFields.add(uiInnerField);
            }
            return new AngularClassField(displayName, submitName, isRequired, uiInnerFields);
        }
    }

    public static String getFieldDisplayName(AnnotatedElement element, Type type, String submitName) {
        DisplayName nameAnnotation = element != null ? element.getDeclaredAnnotation(DisplayName.class) : null;
        if (nameAnnotation == null) {
            DisplayName classNameAnnotation = ((Class<?>) type).getDeclaredAnnotation(DisplayName.class);
            return classNameAnnotation == null ? submitName : classNameAnnotation.value();
        } else {
            return nameAnnotation.value();
        }
    }

    private static Class<?> getClassFromType(Type filedType) {
        return filedType instanceof ParameterizedType
                ? (Class<?>) ((ParameterizedType) filedType).getRawType()
                : ClassUtils.primitiveToWrapper((Class<?>) filedType);
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }
        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        List<Field> filteredFields = Arrays.stream(clazz.getDeclaredFields()).toList();
        result.addAll(filteredFields);
        return result;
    }
}
