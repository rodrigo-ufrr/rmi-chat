package rmi;

import com.gohlares.messenger.Server;
import rmi.interfaces.MessageInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.PeerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer extends UnicastRemoteObject implements PeerInterface{
    
    protected PeerInfo info;
    private Server.MessageCallback callback = null;

    public Peer(PeerInfo info) throws RemoteException{
        super();
        this.info = info;
    }

    public Peer(PeerInfo info, Server.MessageCallback callback) throws RemoteException{
        this(info);
        this.callback = callback;
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
        if (callback != null) {
            try {
                callback.run(from, msg);
            } catch (Exception ex) {
                Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }
}
