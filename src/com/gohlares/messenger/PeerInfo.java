package com.gohlares.messenger;

import java.util.UUID;

import com.gohlares.messenger.interfaces.PeerInfoInterface;

public class PeerInfo implements PeerInfoInterface{
    private UUID uuid = null;
    private String ip;
    private String username;

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
