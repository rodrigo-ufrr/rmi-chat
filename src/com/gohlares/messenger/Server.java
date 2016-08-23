package com.gohlares.messenger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Peer;
import rmi.PeerInfo;
import rmi.interfaces.MessageInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.PeerInterface;

/**
 * Classe responsável por receber mensagens.
 */
public class Server {
    static Peer peer;
    static Registry registry;
    private String ip;
    private int port;
    private String username;

    /**
     * @param port A porta de comunicação do chat
     * @param username O nome de usuário que aparecerá para os outros usuários.
     */
    public Server(int port, String username) {
        this.port = port;
        this.username = username;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
	    System.err.println("Server exception: " + e.toString());
        }
    }
    
    /**
     * Habilita o recebimento de mensagens.
     * @param callback Função a ser executada ao receber uma mensagem.
     */
    public void listen(MessageCallback callback) {
        try {
            peer = new Peer(new PeerInfo(this.ip, this.username), callback);

            UnicastRemoteObject.unexportObject(peer, true);
	    PeerInterface peerInterface = (PeerInterface) UnicastRemoteObject.exportObject(peer, this.port);

	    registry = LocateRegistry.createRegistry(this.port);
	    registry.bind("Peer", peerInterface);

	    System.out.println("Server started on "+ this.ip +":"+ this.port +" as " + this.username);

	} catch (RemoteException | AlreadyBoundException e) {
	    System.err.println("Server exception: " + e.toString());
	}
    }
    
    /**
     * Interface para a função de callback, que deve ser implementada para uso
     * como parâmetro da função listen().
     */
    public interface MessageCallback {
        void run(PeerInfoInterface from, MessageInterface message);
    }
    
    /**
     * Obtém as informações deste usuário.
     * @return Retorna o PeerInfo do usuário.
     */
    public static PeerInfo getInfo() {
        try {
            return (PeerInfo) Server.peer.getInfo();
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
