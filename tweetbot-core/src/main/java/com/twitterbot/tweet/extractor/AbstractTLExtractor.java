package com.twitterbot.tweet.extractor;

import org.apache.log4j.Logger;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by fckey on 2015/12/24.
 */
public abstract class AbstractTLExtractor implements TLExtractor{
    private static Logger LOGGER = Logger.getLogger(AbstractTLExtractor.class);
    protected Paging p;

    protected final Twitter twitter;
    public AbstractTLExtractor(Twitter t) {
        this.twitter = t;
        this.p = new Paging();
    }

    @Override
    public List<Status> extractTL() throws TwitterException {
        LOGGER.info("Get timeline");
        List<Status> statuses = extractTwitterTL();
        updatePage(statuses);
        return statuses;
    }

    @Override
    public List<Status> extractFreshTL() throws TwitterException {
        LOGGER.info("Get timeline");
        Paging p = new Paging();
        List<Status> statuses = extractTwitterTL();
        updatePage(statuses);
        return statuses;
    }

    @Override
    public List<Status> extractTLfrom(long sinceId) throws TwitterException {
        LOGGER.info("Get timeline");
        Paging p = new Paging(sinceId);
        List<Status> statuses = extractTwitterTL();
        updatePage(statuses);
        return statuses;
    }

    protected abstract List<Status> extractTwitterTL() throws TwitterException;

    protected void updatePage(List<Status> statuses) {
        p.setSinceId(statuses.get(statuses.size()-1).getId());
    }
}
