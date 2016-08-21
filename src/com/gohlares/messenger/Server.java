package com.gohlares.messenger;

import static com.gohlares.messenger.Main.peer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Message;
import rmi.Peer;
import rmi.PeerInfo;
import rmi.interfaces.PeerInterface;

public class Server {
    static Peer peer;
    private String port = null;

    public Server() {
        try {
            peer = new Peer(new PeerInfo("192.168.2.22", "Rodrigost23"));
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void teste() {
        try {
            UnicastRemoteObject.unexportObject(peer, true);
	    PeerInterface peerInterface = (PeerInterface) UnicastRemoteObject.exportObject(peer, 1099);

	    Registry registry = LocateRegistry.createRegistry(1099);
	    registry.bind("Peer", peerInterface);

	    System.err.println("Server ready");
            peer.send(new PeerInfo(null, "OI"), new Message("oi"));

	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
