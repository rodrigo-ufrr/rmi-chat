package com.gohlares.messenger;

import rmi.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    static Registry registry;
    static Peer peer;
    public static void main(String args[]) {
//        Client c = new Client(new PeerInfo("192.168.2.6", "bunda"));
//        c.send();
	try {
	    peer = new Peer(new PeerInfo("192.168.2.22", "Rodrigost23"));
	    PeerInterface peerInterface = (PeerInterface) UnicastRemoteObject.exportObject(peer, 1099);

	    registry = LocateRegistry.createRegistry(1099);
	    registry.bind("Peer", peerInterface);

	    System.err.println("Server ready");

	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
