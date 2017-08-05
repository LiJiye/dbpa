package com.lijiye.dbpa.analyse;

import com.facebook.nifty.core.NiftyBootstrap;
import com.facebook.nifty.guice.NiftyModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import com.lijiye.dbpa.analyse.thrift.DbpaNiftyModule;
import com.lijiye.dbpa.util.ConfigurationBuilder;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.lijiye.dbpa.analyse.Config.*;


/**
 * Created by lijiye on 17-8-1.
 */
public class Analyse {

    private static final Logger logger = LoggerFactory.getLogger(Analyse.class);

    private static Analyse analyse;
    private Configuration configuration;
    private NiftyBootstrap bootstrap;

    private Analyse() {
        String filename = Analyse.class.getClassLoader().getResource(DEFAULT_CONFIGURATION_FILE).getPath();
        try {
            configuration = ConfigurationBuilder.build(filename);
        } catch (ConfigurationException e) {
            logger.warn(e.toString());
            logger.warn("Cannot find the configuration file {}. The system will use the default value.", filename);
        }
    }

    private void start() {
        bootstrap = Guice.createInjector(
                Stage.PRODUCTION,
                new DbpaNiftyModule()
        ).getInstance(NiftyBootstrap.class);
        bootstrap.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                bootstrap.stop();
            }
        });
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String[] args) {
        analyse = new Analyse();
        analyse.start();
    }

    public static Analyse getAnalyse() {
        return analyse;
    }


}
