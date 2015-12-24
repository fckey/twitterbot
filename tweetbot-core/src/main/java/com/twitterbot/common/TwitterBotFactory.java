package com.twitterbot.common;

import com.twitterbot.config.LoadProperties;
import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by fckey on 15/09/12.
 */
public class TwitterBotFactory {
    private static Logger LOGGER = Logger.getLogger(TwitterBotFactory.class);

    private static Twitter twitter;

    private TwitterBotFactory twitterBotFactory = new TwitterBotFactory();
    private TwitterBotFactory(){

    }

    public static void init(String account) {
        LOGGER.info("Init TwitterBotFactory");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        LoadProperties lp = LoadProperties.getInstance(account);
        cb.setDebugEnabled(lp.getDebug())
                .setOAuthConsumerKey(lp.getConsumerKey())
                .setOAuthConsumerSecret(lp.getConsumerSecret())
                .setOAuthAccessToken(lp.getAccessToken())
                .setOAuthAccessTokenSecret(lp.getAccessTokenSecret());
        twitter = new TwitterFactory(cb.build()).getInstance();
    }

    public static Twitter getTwitter() {
        if (twitter == null) {
            LOGGER.error("Factory is not initialized.");
            System.exit(1);
        }
        return twitter;
    }
}
