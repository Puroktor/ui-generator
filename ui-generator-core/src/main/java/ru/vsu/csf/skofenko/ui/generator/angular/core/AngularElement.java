package ru.vsu.csf.skofenko.ui.generator.angular.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.vsu.csf.skofenko.ui.generator.api.UIElement;

import java.util.HashMap;
import java.util.Map;

@Data
public class AngularElement implements UIElement {
    private static final Map<String, Integer> NAME_TO_COUNT_MAP = new HashMap<>();

    @JsonProperty(value = "name")
    protected String displayName;
    /* Internal values*/
    protected String fileName;
    protected String scriptName;

    @Override
    public void createNames() {
        String filename = displayName.replaceAll("\\s", "").toLowerCase();
        if (NAME_TO_COUNT_MAP.containsKey(filename)) {
            this.fileName = filename + (NAME_TO_COUNT_MAP.get(filename) + 1);
        } else {
            this.fileName = filename;
        }
        NAME_TO_COUNT_MAP.put(filename, NAME_TO_COUNT_MAP.getOrDefault(filename, 0) + 1);
        this.scriptName = this.fileName.substring(0, 1).toUpperCase() + this.fileName.substring(1);
    }
}
