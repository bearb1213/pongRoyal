package serveur;

import affichage.FrameGame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.SwingUtilities;
import chess.TableJeu;

public class Client extends Thread {
    public Socket socket;
    public String ipAddress;
    public int idPlayer;
    public FrameGame f;
    public TableJeu tableJeu ;
    
    public Client()throws Exception{}
    public Client(String ipAddress,FrameGame f) throws Exception {
        this.f=f;
        this.tableJeu = f.getPanelGame().getTableJeu();
        this.ipAddress = ipAddress;
        if (ipAddress == null || ipAddress.isEmpty()) {
            this.ipAddress = "localhost";
        }

        socket = new Socket(this.ipAddress, 1302);
        
        // BufferedReader in = new BufferedReader(
        //    new InputStreamReader(socket.getInputStream()));
        // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        // out.println("Salut serveur !");
        // String response = in.readLine();
        
        // System.out.println("Réponse du serveur : " + response);
        
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) throws Exception {
        this.ipAddress = ipAddress;
        socket = new Socket(this.ipAddress, 1302);
    }
    public void close() throws Exception {
        socket.close();
    }
    public void sendMessage(String message) throws Exception {
        BufferedReader in = new BufferedReader(
           new InputStreamReader(socket.getInputStream()));
        
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
        String response ;
        while ((response = in.readLine()) != null) {
            System.out.println("Réponse du serveur : " + response);
            handleMessage(response , out);
        }
        

    }

    public void handleMessage(String message , PrintWriter out ) throws Exception {
        if(message.startsWith("YOUARE")){
            String id = message.substring(7);
            idPlayer = Integer.parseInt(id);
            System.out.println("Vous êtes le joueur numéro : " + idPlayer);

            if (idPlayer == 1){
                tableJeu.setPlayer1("me");
                System.out.println("Manao formulaire pieces");
                SwingUtilities.invokeLater(() -> f.goFormPv());
            }
            if (idPlayer ==2){
                tableJeu.setPlayer2("me");
                System.out.println("Manao attente");
                SwingUtilities.invokeLater(() -> f.waiting());
            }
        } if (message.startsWith("WAIT")){
            System.out.println("WAIT");
            SwingUtilities.invokeLater(() -> f.waiting());
        } if (message.startsWith("VS")){
            String opponentName = message.substring(3);
            System.out.println("Votre adversaire est : " + opponentName);
            if (idPlayer == 2){
                tableJeu.setPlayer1(opponentName);
            } 
            if (idPlayer ==1){
                tableJeu.setPlayer2(opponentName);
            }
        }
        if (message.startsWith("PIECES")){
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
                if(!tableJeu.isPiecesSet()) sendMessage(message);
            }
        } if (message.startsWith("START")){
            System.out.println("Le jeu commence !");
            SwingUtilities.invokeLater(() -> f.startGame());
            sendMessage("\nREADY\n");
        } if (message.startsWith("MOVE")) {
            String move = message.substring(5);
            String [] parts = move.split(" ");
            String direction = parts[0];
            int numPlayer = Integer.parseInt(parts[1]);
            if (numPlayer != idPlayer){
                if (direction.equals("UP")){
                    if (numPlayer ==1)
                        tableJeu.getPlayer1().moveUp();
                    else
                        tableJeu.getPlayer2().moveUp();
                } else if (direction.equals("DOWN")){
                    if (numPlayer ==1)
                        tableJeu.getPlayer1().moveDown();
                    else
                        tableJeu.getPlayer2().moveDown();
                }
            }
        }
    }

}
