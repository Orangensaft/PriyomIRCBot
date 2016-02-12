/*
 */
package com.github.io.orangensaft;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bot by b1tshift
 */
public class IRCBot {

    /**
     * @param args the command line arguments
     * args[0] - Nickname
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        if(args.length!=1){
            System.out.println("You have to provide a nickname as parameter!");
            System.exit(0);
        }
        String host = "irc.freenode.net";
        int port = 6667;
        String nick = args[0];
        String ident = args[0];
        String real = "priyom bot by b1tshift";
        Pattern p = Pattern.compile("http://[0-9]+\\.t\\.hetmer\\.cz");
        Matcher match;
        Connection con = new Connection(host, port);
        if (con.failed) {
            System.exit(0);
        }
        con.println("USER " + ident + " " + host + " bla :" + real);
        con.println("NICK " + nick);
        con.println("JOIN #priyom");
        String line;
        String cmd[];
        String code;
        String from;
        String msg;
        String inChan;
        while (true) {
            line = con.read();
            cmd = line.split(" :");
            if (cmd[0].split(" ").length < 2) {
                code = cmd[0];
            } else {
                code = cmd[0].split(" ")[1];
            }
            if (code.equals("PING")) {
                con.println("PONG " + host);
            }
            if (code.equals("NOTICE")){
                msg = line.substring(cmd[0].length() + 2);
                System.out.println("NOTICE: "+msg);
            }
            if (code.equals("JOIN")){
                from = line.split("!")[0].substring(1);
                System.out.println(from+" joined the channel");
            }
            if (code.equals("PART")){
                from = line.split("!")[0].substring(1);
                System.out.println(from+" left");
            }
            if (code.equals("PRIVMSG")) {
                from = line.split("!")[0].substring(1);
                msg = line.substring(cmd[0].length() + 2);
                inChan = cmd[0].split(" ")[2];
                if (inChan.equals(nick)) {
                    System.out.println("*priv* " + from + ": " + msg);
                } else {
                    System.out.println(from + ": " + msg);
                    match = p.matcher(line);
                    while(match.find()){
                        System.out.println("FOUND! "+match.group());
                        java.awt.Desktop.getDesktop().browse(java.net.URI.create(match.group()));
                    }
                }
            }
        }
    }
}
