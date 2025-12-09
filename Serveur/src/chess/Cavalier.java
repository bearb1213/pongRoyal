package chess;

import util.Color;
import javax.swing.ImageIcon;

public class Cavalier extends Pion {
    public Cavalier() {
        super();
    }
    
    public Cavalier(Integer x, Integer y, Color color) {
        super("Cavalier", 3, new ImageIcon("images/cavab.png"), x, y, color);
    }
    
    public Cavalier(Integer pv, Integer x, Integer y, Color color) {
        super("Cavalier", pv, null, x, y, color);
        if (color == Color.BLANC) 
            setImage(new ImageIcon("images/cavab.png"));
        else 
            setImage(new ImageIcon("images/cavan.png"));
    }
}