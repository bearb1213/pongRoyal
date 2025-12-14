package affichage;

import javax.swing.*;
import java.awt.*;
import chess.TableJeu;
import java.net.Socket;
import serveur.Client;

public class PanelGame extends JPanel {

    private TableJeu t;
    private Timer timer;
    private Client client;

    public PanelGame(Client client) {
        this.client = client;
        t = new TableJeu();

        setDoubleBuffered(true);
        
        setFocusable(true);
    }
    public void startGame(){
        if (client.idPlayer == 1)
            addKeyListener(new listener.MoveListener(t.getPlayer1() ,client));
        else
            addKeyListener(new listener.MoveListener(t.getPlayer2(), client));

        // 16 pour environ 60 FPS
        // 33 pour environ 30 FPS
        timer = new Timer(16, e -> {
            t.update(); // <-- déplace les pièces
            repaint();  // <-- redessine l’écran
        });
        timer.start();
     
    }
    public TableJeu getTableJeu() {
        return t;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if (!t.isGameOver()) {   
            t.paint(g); 
        }
    }
}
