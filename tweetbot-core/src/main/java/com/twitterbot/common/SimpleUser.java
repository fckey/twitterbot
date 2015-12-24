package com.twitterbot.common;

/**
 * Created by fckey on 2015/12/25.
 */
public class SimpleUser {

    private static final long serialVersionUID = -5448266606847617015L;
    private long id;
    private String name;
    private String screenName;
    private String description;

    public SimpleUser() {
        //Jackson requires
    }

    public SimpleUser(long id, String name, String screenName, String description) {
        this.id = id;
        this.name = name;
        this.screenName = screenName;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
