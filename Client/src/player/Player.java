package player;

import pong.Ball;
import pong.Raquette;
import util.Color;
import chess.Pion;

import java.awt.Graphics;
import java.util.List;

public class Player {
    private String name;
    private Raquette raquette;
    private List<Pion> pieces;
    private Color color;
    private int numPlayer;


    public Player(String name, Raquette raquette, List<Pion> pieces, Color color) {
        this.name = name;
        this.raquette = raquette;
        this.pieces = pieces;
        this.color = color;
    }
    public Player (String name, Raquette raquette, List<Pion> pieces, Color color , int numPlayer) {
        this.name = name;
        this.raquette = raquette;
        this.pieces = pieces;
        this.color = color;
        this.numPlayer = numPlayer;
    }
    public String getName() {
        return name;
    }
    public Raquette getRaquette() {
        return raquette;
    }
    public List<Pion> getPieces() {
        return pieces;
    }
    public Color getColor() {
        return color;
    }
    public int getNumPlayer() {
        return numPlayer;
    }

    public void setPieces(List<Pion> pieces) {
        this.pieces = pieces;
    }
    public void setRaquette(Raquette raquette) {
        this.raquette = raquette;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void verifierPieces() {
        pieces.removeIf(pion -> pion == null || !pion.isAlive());
    }

    public void paint(Graphics g) {
        raquette.paint(g);
        for (Pion pion : pieces) {
            pion.paint(g);
        }
    }

    public void update(Ball ball) {
        for (Pion pion : pieces) {
            pion.update(ball);
        }   
        verifierPieces();
        raquette.update(ball);
    }

    public boolean hasLost() {
        for (Pion pion : pieces) {
            if (pion.getName().equals("Roi") && pion.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public void moveUp() {
        raquette.moveUp();
    }
    public void moveDown() {
        raquette.moveDown();
    }
}
