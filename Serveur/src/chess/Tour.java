package chess;

import util.Color;

public class Tour extends Pion {
    public Tour() {
        super();
    }
    
    public Tour(Integer x, Integer y, Color color) {
        super("Tour", 3, x, y, color);
    }
    
    public Tour(Integer pv, Integer x, Integer y, Color color) {
        super("Tour", pv, x, y, color);
    }
}