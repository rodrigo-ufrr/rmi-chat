package com.gohlares.messenger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import rmi.Peer;
import rmi.PeerInfo;
import rmi.interfaces.PeerInterface;

public class Server {
    static Peer peer;
    static Registry registry;
    private String ip;
    private int port;
    private String username;

    public Server(int port, String username) {
        this.port = port;
        this.username = username;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            peer = new Peer(new PeerInfo(this.ip, this.username));
        } catch (UnknownHostException | RemoteException e) {
	    System.err.println("Server exception: " + e.toString());
        }
    }
    
    public void listen() {
        try {
            UnicastRemoteObject.unexportObject(peer, true);
	    PeerInterface peerInterface = (PeerInterface) UnicastRemoteObject.exportObject(peer, this.port);

	    registry = LocateRegistry.createRegistry(this.port);
	    registry.bind("Peer", peerInterface);

	    System.out.println("Server started on "+ this.ip +":"+ this.port +" as " + this.username);

	} catch (RemoteException | AlreadyBoundException e) {
	    System.err.println("Server exception: " + e.toString());
	}
    }
}
