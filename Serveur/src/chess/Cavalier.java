package chess;

import util.Color;

public class Cavalier extends Pion {
    public Cavalier() {
        super();
    }
    
    public Cavalier(Integer x, Integer y, Color color) {
        super("Cavalier", 3, x, y, color);
    }
    
    public Cavalier(Integer pv, Integer x, Integer y, Color color) {
        super("Cavalier", pv, x, y, color);
    }
}