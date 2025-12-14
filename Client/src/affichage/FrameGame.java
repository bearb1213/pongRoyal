package affichage;

import javax.swing.*;
import serveur.Client;

public class FrameGame extends JFrame {
    private Client client;
    private PanelFormName panelFormName;
    private PanelGame panelGame;
    private PanelFormPieces panelFormPieces;
    JLabel waitLabel;
    JLabel winnerLabel;

    public FrameGame() {
        setTitle("Pong Royal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            client = new Client("localhost");
        } catch (Exception e) {
            e.printStackTrace();
        }
        panelGame = new PanelGame(client);
        client.setTableJeu(panelGame.getTableJeu()) ;
        panelGame.getTableJeu().setClient(client);  
        panelFormPieces = new PanelFormPieces(client,this);

        panelFormName = new PanelFormName(client,this);
    

        add(panelFormName);
        
        // Démarrer l'écoute des messages du serveur
        client.startListening(this::processServerMessage);
    }

    private void processServerMessage(String message) {
        System.out.println("Message reçu: " + message);
        if (message.startsWith("ID")) { 
            
            client.idPlayer = Integer.parseInt(message.substring(3).trim());    
            panelGame.getTableJeu().setNumPlayer(client.idPlayer);
            if (client.idPlayer == 1) {
                panelGame.getTableJeu().setPlayer1(panelFormName.getNameValue());
            } else {
                panelGame.getTableJeu().setPlayer2(panelFormName.getNameValue());
            }
        } else if (message.equals("WAIT")) {
            SwingUtilities.invokeLater(this::waiting);
        } else if (message.equals("START")) {
            SwingUtilities.invokeLater(this::startGame);
        } else if (message.startsWith("CONFIG")) {
            SwingUtilities.invokeLater(this::goFormPv);
        } else if (message.startsWith("OPPONENT")) {
            String opponentName = message.substring(9).trim();
            panelGame.getTableJeu().setOpponentName(opponentName);

        } else if (message.startsWith("PIECES")){
            try {
                panelGame.getTableJeu().setPieces(message);
                startGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (message.startsWith("START")){
            SwingUtilities.invokeLater(this::startGame);
        } else if (message.startsWith("MOVE") || message.startsWith("BALL")){
            try {
                panelGame.getTableJeu().processMoveMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (message.startsWith("WINNER")){
            String winnerName = message.substring(7).trim();
            SwingUtilities.invokeLater(() -> showWinner(winnerName));
        }
    }

    public void goFormPv(){
        getContentPane().removeAll();
        getContentPane().add(panelFormPieces);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    public void waiting(){
        getContentPane().removeAll();
        waitLabel = new JLabel("En attente de l'autre joueur...", SwingConstants.CENTER);
        getContentPane().add(waitLabel);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    public PanelGame getPanelGame() {
        return panelGame;
    }
    public void startGame(){
        getContentPane().removeAll();
        getContentPane().add(panelGame);
        panelGame.startGame();
        getContentPane().revalidate();
        getContentPane().repaint();
        // Demander le focus après que le panel soit visible
        SwingUtilities.invokeLater(() -> panelGame.requestFocusInWindow());
    }

    public void showWinner(String winnerName){
        getContentPane().removeAll();
        winnerLabel = new JLabel("Le gagnant est : " + winnerName, SwingConstants.CENTER);
        getContentPane().add(winnerLabel);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    

    
}
