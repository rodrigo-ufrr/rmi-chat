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
            //Keep a socket open to listen to all the UDP traffic that is destined for this port
            recvSocket = new DatagramSocket(1098, InetAddress.getByName("0.0.0.0"));
            recvSocket.setBroadcast(true);

            sendSocket = new DatagramSocket();
            sendSocket.setBroadcast(true);

            while (true) {
                //Send a packet
                try {
                    byte[] sendBuf = Server.getInfo().getUUID().getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName("255.255.255.255"), 1098);
                    sendSocket.send(sendPacket);
                } catch (NullPointerException e) {
                    System.err.println("Nenhum UUID no servidor.");
                }

                //Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                recvSocket.receive(packet);

                //Packet received
                String ip = packet.getAddress().getHostAddress();
                String uuid = new String(packet.getData());

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
    
    public static void addListener(Callback listener) {
        listeners.add(listener);
    }
    
    public static void removeListener(Callback listener) {
        listeners.remove(listener);
    }
    
    public static void clearListeners(Callback listener) {
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
