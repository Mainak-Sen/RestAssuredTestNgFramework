package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {

    private static ConfigLoader configLoader;
    private final Properties properties; //making it final so that once loaded it can never be changed

    //make constructor private to achieve singleton design pattern
    private ConfigLoader() {
        properties = PropertyUtils.loadProperties("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            //initialize only once when its null
            configLoader = new ConfigLoader();
        }
        //return the initialized one only which was initialized when it was null
        return configLoader;
    }

    public String getClientId() {
        String prop = properties.getProperty("client_id");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property client_id is not specified in config.properties file");
        }
    }

    public String getClientSecret() {
        String prop = properties.getProperty("client_secret");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property client_secret is not specified in config.properties file");
        }
    }

    public String getGrantType() {
        String prop = properties.getProperty("grant_type");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property grant_type is not specified in config.properties file");
        }
    }

    public String getRefreshToken() {
        String prop = properties.getProperty("refresh_token");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property refresh_token is not specified in config.properties file");
        }
    }

    public String getUserId() {
        String prop = properties.getProperty("user_id");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property user_id is not specified in config.properties file");
        }
    }
}
