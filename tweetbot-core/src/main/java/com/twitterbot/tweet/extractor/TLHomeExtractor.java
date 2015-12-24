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
public class TLHomeExtractor extends AbstractTLExtractor {
    private static Logger LOGGER = Logger.getLogger(TLHomeExtractor.class);
    protected Paging p;

    public TLHomeExtractor(Twitter t) {
        super(t);
    }

    @Override
    protected List<Status> extractTwitterTL() throws TwitterException {
        return twitter.getHomeTimeline(p);
    }



    public void refreshPage() {
        p = new Paging();
    }

}
