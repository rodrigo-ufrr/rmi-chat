package com.gohlares.messenger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import rmi.interfaces.PeerInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Message;
import rmi.PeerInfo;

/**
 * Classe responsável por enviar mensagens para outros usuários
 */
public class Client {
    private static Registry registry;
    private final PeerInfo info;
    private final int port;

    /**
     * @param port A porta a ser usada pelo RMI.
     * @param username O nome de usuário deste peer.
     */
    public Client(int port, String username) {
        this.port = port;

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.info = Server.getInfo();
    }

    /**
     * Envia uma mensagem para outro usuário.
     * @param ip O IP de destino.
     * @param body O conteúdo da mensagem.
     */
    public void send(String ip, String body) {
        
        new Thread(()->{
            boolean response;
            try {
                PeerInterface peer = get(ip);
                response = peer.send(info, new Message(body));

                if(response) {
                    System.out.println("Message sent to "+ peer.getInfo().getUserName() +": "+ body);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }
    
    /**
     * Pega um Peer dado um endereço de IP.
     */
    public PeerInterface get(String ip) {
        try {
            registry = LocateRegistry.getRegistry(ip, this.port);
            return (PeerInterface) registry.lookup("Peer");

        } catch (RemoteException | NotBoundException ex) {
            System.err.println("Erro ao obter usuário. "+ ex.getMessage());
            return null;
        }
    }
}