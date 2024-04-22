package ru.vsu.csf.skofenko.ui.generator.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CmdArguments {
    private UIGenerationEngine engine;
    private String configPath;
    private boolean override;
    private boolean startup;
}
