package rmi;

import java.util.UUID;

import rmi.interfaces.PeerInfoInterface;

public class PeerInfo implements PeerInfoInterface{
    private static final long serialVersionUID = 1;
    private UUID uuid = null;
    private final String ip;
    private final String username;

    public PeerInfo(String ip, String username) {
        this.ip = ip;
        this.username = username;
    }

    @Override
    public String getUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        return this.uuid.toString();
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public String getUserName() {
        return this.username;
    }
    
}
