package com.lijiye.dbpa.fetch.util;

import com.sun.istack.internal.NotNull;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * Created by lijiye on 17-7-23.
 */
public class ConfigurationBuilder {
    private static Configurations configurations = new Configurations();

    private ConfigurationBuilder() {

    }

    public static Configuration build(@NotNull String filename) throws ConfigurationException {
        return configurations.properties(new File(filename));
    }

    public static Configuration build() {
        return new PropertiesConfiguration();
    }
}
