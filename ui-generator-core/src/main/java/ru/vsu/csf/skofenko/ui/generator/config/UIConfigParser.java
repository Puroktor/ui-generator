package ru.vsu.csf.skofenko.ui.generator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.csf.skofenko.ui.generator.api.UI;

import java.io.File;
import java.io.IOException;

public class UIConfigParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static UI parseConfig(UIGenerationEngine engine, String configFile) {
        try {
            return mapper.readValue(new File(configFile), engine.getUiClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
