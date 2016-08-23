package rmi;

import java.util.Objects;
import java.util.UUID;

import rmi.interfaces.PeerInfoInterface;

public class PeerInfo implements PeerInfoInterface{
    private static final long serialVersionUID = 1;
    
    protected String uuid;
    protected String ip;
    protected String userName;

    public PeerInfo(String ip, String username) {
        this(ip, username, null);
    }

    public PeerInfo(String ip, String username, String uuid) {
        if (uuid == null && this.uuid == null) {
            // TODO: Save UUID on disk
            this.uuid = UUID.randomUUID().toString();
        } else {
            this.uuid = uuid;
        }
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

    @Override
    public String toString() {
        return this.getUserName();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PeerInfoInterface) {
            return Objects.equals(this.getUUID(), ((PeerInfoInterface) o).getUUID());
        } else {
            System.out.println("epa");
            return super.equals(o);
        }
    }
}
