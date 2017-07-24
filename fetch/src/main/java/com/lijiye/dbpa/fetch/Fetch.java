package com.lijiye.dbpa.fetch;

import com.lijiye.dbpa.util.ConfigurationBuilder;
import com.sun.istack.internal.NotNull;
import org.apache.commons.cli.*;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

/**
 * Created by lijiye on 17-7-23.
 */
public class Fetch {
    private Configuration configuration;

    private static String DEFAULT_CONFIGURATION_FILE = "configuration.properties";
    private static Logger logger = Logger.getLogger(Fetch.class);


    private Fetch(String  filename) {
        try {
            configuration = ConfigurationBuilder.build(filename);
        } catch (ConfigurationException e) {
            logger.warn(e);
            logger.warn(String.format("Cannot find the configuration file %s. " +
                    "The system will use the default value.", filename));
            configuration = ConfigurationBuilder.build();
        }
    }


    public static void main(String[] args) throws ParseException {
        Options options = addOptions();
        CommandLine commandLine = parseOptions(options, args);
        if (commandLine.hasOption("h")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("Options Tip", options);
            return;
        }
        String filename = Fetch.class.getResource(DEFAULT_CONFIGURATION_FILE).getPath();
        Fetch fetch = new Fetch(commandLine.getOptionValue("c", filename));
    }

    private static Options addOptions() {
        Options options = new Options();
        options.addOption("h", false, "Print help for dbpa-fetch module.");
        options.addOption("c", false, "Specify the configuration file.");
        return options;
    }

    private static CommandLine parseOptions(@NotNull Options options, String[] args) throws ParseException {
        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        return commandLine;
    }
}
