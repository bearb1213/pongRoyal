package chess;

import util.Color;
import javax.swing.ImageIcon;

public class Tour extends Pion {
    public Tour() {
        super();
    }
    
    public Tour(Integer x, Integer y, Color color) {
        super("Tour", 3, new ImageIcon("images/tourb.png"), x, y, color);
    }
    
    public Tour(Integer pv, Integer x, Integer y, Color color) {
        super("Tour", pv, null, x, y, color);
        if (color == Color.BLANC) 
            setImage(new ImageIcon("images/tourb.png"));
        else 
            setImage(new ImageIcon("images/tourn.png"));
    }
}