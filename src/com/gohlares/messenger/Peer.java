package com.gohlares.messenger;

import com.gohlares.messenger.interfaces.MessageInterface;
import com.gohlares.messenger.interfaces.PeerInfoInterface;
import com.gohlares.messenger.interfaces.PeerInterface;
import java.rmi.RemoteException;

public class Peer implements PeerInterface{

    @Override
    public String getUUID() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PeerInfoInterface getInfo() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean send(MessageInterface msg) throws RemoteException {
        System.out.println("Recebido: " + msg.getBody());
        return false;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAlive() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
