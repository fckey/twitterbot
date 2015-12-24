package com.tweetbot;

import org.mockito.Mockito;
import twitter4j.Status;
import twitter4j.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fckey on 15/05/24.
 */
public class TwitterMockBuilder {
    List<String> messages = new ArrayList<>();
    public User mockUser1(){
        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("USER1");
        Mockito.when(user.getScreenName()).thenReturn("user1");
        Mockito.when(user.getLocation()).thenReturn("Tokyo");
        Mockito.when(user.getDescription()).thenReturn("profile");
        return user;
    }

    public User mockUser2(){
        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("USER2");
        Mockito.when(user.getScreenName()).thenReturn("user2");
        Mockito.when(user.getLocation()).thenReturn("Tokyo");
        Mockito.when(user.getDescription()).thenReturn("profile");
        return user;
    }

    public Status mockStatus(String message){
        Status status = Mockito.mock(Status.class);
        Mockito.when(status.getText()).thenReturn(message);
        Mockito.when(status.getInReplyToScreenName()).thenReturn("MockScreenName");
        Mockito.when(status.getText()).thenReturn("");
        Mockito.when(status.getFavoriteCount()).thenReturn(0);
        Mockito.when(status.getRetweetCount()).thenReturn(0);
        Mockito.when(status.getUser()).thenReturn(mockUser1());
        return status;
    }

    public List<Status> buildSimpleStatuses(){
        List<Status> statuses = new ArrayList<>();
        for(String me: messages){
            statuses.add(mockStatus(me));
        }
        return statuses;
    }

    public List<Status> buildFavedStatuses(){
        List<Status> statuses = new ArrayList<>();
        for(String me: messages){
            Status status = mockStatus(me);
            Mockito.when(status.getFavoriteCount()).thenReturn(10);
            statuses.add(status);
        }
        return statuses;
    }

    protected Status createRTedStatus(String message){
        Status status = mockStatus(message);
        Mockito.when(status.getRetweetCount()).thenReturn(10);
        return status;
    }

    public List<Status> buildRTedStatuses(String message){
        List<Status> statuses = new ArrayList<>();
        statuses.add(createRTedStatus(message));
        return statuses;
    }

    public List<Status> buildOthersRTStatuses(String message){
        List<Status> statuses = new ArrayList<>();
        Status status = createRTedStatus(message);
        Status originalStatus = createRTedStatus(message);
        Mockito.when(originalStatus.getUser()).thenReturn(mockUser2());
        Mockito.when(status.getRetweetedStatus()).thenReturn(originalStatus);
        statuses.add(status);
        return statuses;
    }

    public void addMessage(String me){
        messages.add(me);
    }
}
