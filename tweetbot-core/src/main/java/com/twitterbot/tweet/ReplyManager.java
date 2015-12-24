package com.twitterbot.tweet;

import twitter4j.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fckey on 15/05/24.
 */
public class ReplyManager {
    private static Logger LOGGER = Logger.getLogger(ReplyManager.class);
    private final static List<String> messageList = Arrays.asList("1人の商人無数の被害", "あかんでしょ", "まいったね", "まずいですよ", "なるほどね", "うるせー", "いかんのか？");
    private  int messageId = 0;

    private final Twitter twitter;
    public ReplyManager(Twitter twitter){
        this.twitter = twitter;
    }

    public void tweet(String message) throws TwitterException {
        Status status = twitter.updateStatus(message);
        LOGGER.info("Successfully updated the status to [" + status.getText() + "].");
    }

    private String atUser(User user){
        return String.format("@%s ",user.getScreenName());
    }

    public void reply(Status status) throws TwitterException {
        String message = atUser(status.getUser()) + getMessage();
        StatusUpdate su = new StatusUpdate(message);
        su.setInReplyToStatusId(status.getId());
//        twitter.updateStatus(su);
        LOGGER.info(String.format("Replied to %s as $s",status.getUser(),message));
    }

    public void replyToUsers(List<Status> statuses)  {
        statuses.forEach((s) -> {
            try{
                reply(s);
            }catch (TwitterException e){
                LOGGER.error(String.format("Tweet wasn't completed for %s", s.getUser()),e);
            }
        });

    }

    public void replyToNewUser(User user) throws TwitterException {
        String message = atUser(user) + getMessage();
        StatusUpdate su = new StatusUpdate(message);
        twitter.updateStatus(su);
    }

    private String getMessage(){
        String value = messageList.get(messageId);
        messageId = ++messageId% messageList.size();
        return value;
    }

}
