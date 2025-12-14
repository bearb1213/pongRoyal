package serveur;

import chess.TableJeu;
import player.Player;
import java.net.InetAddress;

public class PlayerSession {

    private TableJeu tableJeu;
    private Player player;
    private int numPlayer;
    private InetAddress address;
    private int port;
    private ServeurUdp serveur;

    public PlayerSession(TableJeu tableJeu, int numPlayer,
                         InetAddress address, int port, ServeurUdp serveur) {
        this.tableJeu = tableJeu;
        this.numPlayer = numPlayer;
        this.address = address;
        this.port = port;
        this.serveur = serveur;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public void handleMessage(String message) {
        System.out.println("Received from player " + numPlayer + ": " + message);

        try {

            // ===== NAME =====
            if (message.startsWith("NAME")) {
                String name = message.substring(5);

                if (numPlayer == 1) {
                    tableJeu.setPlayer1(name);
                    player = tableJeu.getPlayer1();
                } else {
                    tableJeu.setPlayer2(name);
                    player = tableJeu.getPlayer2();
                }

                player.setNumPlayer(numPlayer);
                serveur.sendToPlayer(this, "YOUARE " + numPlayer);

                Player other = tableJeu.getOtherPlayer(numPlayer);
                if (other != null) {
                    serveur.sendToPlayer(this, "VS " + other.getName());
                } else {
                    serveur.sendToPlayer(this, "WAIT");
                }
            }

            // ===== PIECES =====
            else if (message.startsWith("PIECES")) {
                String[] parts = message.substring(7).split(" ");

                int size = Integer.parseInt(parts[0]);
                tableJeu.setSize(size);

                if (tableJeu.isFull()) {
                    tableJeu.setPieces(
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[5]),
                            Integer.parseInt(parts[4]),
                            Integer.parseInt(parts[6]),
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[2])
                    );

                    serveur.sendToAll(message);
                    serveur.sendToAll("START");
                }
            }

            // ===== READY =====
            else if (message.equals("READY")) {
                serveur.playerReady();
            }

            // ===== MOVE =====
            else if (message.startsWith("MOVE")) {
                serveur.sendToAll(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
