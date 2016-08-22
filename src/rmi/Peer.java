package rmi;

import rmi.interfaces.MessageInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.PeerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Peer extends UnicastRemoteObject implements PeerInterface{
    
    protected PeerInfo info;

    public Peer(PeerInfo info) throws RemoteException{
        super();
        this.info = info;
    }

    @Override
    public String getUUID() {
        return this.info.getUUID();
    }

    @Override
    public PeerInfoInterface getInfo() throws RemoteException {
        return this.info;
    }

    @Override
    public boolean send(PeerInfoInterface from, MessageInterface msg) throws RemoteException {
        System.err.println("Message received from "+ from.getUserName() +": " + msg.getBody());
        return true;
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }
}
