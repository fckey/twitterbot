package com.twitterbot.validation;

import twitter4j.Status;
import twitter4j.User;

import java.util.List;

/**
 * Created by fckey on 15/05/24.
 */
public class TweetValidator {

    private static int RT_THRESHOLD = 5;
    private static int  FAV_THRESHOLD = 4;

    public static boolean isReplyToMe(Status s, String myId){
        boolean isReplyToMe  = false;
        String message = s.getText();
        String replyToScreenName = s.getInReplyToScreenName();
        if( replyToScreenName != null && replyToScreenName.equals(myId))
        {
            isReplyToMe = true;
        }
        return isReplyToMe;
    }

    public static boolean isValuable(Status s, List<User> friends){
        boolean isSValuable = false;
        String userName = s.getUser().getScreenName();
        if(s.getRetweetedStatus() == null && //Not re-tweet of the others
                friends.stream().anyMatch(u -> u.getScreenName().equals(userName))){
            isSValuable =(s.getFavoriteCount() >= FAV_THRESHOLD) || (s.getRetweetCount()  >= RT_THRESHOLD);
        }

        return isSValuable;

    }
}
