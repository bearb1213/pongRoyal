package serveur;

import player.Player;
import chess.TableJeu;
import java.io.*;
import java.net.*;

public class PlayerHandler extends Thread {
    
    private TableJeu tableJeu;
    private Player player;
    private int numPlayer;
    private Socket socket;
    private Serveur serveur;
    private String pieces ; 
    private int nbPlayerReady = 0;

    public PlayerHandler(TableJeu tableJeu, int numPlayer , Socket socket ,Serveur serveur) {
        this.tableJeu = tableJeu;
        this.numPlayer = numPlayer;
        this.socket = socket;
        this.serveur = serveur;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String message;

            while ((message = in.readLine()) != null) {
                if(message.equals("QUIT")) {
                    System.out.println("Player " + numPlayer + " disconnected.");
                    break;
                }
                handleMessage(message, out);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void handleMessage(String message, PrintWriter out) {
        System.out.println("Received from player " + numPlayer + ": " + message);
        if (message.startsWith("NAME")) {
            String name = message.substring(5);
            if (numPlayer == 1) {
                tableJeu.setPlayer1(name);
                player = tableJeu.getPlayer1();
                player.setNumPlayer(1);
            } else if (numPlayer == 2) {
                tableJeu.setPlayer2(name);
                player = tableJeu.getPlayer2();
                player.setNumPlayer(2);
            }
            out.println("YOUARE " + numPlayer +"\n");
            if(numPlayer ==1 ){
                if(tableJeu.getPlayer2() != null){
                    out.println("VS " + tableJeu.getPlayer2().getName() + "\n");
                } 
            } else {
                if(tableJeu.getPlayer1() != null){
                    out.println("VS " + tableJeu.getPlayer1().getName() + "\n");
                } else {
                    out.println("WAIT \n");
                }
            }
            
        } else if (message.startsWith("PIECES")){
            pieces = message;
            String size = message.substring(7);
            String [] parts = size.split(" ");
            Integer s = Integer.parseInt(parts[0]);
            tableJeu.setSize(s);
            if(tableJeu.isFull()){
                Integer pionPv = Integer.parseInt(parts[1]);
                Integer roiPv = Integer.parseInt(parts[2]);
                Integer reine = Integer.parseInt(parts[3]);
                Integer fou = Integer.parseInt(parts[4]);
                Integer cavalier = Integer.parseInt(parts[5]);
                Integer tour = Integer.parseInt(parts[6]);
                tableJeu.setPieces(pionPv, cavalier, fou, tour, reine, roiPv);

                try {
                    serveur.sendToPlayer(1, "VS " + tableJeu.getPlayer2().getName() + "\n");
                    serveur.sendToAll(message+"\n");
                    serveur.sendToAll("START\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } 
        else if(message.startsWith("READY")) {
            nbPlayerReady++;
            if(nbPlayerReady ==2){
                try {
                    serveur.sendToAll("START\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } 
        else if(message.startsWith("MOVE")) {
            try {
                serveur.sendToAll(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        

    }

    public void sendMessage(String message) throws Exception {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
    }
    

}
