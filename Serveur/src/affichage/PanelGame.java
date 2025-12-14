package affichage;

import javax.swing.*;
import java.awt.*;
import chess.TableJeu;

public class PanelGame extends JPanel {

    private TableJeu t;
    private Timer timer;

    public PanelGame() {
        t = new TableJeu();
        

        setDoubleBuffered(true);
        

        
        addKeyListener(new listener.MoveListener(t.getPlayer1()));
        setFocusable(true);
    }

    public void startGame(){
           // 16 pour environ 60 FPS
        // 33 pour environ 30 FPS
        timer = new Timer(16, e -> {
            t.update(); // <-- déplace les pièces
            repaint();  // <-- redessine l’écran
        });
        timer.start(); 
     
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if (!t.isGameOver()) {   
            t.paint(g); 
        }
    }

}
