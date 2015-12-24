package com.twitterbot;

import org.kohsuke.args4j.Option;

/**
 * Created by fckey on 2015/12/24.
 */
public class TwitterBotCommandLine {
    @Option(name = "-a", aliases = "--account", usage = "choose acccount")
    protected String account;

    public String getAccount() {
        return account;
    }
}
