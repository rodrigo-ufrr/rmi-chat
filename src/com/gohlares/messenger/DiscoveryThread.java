package com.gohlares.messenger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por enviar os dados para Broadcast e
 */
public class DiscoveryThread implements Runnable {

    private DatagramSocket recvSocket;
    private DatagramSocket sendSocket;
    private static List<Callback> listeners = new ArrayList<>();

    @Override
    public void run() {
        try {
            //Abre um socket pra receber do broadcast
            recvSocket = new DatagramSocket(1098, InetAddress.getByName("0.0.0.0"));
            recvSocket.setBroadcast(true);

            //Abre um socket para enviar no broadcast
            sendSocket = new DatagramSocket();
            sendSocket.setBroadcast(true);

            while (true) {
                //Envia um pacote com o UUID do servidor para o broadcast
                try {
                    byte[] sendBuf = Server.getInfo().getUUID().getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName("255.255.255.255"), 1098);
                    sendSocket.send(sendPacket);
                } catch (NullPointerException e) {
                    System.err.println("Nenhum UUID no servidor.");
                }

                //Recebe um pacote
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                recvSocket.receive(packet);

                //Trata o pacote recebido
                String ip = packet.getAddress().getHostAddress();
                String uuid = new String(packet.getData());

                //Executa todos os callbacks
                for (Callback l : listeners) {
                    l.run(ip, uuid);
                }

                Thread.currentThread().sleep(3000);
            }
        } catch (IOException ex) {
            Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Adiciona um listener para quando um usuário for descoberto na rede.
     * @param listener A função de callback
     */
    public static void addListener(Callback listener) {
        listeners.add(listener);
    }
    
    /**
     * Remove um listener.
     * @param listener
     */
    public static void removeListener(Callback listener) {
        listeners.remove(listener);
    }
    
    /**
     * Remove todos os listeners
     */
    public static void clearListeners() {
        listeners.clear();
    }
    
    interface Callback {
        void run(String ip, String uuid);
    }

    public static DiscoveryThread getInstance() {
        return DiscoveryThreadHolder.INSTANCE;
    }

    private static class DiscoveryThreadHolder {

        private static final DiscoveryThread INSTANCE = new DiscoveryThread();
    }
}
