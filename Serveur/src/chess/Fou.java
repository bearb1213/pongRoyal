package chess;

import util.Color;
import javax.swing.ImageIcon;

public class Fou extends Pion {
    public Fou() {
        super();
    }
    
    public Fou(Integer x, Integer y, Color color) {
        super("Fou", 3, new ImageIcon("images/foub.png"), x, y, color);
    }
    
    public Fou(Integer pv, Integer x, Integer y, Color color) {
        super("Fou", pv, null, x, y, color);
        if (color == Color.BLANC) 
            setImage(new ImageIcon("images/foub.png"));
        else 
            setImage(new ImageIcon("images/foun.png"));
    }
}