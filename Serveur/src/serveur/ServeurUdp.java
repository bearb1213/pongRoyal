package serveur;

import chess.TableJeu;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServeurUdp {

    private TableJeu t;
    private DatagramSocket socket;
    
    private PlayerSession player1;
    private PlayerSession player2;
    private int readyCount = 0;

    private int nbPlayers = 0;

    public ServeurUdp() {
        try {
            t = new TableJeu();
            socket = new DatagramSocket(1302);
            System.out.println("Serveur UDP démarré sur le port 1302");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // BLOQUANT

                String message = new String(
                        packet.getData(), 0, packet.getLength()
                );

                System.out.println("Reçu : " + message);

                // Enregistrement des joueurs
                if (message.equals("JOIN")) {
                    registerPlayer(packet);
                    continue;
                }

                // Traitement du message de jeu
                handleGameMessage(message, packet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerPlayer(DatagramPacket packet) throws Exception {
        if (player1 == null) {
            player1 = new PlayerSession(
                t, 1, packet.getAddress(), packet.getPort(), this
            );
            sendToPlayer(player1, "PLAYER 1");
        } else if (player2 == null) {
            player2 = new PlayerSession(
                t, 2, packet.getAddress(), packet.getPort(), this
            );
            sendToPlayer(player2, "PLAYER 2");
        } else {
            sendRaw(packet, "FULL");
        }
    }
    private PlayerSession findPlayer(DatagramPacket packet) {
    if (player1 != null &&
        packet.getAddress().equals(player1.getAddress()) &&
        packet.getPort() == player1.getPort())
        return player1;

    if (player2 != null &&
        packet.getAddress().equals(player2.getAddress()) &&
        packet.getPort() == player2.getPort())
        return player2;

    return null;
}


    private void handleMessage(String message, DatagramPacket packet) {
        PlayerSession sender = findPlayer(packet);
        if (sender != null) {
            sender.handleMessage(message);
        }
    }


    private void sendToAll(String message) throws Exception {
        if (player1 != null) sendToPlayer(player1, message);
        if (player2 != null) sendToPlayer(player2, message);
    }

    private void sendToPlayer(PlayerInfo player, String message) throws Exception {
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                player.address,
                player.port
        );
        socket.send(packet);
    }

    public void playerReady() throws Exception {
        readyCount++;
        if (readyCount == 2) {
            sendToAll("START");
        }
    }


    private void sendRaw(DatagramPacket incoming, String message) throws Exception {
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                incoming.getAddress(),
                incoming.getPort()
        );
        socket.send(packet);
    }
}
