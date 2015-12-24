package com.twitterbot.tweet.extractor;

import org.apache.log4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by fckey on 2015/12/24.
 */
public class TLUserExtractor extends AbstractTLExtractor {
    private static Logger LOGGER = Logger.getLogger(TLUserExtractor.class);

    private final String user;
    public TLUserExtractor(Twitter t, String user) {
        super(t);
        this.user = user;
    }

    @Override
    protected List<Status> extractTwitterTL() throws TwitterException {
        return twitter.getUserTimeline(user,p);
    }

    public String getUser() {
        return user;
    }
}
