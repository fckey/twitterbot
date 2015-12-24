package com.twitterbot;

import com.twitterbot.common.TwitterBotFactory;
import com.twitterbot.tweet.extractor.TLExtractor;
import com.twitterbot.tweet.extractor.TLHomeExtractor;
import com.twitterbot.validation.TweetValidator;
import com.twitterbot.tweet.ReplyManager;
import org.apache.log4j.Logger;
import twitter4j.*;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Created by fckey on 15/05/24.
 */
public class TwitterBotImpl {

    private static Logger LOGGER = Logger.getLogger(TwitterBotImpl.class);
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private final Twitter twitter;
    private ReplyManager replyManager;
    private TLExtractor tlExtractor;
    private Long cursor = -1L;
    private String myAccount;

    private PagableResponseList<User> followers;
    private PagableResponseList<User> friends;
    private Timer timer;


    public TwitterBotImpl() {
        this.twitter = TwitterBotFactory.getTwitter();
        this.replyManager = new ReplyManager(twitter);
        this.tlExtractor = new TLHomeExtractor(twitter);
        try {
            myAccount = twitter.getScreenName();
            init();
        } catch (TwitterException e) {
            System.exit(1);
        }
    }

    public void init() throws TwitterException {
        followers = getFollowersList();
        friends = getFriendsList();
        timer = new Timer("Tweet-Listen-Thread", false);
    }

    /**
     * @throws TwitterException
     */
    public void run() throws TwitterException {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<Status> statuses = null;
                try {
                    statuses = tlExtractor.extractTL();
                } catch (TwitterException e) {
                    //TODO
                    LOGGER.error("Couldn't get the timeline");
                    e.printStackTrace();
                }
                if(statuses ==null||statuses.isEmpty()) return;
                doAction(statuses);


            }
        }, SECOND, 20 * MINUTE);

    }

    void doAction(List<Status> statuses){

        List<Status> replyToMeStatuses = statuses.stream().
                filter(s -> TweetValidator.isReplyToMe(s, myAccount)).
                collect(Collectors.toList());

        //Reply to reply messages
        replyManager.replyToUsers(replyToMeStatuses);

        //To keep popular Message
        List<Status> valuableStatuses = statuses.stream().
                filter(s -> TweetValidator.isValuable(s, friends)).
                collect(Collectors.toList());

        //Store popular messages of friends
    }

    private PagableResponseList<User> getFollowersList() throws TwitterException {
        PagableResponseList<User> users = twitter.getFollowersList(twitter.getScreenName(), cursor);
        return users;
    }

    private PagableResponseList<User> getFriendsList() throws TwitterException {
        PagableResponseList<User> users = twitter.getFriendsList(twitter.getScreenName(), cursor);
        return users;
    }


    private void checkFollowers() throws TwitterException {
        PagableResponseList<User> currentFollowers = getFollowersList();
        for (User user : currentFollowers) {
            /*
            if(isNewFollower(user)){
                replyToNewUser(user);
                followers.add(user);
            }
            */
        }
    }

    private boolean isNewFollower(User user) {
        return !followers.contains(user);
    }
}
