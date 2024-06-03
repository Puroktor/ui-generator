package ru.vsu.csf.skofenko.ui.generator.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.vsu.csf.skofenko.ui.generator.spring.factory.UIFieldFactory.DEFAULT_DATE_FORMAT;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface DateField {
    String value() default DEFAULT_DATE_FORMAT;
}
