package com.gohlares.messenger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import rmi.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Message;
import rmi.PeerInfo;

public class Client {
    private final PeerInfo info;
    private final int port;

    public Client(int port, String username) {
        this.port = port;

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.info = new PeerInfo(ip, username);
    }

    public void send(String ip, String body) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, this.port);
            PeerInterface peer = (PeerInterface) registry.lookup("Peer");
//            peer.isAlive();
            boolean response = peer.send(info, new Message(body));
            
            if(response) {
                System.out.println("Message sent to "+ peer.getInfo().getUserName() +": "+ body);
            }
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Client exception: "+ e.toString());
        }
    }
}