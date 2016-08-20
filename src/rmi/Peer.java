package rmi;

import rmi.interfaces.MessageInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.PeerInterface;
import java.rmi.RemoteException;
import java.util.UUID;

public class Peer implements PeerInterface{
    private UUID uuid = null;
    private PeerInfoInterface info;

    public Peer(PeerInfoInterface info) {
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
