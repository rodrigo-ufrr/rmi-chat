package com.gohlares.messenger;

import com.gohlares.messenger.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String args[]) {
	try {
	    Peer obj = new Peer();
	    PeerInterface stub = (PeerInterface) UnicastRemoteObject.exportObject(obj, 0);

	    Registry registry = LocateRegistry.createRegistry(1098);
	    registry.bind("PeerInterface", stub);

	    System.err.println("Server ready");
            registry.unbind("PeerInterface");

	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
