package com.gohlares.messenger;

import com.gohlares.messenger.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {

    String host = (args.length < 1) ? null : args[0];
    try {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1098);
        PeerInterface stub = (PeerInterface) registry.lookup("Hello");
        String response = stub.getUUID();
        System.out.println("response: " + response);
    } catch (Exception e) {
        System.err.println("Client exception: " + e.toString());
        e.printStackTrace();
    }
    }
}