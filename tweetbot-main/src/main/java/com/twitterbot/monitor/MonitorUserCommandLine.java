package com.twitterbot.monitor;

import com.twitterbot.TwitterBotCommandLine;
import org.kohsuke.args4j.Option;

/**
 * Created by fckey on 2015/12/24.
 */
public class MonitorUserCommandLine extends TwitterBotCommandLine {
    @Option(name = "-u", aliases = "--user", usage = "Target User")
    private String user;

    public String getUser() {
        return user;
    }
}
