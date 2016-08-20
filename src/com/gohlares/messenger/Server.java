package com.gohlares.messenger;
	
import com.gohlares.messenger.interfaces.PeerInterface;
import com.gohlares.messenger.interfaces.MessageInterface;
import com.gohlares.messenger.interfaces.PeerInfoInterface;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
	
public class Server implements PeerInterface {
	
    public Server() {}

    public String sayHello() {
	return "Olá, Marilene!";
    }
	
    public static void main(String args[]) {
	
	try {
	    Server obj = new Server();
	    PeerInterface stub = (PeerInterface) UnicastRemoteObject.exportObject(obj, 0);

	    // Bind the remote object's stub in the registry
	    Registry registry = LocateRegistry.createRegistry(1098);
	    registry.bind("Hello", stub);

	    System.err.println("Server ready");
	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }

    @Override
    public String getUUID() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean send(MessageInterface msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAlive() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PeerInfoInterface getInfo() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}