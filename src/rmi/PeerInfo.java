package rmi;

import java.util.UUID;

import rmi.interfaces.PeerInfoInterface;

public class PeerInfo implements PeerInfoInterface{
    private static final long serialVersionUID = 1;
    
    protected String uuid;
    protected String ip;
    protected String userName;

    public PeerInfo(String ip, String username) {
        this.uuid = UUID.randomUUID().toString();
        this.ip = ip;
        this.userName = username;
    }

    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }
    
}
