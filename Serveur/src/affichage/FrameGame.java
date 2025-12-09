package affichage;

import javax.swing.*;
import java.awt.*;
import chess.TableJeu;

public class FrameGame extends JFrame {
    
    private TableJeu t;
    
    public FrameGame() {
        setTitle("Pong Royal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        t = new TableJeu("p1","p2",8);
        t.setPieces(1, 2, 2, 2, 2, 2);
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (t != null) {
            t.paint(g);
        }
        repaint();
    }

    
    
}
