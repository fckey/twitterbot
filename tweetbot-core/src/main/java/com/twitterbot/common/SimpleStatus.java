package com.twitterbot.common;

import java.util.Date;

/**
 * Created by fckey on 2015/12/24.
 */
public class SimpleStatus {
    private static final long serialVersionUID = -6461195536943679985L;
    private long id;
    private String text;
    private int favoriteCount;
    private long retweetCount;
    private String lang;
    private SimpleUser user;
    private Date createdAt;

    public SimpleStatus() {
        //JackSon requires
    }

    /**
     *
     * @param id
     * @param text
     * @param favoriteCount
     * @param retweetCount
     * @param lang
     * @param user
     * @param createdAt
     */
    public SimpleStatus(long id, String text, int favoriteCount, long retweetCount, String lang, Date createdAt, SimpleUser user) {
        this.id = id;
        this.text = text;
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
        this.lang = lang;
        this.user = user;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public SimpleUser getUser() {
        return user;
    }

    public void setUser(SimpleUser user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
