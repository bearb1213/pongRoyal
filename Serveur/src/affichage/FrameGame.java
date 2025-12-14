package affichage;

import javax.swing.*;

public class FrameGame extends JFrame {

    public FrameGame() {
        setTitle("Pong Royal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new PanelGame()); 
    }
}
