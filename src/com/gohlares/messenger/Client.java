package com.gohlares.messenger;

import rmi.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private final PeerInfo info;

    public Client(PeerInfo info) {
        this.info = info;
    }
    
    public void send() {
        try {
            Registry registry = LocateRegistry.getRegistry(info.getIp(), 1099);
            PeerInterface peer = (PeerInterface) registry.lookup("Peer");
//            peer.isAlive();
            boolean response = peer.send(info, new Message("oi"));
            
            System.out.println(response ? "Enviado." : "Não enviado.");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}