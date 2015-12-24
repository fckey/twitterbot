package com.twitterbot.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fckey on 2015/12/24.
 */
public class LoadProperties {

    private static LoadProperties loadProperties = null;

    private boolean debug;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String filePath;

    private LoadProperties(String account) {

        Properties configuration = new Properties();
        try {
            InputStream inputStream = LoadProperties.class.getResourceAsStream(String.format("/%s.properties", account));
//                    = new FileInputStream(new File(
//                    LoadProperties.
//                    getClass().getClassLoader().getResource(
//                    String.format("%s.properties", account)).toString()));
            configuration.load(inputStream);

            debug = Boolean.getBoolean(configuration.getProperty("debug"));
            consumerKey = configuration.getProperty("oauth.consumerKey");
            consumerSecret = configuration.getProperty("oauth.consumerSecret");
            accessToken = configuration.getProperty("oauth.accessToken");
            accessTokenSecret = configuration.getProperty("oauth.accessTokenSecret");
            filePath = configuration.getProperty("file.path");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LoadProperties getInstance(String account) {
        if (loadProperties == null) {
            loadProperties = new LoadProperties(account);
        }
        return loadProperties;
    }

    public boolean getDebug() {
        return debug;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public String getFilePath() {
        return filePath;
    }
}
