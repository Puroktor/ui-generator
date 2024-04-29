package ru.vsu.csf.skofenko.ui.generator;

import ru.vsu.csf.skofenko.ui.generator.api.UI;
import ru.vsu.csf.skofenko.ui.generator.config.CmdArguments;
import ru.vsu.csf.skofenko.ui.generator.config.CmdParser;
import ru.vsu.csf.skofenko.ui.generator.config.UIConfigParser;

public class UIGenerator {

    public static void main(String[] args) {
        CmdArguments arguments = CmdParser.parseCmdArguments(args);
        UI ui = UIConfigParser.parseConfig(arguments.getEngine(), arguments.getConfigPath());
        ui.create(arguments.isOverride());
        if (arguments.isStartup()) {
            ui.run();
        }
    }
}
