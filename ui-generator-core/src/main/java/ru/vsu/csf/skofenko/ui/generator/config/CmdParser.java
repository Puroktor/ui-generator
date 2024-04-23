package ru.vsu.csf.skofenko.ui.generator.config;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.EnumUtils;

public class CmdParser {

    public static CmdArguments parseCmdArguments(String[] args) {
        Options options = new Options();

        Option engineOption = Option.builder("e")
                .longOpt("engine")
                .desc("Frontend engine that will be used for project generation, defaults to Angular")
                .hasArg()
                .build();
        options.addOption(engineOption);

        Option pathOption = Option.builder("p")
                .longOpt("path")
                .desc("Path to project configuration file")
                .hasArg()
                .required()
                .build();
        options.addOption(pathOption);

        Option overrideOption = Option.builder("o")
                .longOpt("override")
                .desc("Should override existing components (if any)")
                .type(Boolean.class)
                .build();
        options.addOption(overrideOption);

        Option startupOption = Option.builder("s")
                .longOpt("startup")
                .desc("Should startup generated project using system CLI (ng serve and etc)")
                .type(Boolean.class)
                .build();
        options.addOption(startupOption);

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            String engineName = cmd.getOptionValue(engineOption);
            UIGenerationEngine engine = EnumUtils.getEnum(UIGenerationEngine.class, engineName);
            if (engine == null) {
                engine = UIGenerationEngine.ANGULAR;
            }
            String path = cmd.getOptionValue(pathOption);
            boolean override = cmd.hasOption(overrideOption);
            boolean startup = cmd.hasOption(startupOption);
            return new CmdArguments(engine, path, override, startup);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ui-generator", options);
            System.exit(1);
            return null; // Will not be reached
        }
    }
}
