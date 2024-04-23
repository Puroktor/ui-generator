package ru.vsu.csf.skofenko.ui.generator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vsu.csf.skofenko.ui.generator.api.UI;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UIConfigParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static UI parseConfig(UIGenerationEngine engine, String configFile) {
        try {
            Path path = Paths.get(configFile);
            return mapper.readValue(path.toFile(), engine.getUiClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
