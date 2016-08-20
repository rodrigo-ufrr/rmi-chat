package com.gohlares.messenger;

import static com.gohlares.messenger.Main.peer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import rmi.Peer;
import rmi.PeerInfo;
import rmi.interfaces.PeerInterface;

public class Server {
    public void teste() {
        try {
	    Peer peer = new Peer(new PeerInfo("192.168.2.22", "Rodrigost23"));
	    PeerInterface peerInterface = (PeerInterface) UnicastRemoteObject.exportObject(peer, 1099);

	    Registry registry = LocateRegistry.createRegistry(1099);
	    registry.bind("Peer", peerInterface);

	    System.err.println("Server ready");

	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
