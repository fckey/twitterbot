package com.twitterbot;

import com.twitterbot.common.TwitterBotFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import twitter4j.TwitterException;

/**
 * Created by fckey on 15/05/24.
 */
public class TwitterBotMain {

    private TwitterBotImpl twitterBotImpl;

    public TwitterBotMain() {
        twitterBotImpl = new TwitterBotImpl();
    }

    public void start() throws TwitterException {
        twitterBotImpl.run();
    }

    public static void main(String[] args) throws TwitterException {
        TwitterBotCommandLine cmdLine = new TwitterBotCommandLine();
        CmdLineParser parser = new CmdLineParser(cmdLine);
        try {
            parser.parseArgument(args);

            TwitterBotFactory.init(cmdLine.getAccount());
            TwitterBotMain twitterBotMain = new TwitterBotMain();
            twitterBotMain.start();
        } catch (CmdLineException e) {
            parser.printUsage(System.out);
            e.printStackTrace();
        }
    }
}
