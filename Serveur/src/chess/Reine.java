package chess;

import util.Color;
import javax.swing.ImageIcon;

public class Reine extends Pion {
    public Reine() {
        super();
    }
    public Reine(Integer x, Integer y,Color color) {
        super("Reine", 5, new ImageIcon("images/reineb.png"), x, y,color);
    }
    public Reine(Integer pv,Integer x,Integer y,Color color){
        super("Reine", pv, null, x, y,color);
        if (color == Color.BLANC) 
            setImage(new ImageIcon("images/reineb.png"));
        else 
            setImage(new ImageIcon("images/reinen.png"));
    }
    
}
