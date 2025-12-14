package chess;

import util.Color;
import javax.swing.ImageIcon;

public class Roi extends Pion {
    public Roi() {
        super();
    }
    
    public Roi(Integer x, Integer y, Color color) {
        super("Roi", 10, new ImageIcon("images/roib.png"), x, y, color);
    }
    
    public Roi(Integer pv, Integer x, Integer y, Color color) {
        super("Roi", pv, null, x, y, color);
        if (color == Color.BLANC) 
            setImage(new ImageIcon("images/roib.png"));
        else 
            setImage(new ImageIcon("images/roin.png"));
    }
}