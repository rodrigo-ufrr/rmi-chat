package com.gohlares.messenger;

import com.gohlares.messenger.interfaces.MessageInterface;
import com.gohlares.messenger.interfaces.PeerInfoInterface;
import rmi.interfaces.PeerInterface;
import java.rmi.RemoteException;
import java.util.UUID;

public class Peer implements PeerInterface{
    private UUID uuid = null;
    private PeerInfo info;

    public Peer(PeerInfo info) {
        this.info = info;
    }

    @Override
    public String getUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        return this.uuid.toString();
    }

    @Override
    public PeerInfoInterface getInfo() throws RemoteException {
        return this.info;
    }

    @Override
    public boolean send(PeerInfoInterface from, MessageInterface msg) throws RemoteException {
        System.out.println("Recebido de "+ from.getUserName() +": " + msg.getBody());
        return true;
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }
    
}
