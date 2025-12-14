package serveur;

import chess.TableJeu;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Client {
    
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort = 1302;
    private byte[] buffer;
    private static final int BUFFER_SIZE = 1024;
    private TableJeu tableJeu;
    public int idPlayer;
   
    public Client(String serverHost) throws SocketException {
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getByName(serverHost);
            this.buffer = new byte[BUFFER_SIZE];
        } catch (Exception e) {
            throw new SocketException("Erreur lors de l'initialisation du client UDP: " + e.getMessage());
        }
    }

    public void setTableJeu(TableJeu tableJeu) {
        this.tableJeu = tableJeu;
    }
    
    public void send(String message) throws IOException {
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
        socket.send(packet);
    }
    

    public void send(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
        socket.send(packet);
    }
    
    public String receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }
    
    public byte[] receiveBytes() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        byte[] data = new byte[packet.getLength()];
        System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());
        return data;
    }
    
    
    public String receiveWithTimeout(int timeout) throws IOException {
        try {
            socket.setSoTimeout(timeout);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (SocketTimeoutException e) {
            return null;
        } finally {
            socket.setSoTimeout(0); // Remet le timeout à infini
        }
    }
    
    
    public void setTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
    }
    
    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }
    
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
    
    public int getLocalPort() {
        return socket.getLocalPort();
    }
    
    public InetAddress getServerAddress() {
        return serverAddress;
    }
    
    public int getServerPort() {
        return serverPort;
    }

    public void startListening(MessageHandler handler) {
        Thread listenerThread = new Thread(() -> {
            while (isConnected()) {
                try {
                    String message = receive();
                    if (message != null && !message.isEmpty()) {
                        handler.handleMessage(message);
                    }
                } catch (IOException e) {
                    if (isConnected()) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public interface MessageHandler {
        void handleMessage(String message);
    }
}
