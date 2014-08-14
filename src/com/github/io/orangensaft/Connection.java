/*
 */

package com.github.io.orangensaft;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Just to make things a bit
 */
class Connection {
    public boolean failed=false;
    Socket s;
    OutputStream out;
    InputStream in;
    Scanner sc;
    PrintWriter p;
    
    Connection(String ip, int port) {
        try {
            s=new Socket(ip,port);
            out=s.getOutputStream();
            in=s.getInputStream();
            sc = new Scanner(in);
            p = new PrintWriter(out);
        } catch (IOException ex) {
            System.out.println("Fehler beim verbinden! "+ex.getMessage());
            failed=true;
        }
    }

    public InputStream getInputStream() {
        return in;
    }

    public OutputStream getOutputStream() {
        return out;
    }
    
    public void disconnect(){
        try {
            s.close();
        } catch (IOException ex) {
            System.out.println("Fehler bei disconnect! "+ex.getMessage());
        }
    }
    public void println(String s){
        p.print(s+"\r\n");
        p.flush();
    }
    public String read(){
        if(sc.hasNext()){
            return sc.nextLine();
        }
        return "";
    }
}
