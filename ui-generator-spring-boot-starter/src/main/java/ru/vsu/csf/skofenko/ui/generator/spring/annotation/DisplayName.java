package ru.vsu.csf.skofenko.ui.generator.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface DisplayName {
    String value() default "";
}