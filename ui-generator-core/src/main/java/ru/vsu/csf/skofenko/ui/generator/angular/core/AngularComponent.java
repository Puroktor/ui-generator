package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vsu.csf.skofenko.ui.generator.api.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AngularComponent extends AngularElement implements UIComponent {
    @JsonProperty
    @JsonDeserialize(contentAs = AngularEndpoint.class)
    private List<UIEndpoint> endpoints = new ArrayList<>();
}
