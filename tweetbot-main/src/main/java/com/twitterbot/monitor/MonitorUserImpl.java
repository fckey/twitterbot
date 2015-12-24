package com.twitterbot.monitor;

import com.twitterbot.common.SimpleStatus;
import com.twitterbot.common.SimpleUser;
import com.twitterbot.common.TwitterBotFactory;
import com.twitterbot.config.LoadProperties;
import com.twitterbot.dao.FileDao;
import com.twitterbot.domain.JsonConverter;
import com.twitterbot.tweet.extractor.TLExtractor;
import com.twitterbot.tweet.extractor.TLUserExtractor;
import org.apache.log4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.IOException;
import java.util.*;

/**
 * Created by fckey on 2015/12/24.
 */
public class MonitorUserImpl {
    private static Logger LOGGER = Logger.getLogger(MonitorUserImpl.class);
    private final Twitter twitter;
    private final String user;
    private TLExtractor tlExtractor;
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;

    private String filePath;

    private Timer timer;

    public MonitorUserImpl(String user) {
        this.twitter = TwitterBotFactory.getTwitter();
        this.tlExtractor = new TLUserExtractor(twitter, user);
        this.user = user;
        filePath = LoadProperties.getInstance("fkyish").getFilePath();
    }

    public void run() {
        timer = new Timer("Tweet-Monitor-Thread", false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<Status> statuses = null;
                try {
                    statuses = tlExtractor.extractTL();
                    if (statuses == null || statuses.isEmpty()){
                        LOGGER.info("No valid status was extracted.");
                        return;
                    }
                    LOGGER.info(String.format("%d statuses were extracted.", statuses.size()));
                    doAction(statuses);
                } catch (TwitterException e) {
                    //TODO
                    LOGGER.error("Couldn't get the timeline");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }, SECOND, 5 * MINUTE);
    }

    public void doAction(List<Status> statuses) throws TwitterException {
        List<SimpleStatus> results = new ArrayList<>();
        String fileName = String.format("%sTweet", user);
        for (Status status : statuses) {
            if (!results.contains(status)) {
                LOGGER.info(String.format("Add %d to results", status.getId()));
                results.add(toSimpleStatus(status));
            }
            makeChainIfReply(status, statuses, results);
        }
        try {
            LOGGER.info(String.format("Convert %d results into JSON", results.size()));
            String json = JsonConverter.convertToString(results);
            FileDao.writeMsg(String.format("%s%s%d.txt", filePath, fileName, System.currentTimeMillis()), json);
        } catch (IOException e) {
            LOGGER.error("Failed to convert status to JOSN");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void makeChainIfReply(Status status,List<Status> statuses, List<SimpleStatus> results) {
        try {
            if (status.getInReplyToStatusId() > 0) {
                LOGGER.debug(String.format("msg:%s, to:%s", status.getText(), status.getInReplyToScreenName()));
                Status reply = twitter.showStatus(status.getInReplyToStatusId());
                LOGGER.info(String.format("Add %d to results", reply.getId()));
                results.add(toSimpleStatus(reply));
                Optional<Status> replyBack = statuses.stream()
                        .filter(s -> s.getId() == reply.getInReplyToStatusId()).findFirst();

                if (replyBack.isPresent()) {
                    Status repliedStatus = replyBack.get();
                    LOGGER.info(String.format("Add %d to results", repliedStatus.getId()));
                    results.add(toSimpleStatus(repliedStatus));
                    makeChainIfReply(repliedStatus, statuses, results);
                }
            }
        } catch (TwitterException e) {
            LOGGER.warn(String.format("Failed to get reply to %s from %s", status.getInReplyToScreenName(), status.getText()));
        }
    }

    private SimpleStatus toSimpleStatus(Status s) {
        User u = s.getUser();
        return new SimpleStatus(s.getId(),s.getText(),s.getFavoriteCount(),
                s.getRetweetCount(),s.getLang(), s.getCreatedAt(),
                new SimpleUser(u.getId(), u.getName(), u.getScreenName(), u.getDescription()));
    }
}
