package com.lijiye.dbpa.fetch;

import com.lijiye.dbpa.fetch.builder.GetMatchDetailRunnableBuilder;
import com.lijiye.dbpa.util.ConfigurationBuilder;
import com.lijiye.dbpa.util.Counter;
import org.apache.commons.cli.*;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by lijiye on 17-7-23.
 */
public class Fetch {
    private Configuration configuration;

    private static Fetch fetch;
    private static final String DEFAULT_CONFIGURATION_FILE = "configuration.properties";
    private static final Logger logger = LoggerFactory.getLogger(Fetch.class);


    private Fetch() {
        String filename = Fetch.class.getClassLoader().getResource(DEFAULT_CONFIGURATION_FILE).getPath();
        try {
            configuration = ConfigurationBuilder.build(filename);
        } catch (ConfigurationException e) {
            logger.warn(e.toString());
            logger.warn("Cannot find the configuration file {}. The system will use the default value.", filename);
        }
    }

    private void start() {
        GetMatchDetailRunnableBuilder getMatchDetailRunnableBuilder = new GetMatchDetailRunnableBuilder(new Counter(3021004225L));
        new Thread(getMatchDetailRunnableBuilder).start();
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String[] args) throws ParseException {
        fetch = new Fetch();
        fetch.start();
    }

    public static Fetch getFetch() {
        return fetch;
    }

}
