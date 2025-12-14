package chess;

import util.Color;

public class Reine extends Pion {
    public Reine() {
        super();
    }
    public Reine(Integer x, Integer y, Color color) {
        super("Reine", 5, x, y, color);
    }
    public Reine(Integer pv, Integer x, Integer y, Color color){
        super("Reine", pv, x, y, color);
        
    }
    
}
