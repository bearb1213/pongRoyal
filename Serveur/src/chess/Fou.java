package chess;

import util.Color;

public class Fou extends Pion {
    public Fou() {
        super();
    }
    
    public Fou(Integer x, Integer y, Color color) {
        super("Fou", 3, x, y, color);
    }
    
    public Fou(Integer pv, Integer x, Integer y, Color color) {
        super("Fou", pv, x, y, color);
    }
}