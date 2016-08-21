package com.gohlares.messenger;

import rmi.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.Message;
import rmi.PeerInfo;

public class Client {
    private final PeerInfo info;

    public Client(PeerInfo info) {
        this.info = info;
    }
    
    public void send(String ip) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            PeerInterface peer = (PeerInterface) registry.lookup("Peer");
//            peer.isAlive();
            boolean response = peer.send(info, new Message("oi"));
            
            System.out.println(response ? "Enviado - " + peer.getInfo().getUserName() : "Não enviado.");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}