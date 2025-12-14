package chess;

import util.Color;

public class Roi extends Pion {
    public Roi() {
        super();
    }
    
    public Roi(Integer x, Integer y, Color color) {
        super("Roi", 10, x, y, color);
    }
    
    public Roi(Integer pv, Integer x, Integer y, Color color) {
        super("Roi", pv, x, y, color);
    }
}