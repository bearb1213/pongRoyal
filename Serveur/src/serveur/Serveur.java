package serveur;

import chess.TableJeu;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    private TableJeu t;
    private int nbPlayers = 1;
    private ServerSocket serverSocket;
    private PlayerHandler player1Handler;
    private PlayerHandler player2Handler;
    public Serveur() {
        try {
            
            t = new TableJeu();
            this.serverSocket = new ServerSocket(1302);
            System.out.println("Server started on port 1302");
            
            while (nbPlayers <= 2) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                if (nbPlayers==1) {
                    player1Handler = new PlayerHandler(t, nbPlayers,socket,this);
                    player1Handler.start();
                } 
                if(nbPlayers==2){
                    player2Handler = new PlayerHandler(t, nbPlayers,socket,this);
                    player2Handler.start();
                }
                nbPlayers++;
            }
        } catch (Exception e) {
        
            e.printStackTrace();
        }
    }

    public void sendToAll(String message) throws Exception {
        if (player1Handler != null) {
            player1Handler.sendMessage(message);
        }
        if (player2Handler != null) {
            player2Handler.sendMessage(message);
        }
    }

    public void sendToPlayer(int numPlayer, String message) throws Exception {
        if (numPlayer == 1 && player1Handler != null) {
            player1Handler.sendMessage(message);
        } else if (numPlayer == 2 && player2Handler != null) {
            player2Handler.sendMessage(message);
        }
    }
    

}
