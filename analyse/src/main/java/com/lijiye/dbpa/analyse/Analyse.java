package com.lijiye.dbpa.analyse;

import com.facebook.nifty.core.NiftyBootstrap;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.facebook.nifty.guice.NiftyModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import com.lijiye.dbpa.analyse.thrift.DbpaNiftyModule;
import com.lijiye.dbpa.thrift.DbpaService;
import com.lijiye.dbpa.thrift.MatchDetail;
import com.lijiye.dbpa.util.ConfigurationBuilder;
import io.netty.internal.tcnative.SessionTicketKey;
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by lijiye on 17-8-1.
 */
public class Analyse {
    private static final String DEFAULT_CONFIGURATION_FILE = "configuration.properties";
    private static final Integer DEFAULT_PORT = 8080;
    private static final Integer DEFAULT_TIMEOUT = 5000;
    private static final String PORT = "system.thrift.server.port";
    private static final String TIMEOUT = "system.thrift.server.timeout";
    private static final Logger logger = LoggerFactory.getLogger(Analyse.class);

    private static Analyse analyse;
    private Configuration configuration;

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
        int port = configuration.getInt(PORT, DEFAULT_PORT);
        int timeout = configuration.getInt(TIMEOUT, DEFAULT_TIMEOUT);
        NiftyModule niftyModule = new DbpaNiftyModule(port, timeout);
    }
    public static void main(String[] args) {
        analyse = new Analyse();
        analyse.start();
    }

    public static Analyse getAnalyse() {
        return analyse;
    }
}
