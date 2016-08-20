package com.gohlares.messenger;

import java.rmi.registry.Registry;
import rmi.Peer;
import rmi.PeerInfo;

public class Main {
    static Registry registry;
    static Peer peer;
    public static void main(String args[]) {
        Server s = new Server();
        s.teste();
        
//	Client c = new Client(new PeerInfo("192.168.122.1", "Rodrigo"));
//        c.send("10.42.0.125");
    }
}
