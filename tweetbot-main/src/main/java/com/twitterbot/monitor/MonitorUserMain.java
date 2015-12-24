package com.twitterbot.monitor;

import com.twitterbot.common.TwitterBotFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Created by fckey on 2015/12/24.
 */
public class MonitorUserMain {

    private MonitorUserImpl monitorUser;
    public MonitorUserMain(String user) {
        this.monitorUser = new MonitorUserImpl(user);
    }

    private void start() {
        monitorUser.run();
    }

    public static void main(String[] args) {
        MonitorUserCommandLine cmdLine = new MonitorUserCommandLine();
        CmdLineParser parser = new CmdLineParser(cmdLine);
        try {
            parser.parseArgument(args);

            TwitterBotFactory.init(cmdLine.getAccount());
            MonitorUserMain monitorUserMain = new MonitorUserMain(cmdLine.getUser());
            monitorUserMain.start();
        } catch (CmdLineException e) {
            parser.printUsage(System.out);
            e.printStackTrace();
        }
    }
}
