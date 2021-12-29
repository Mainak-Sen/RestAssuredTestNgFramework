package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {

    private static DataLoader dataLoader;
    private final Properties properties; //making it final so that once loaded it can never be changed

    //make constructor private to achieve singleton design pattern
    private DataLoader() {
        properties = PropertyUtils.loadProperties("src/test/resources/data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            //initialize only once when its null
            dataLoader = new DataLoader();
        }
        //return the initialized one only which was initialized when it was null
        return dataLoader;
    }

    public String getGetPlaylistId() {
        String prop = properties.getProperty("getPlaylistId");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property getPlaylistId is not specified in data.properties file");
        }
    }

    public String getUpdatePlaylistId() {
        String prop = properties.getProperty("updatePlaylistId");
        if (prop != null) {
            return prop;
        } else {
            throw new RuntimeException("Property updatePlaylistId is not specified in data.properties file");
        }
    }
}
