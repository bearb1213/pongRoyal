package affichage;

import javax.swing.*;
import serveur.Client;

public class FrameGame extends JFrame {
    private Client client;
    private PanelFormName panelFormName;
    private PanelGame panelGame;
    private PanelFormPieces panelFormPieces;
    JLabel waitLabel;

    public FrameGame() {
        setTitle("Pong Royal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            client = new Client();
            client.f = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
        panelGame = new PanelGame(client);
        client.tableJeu = panelGame.getTableJeu();
        client.start();
        panelFormPieces = new PanelFormPieces(client,this);

        panelFormName = new PanelFormName(client,this);
    

        add(panelFormName); 
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
}
