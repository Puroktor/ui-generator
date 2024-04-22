package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import ru.vsu.csf.skofenko.ui.generator.api.UIComponent;
import ru.vsu.csf.skofenko.ui.generator.api.UIEndpoint;

import java.util.List;

@Getter
@Data
public class AngularComponent implements UIComponent {
    @JsonProperty
    private String displayName;
    @JsonProperty
    @JsonDeserialize(contentAs = UIEndpoint.class)
    private List<UIEndpoint> uiEndpoints;

    /* Internal values*/
    private String fileName;
    private String scriptName;

    @Override
    public void createNames() {
        this.fileName = displayName.replaceAll("\\s", "").toLowerCase();
        this.scriptName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
    }

    @Override
    public List<UIEndpoint> getEndpoints() {
        return List.copyOf(uiEndpoints);
    }
}
