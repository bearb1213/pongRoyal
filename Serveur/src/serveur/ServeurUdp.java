package serveur;

import chess.TableJeu;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class ServeurUdp {
    private DatagramSocket socket;
    private int port=1302;
    private boolean running;
    private static final int BUFFER_SIZE = 1024;

    // Client 1
    private InetAddress client1Address;
    private int client1Port;
    private boolean client1Connected;
    private String client1Name;

    // Client 2
    private InetAddress client2Address;
    private int client2Port;
    private boolean client2Connected;
    private String client2Name;

    private TableJeu tableJeu;

    private boolean gameStart = false;
    
    private Timer gameTimer;
    private static final int UPDATE_INTERVAL = 16; // 16ms (~60 FPS)

    public ServeurUdp(int port) {
        this.port = port;
        this.running = false;
        this.client1Connected = false;
        this.client2Connected = false;
        
    }

    public ServeurUdp() {
        this.port = 1302;
        this.running = false;
        this.client1Connected = false;
        this.client2Connected = false;
        
    }

    public void start() {
        try {
            tableJeu = new TableJeu();
            tableJeu.setServeurUdp(this);
            System.out.println("Initialisation de la table de jeu terminée.");
            socket = new DatagramSocket(port);
            running = true;
            System.out.println("Serveur UDP démarré sur le port " + port);

            byte[] buffer = new byte[BUFFER_SIZE];

            while (running) {
                // Recevoir un paquet
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                System.out.println("En attente de messages des clients...");
                socket.receive(packet);

                // Récupérer les infos du client
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                String message = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Reçu de " + clientAddress + ":" + clientPort + " -> " + message);
                if (isFull()) {
                    handleMessageOther(message);
                } else {
                    // Gérer la connexion
                    int senderClient = handleConnection(clientAddress, clientPort, message);
                }

            }
        } catch (Exception e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    // Gérer la connexion d'un client, retourne le numéro du client (1 ou 2) ou 0 si non connecté
    private int handleConnection(InetAddress address, int port, String message) {
        // Vérifier si c'est déjà client 1
        if (client1Connected && client1Address.equals(address) && client1Port == port) {
            return 1;
        }
        // Vérifier si c'est déjà client 2
        if (client2Connected && client2Address.equals(address) && client2Port == port) {
            return 2;
        }

        // Nouvelle connexion
        if (message.startsWith("NAME")) {
            if (!client1Connected) {
                client1Address = address;
                client1Port = port;
                client1Connected = true;
                client1Name = message.substring(5); 
                System.out.println("Client 1 connecté: " + address + ":" + port);
                tableJeu.setPlayer1(client1Name);
                sendMessage("ID 1", address, port);
                sendToClient("WAIT", 1);
                return 1;
            } else if (!client2Connected) {
                client2Address = address;
                client2Port = port;
                client2Connected = true;
                client2Name = message.substring(5); 
                System.out.println("Client 2 connecté: " + address + ":" + port);
                tableJeu.setPlayer2(client2Name);
                sendMessage("ID 2", address, port);
                sendToClient("OPPONENT " + client1Name, 2);
                sendToClient("WAIT", 2);
                
                
                sendToClient("OPPONENT " + client2Name, 1);
                sendToClient("CONFIG", 1);

                sendToAll("READY");
                
                return 2;
            } else {
                // Serveur plein
                sendMessage("FULL", address, port);
                return 0;
            }
        } 
        return 0; // Non connecté
    }

    private void handleMessageOther( String message) {
        if (message.startsWith("PIECES")){
            sendToAll(message);
            tableJeu.setPieces(message);
            gameStart = true;
            
            startGameTimer();
        } else if (message.startsWith("MOVE")){
            sendToAll(message);
            tableJeu.processMoveMessage(message);
        } else if (message.startsWith("OK")){
            System.out.println("OK");
        }
    }

    // Envoyer un message à l'autre client
    private void broadcastMessage(String message, int senderClient) {
        if (senderClient == 1 && client2Connected) {
            sendMessage(message, client2Address, client2Port);
        } else if (senderClient == 2 && client1Connected) {
            sendMessage(message, client1Address, client1Port);
        }
    }

    // Envoyer un message aux deux clients
    public void sendToAll(String message) {
        if (client1Connected) {
            sendMessage(message, client1Address, client1Port);
        }
        if (client2Connected) {
            sendMessage(message, client2Address, client2Port);
        }
    }

    // Envoyer un message à un client spécifique par numéro
    public void sendToClient(String message, int clientNumber) {
        if (clientNumber == 1 && client1Connected) {
            sendMessage(message, client1Address, client1Port);
        } else if (clientNumber == 2 && client2Connected) {
            sendMessage(message, client2Address, client2Port);
        }
    }

    // Envoyer un message à une adresse spécifique
    public void sendMessage(String message, InetAddress address, int port) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("Envoyé à " + address + ":" + port + " -> " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectClient(int clientNumber) {
        if (clientNumber == 1) {
            client1Connected = false;
            client1Address = null;
            client1Port = 0;
            System.out.println("Client 1 déconnecté");
        } else if (clientNumber == 2) {
            client2Connected = false;
            client2Address = null;
            client2Port = 0;
            System.out.println("Client 2 déconnecté");
        }
    }

    public void stop() {
        running = false;
        stopGameTimer();
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        System.out.println("Serveur UDP arrêté");
    }

    public int getClientCount() {
        int count = 0;
        if (client1Connected) count++;
        if (client2Connected) count++;
        return count;
    }

    public boolean isFull() {
        return client1Connected && client2Connected;
    }

    public boolean isClient1Connected() {
        return client1Connected;
    }

    public boolean isClient2Connected() {
        return client2Connected;
    }

    private void startGameTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        gameTimer = new Timer("GameUpdateTimer", true);
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameStart && tableJeu != null) {
                    tableJeu.update();
                } 
                if (tableJeu.isGameOver()) {
                    sendToAll("WINNER " + (tableJeu.getPlayer1().hasLost() ? tableJeu.getPlayer2().getName() : tableJeu.getPlayer1().getName()));
                    stopGameTimer();
                    gameStart = false;
                }
            }
        }, 0, UPDATE_INTERVAL);
        System.out.println("Timer de jeu démarré (intervalle: " + UPDATE_INTERVAL + "ms)");
    }

    private void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
            System.out.println("Timer de jeu arrêté");
        }
    }

    
}
