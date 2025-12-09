package chess;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import player.Player;
import pong.Raquette;
import util.Color;

public class TableJeu {
    
    private Player player1;
    private Player player2;
    private Integer size;
    public TableJeu(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
    public TableJeu(String nameP1, String nameP2,Integer size){
        this.size = size;
        Raquette raquette1 = new Raquette(120, 140, 10, 40, 10);
        Raquette raquette2 = new Raquette(280, 140, 10, 40, 10);
        this.player1 = new Player(nameP1, raquette1, new ArrayList<>(), Color.BLANC);
        this.player2 = new Player(nameP2, raquette2, new ArrayList<>(), Color.NOIR);
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
 
    public Integer getSize() {
        return size;
    }


    public void createDame(Integer pv){
        player1.getPieces().add(new Reine(pv,0,120,Color.BLANC));
        player2.getPieces().add(new Reine(pv,360,120,Color.NOIR));
    }
    public void createRoi(Integer pv){
        player1.getPieces().add(new Roi(pv,0,160,Color.BLANC));
        player2.getPieces().add(new Roi(pv,360,160,Color.NOIR));
    }
    public void createCavalier(Integer pv){
        player1.getPieces().add(new Cavalier(pv,0,80,Color.BLANC));
        player1.getPieces().add(new Cavalier(pv,0,200,Color.BLANC));
        player2.getPieces().add(new Cavalier(pv,360,80,Color.NOIR));
        player2.getPieces().add(new Cavalier(pv,360,200,Color.NOIR));
    }
    public void createFou(Integer pv){
        player1.getPieces().add(new Fou(pv,0,40,Color.BLANC));
        player1.getPieces().add(new Fou(pv,0,240,Color.BLANC));
        player2.getPieces().add(new Fou(pv,360,40,Color.NOIR));
        player2.getPieces().add(new Fou(pv,360,240,Color.NOIR));
    }
    public void createTour(Integer pv){
        player1.getPieces().add(new Tour(pv,0,0,Color.BLANC));
        player1.getPieces().add(new Tour(pv,0,280,Color.BLANC));
        player2.getPieces().add(new Tour(pv,360,0,Color.NOIR));
        player2.getPieces().add(new Tour(pv,360,280,Color.NOIR));
    }



    public void setPieces(Integer pionPv , Integer cavalierPv , Integer fouPv , Integer tourPv , Integer reinePv , Integer roiPv) {
        if (size%2 != 0) throw new IllegalArgumentException("Nombre paire");
        if (size <= 0) throw new IllegalArgumentException("Nombre doit etre superieur 0");
        if (size > 8) throw new IllegalArgumentException("Nombre doit etre inferieur ou egal 8");
        
        List<Pion> piecesP1 = new ArrayList<>();
        List<Pion> piecesP2 = new ArrayList<>();
        // Initialisation des pieces pour player1 (BLANC)
        int pionPosY = 160 - (size/2)*40;

        for (int i = 0; i < size; i++) {
            piecesP1.add(new Pion("Pion", pionPv, Color.BLANC,40, (i*40) + pionPosY));
            piecesP2.add(new Pion("Pion", pionPv, Color.NOIR,320, (i*40) + pionPosY));
        }
        
        switch (size) {
            case 2:
                createRoi(roiPv);
                createDame(reinePv);
                break;
            case 4:
                
                createRoi(roiPv);
                createDame(reinePv);
                createCavalier(cavalierPv);
                break;
            case 6:
                createRoi(roiPv);
                createDame(reinePv);
                createCavalier(cavalierPv);
                createFou(fouPv);
                break;
            case 8:
                createRoi(roiPv);
                createDame(reinePv);
                createCavalier(cavalierPv);
                createFou(fouPv);
                createTour(tourPv);
                break;

            default:
                createRoi(roiPv);
                createDame(reinePv);
                break;
        }
    }

    public void paint(Graphics g){
        player1.paint(g);
        player2.paint(g);
    }
}
