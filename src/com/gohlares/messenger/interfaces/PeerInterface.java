package com.gohlares.messenger.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PeerInterface extends Remote {
    public String getUUID() throws RemoteException;
    public PeerInfoInterface getInfo() throws RemoteException;
    public boolean send(MessageInterface msg) throws RemoteException;
    public boolean isAlive() throws RemoteException;
}