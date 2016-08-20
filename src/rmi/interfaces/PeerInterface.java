package rmi.interfaces;

import com.gohlares.messenger.interfaces.MessageInterface;
import com.gohlares.messenger.interfaces.PeerInfoInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PeerInterface extends Remote {
    public String getUUID() throws RemoteException;
    public PeerInfoInterface getInfo() throws RemoteException;
    public boolean send(PeerInfoInterface from, MessageInterface msg) throws RemoteException;
    public boolean isAlive() throws RemoteException;
}