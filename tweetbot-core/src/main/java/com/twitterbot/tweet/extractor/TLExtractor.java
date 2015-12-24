package com.twitterbot.tweet.extractor;

import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by fckey on 2015/12/24.
 */
public interface TLExtractor {
    public List<Status> extractTL() throws TwitterException;

    public List<Status> extractFreshTL() throws TwitterException;

    public List<Status> extractTLfrom(long sinceId) throws TwitterException;

}
