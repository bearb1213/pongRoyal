package chess;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

import player.Player;
import pong.Ball;
import pong.Raquette;
import util.Color;

import serveur.ServeurUdp;

public class TableJeu {
    
    private Player player1;
    private Player player2;
    private Integer size;
    private final Integer DECAL = 40;
    private Rectangle hitbox;
    private Ball ball;
    private ServeurUdp serveurUdp;
    

    public TableJeu() {
        this.ball = new Ball(200,160,10,2,2);
    }

    public TableJeu(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.ball = new Ball(200,160,10,2,2);
    }
    public TableJeu(String nameP1, String nameP2,Integer size){
        this.size = size;
        Raquette raquette1 = new Raquette(120 + DECAL, 140 + DECAL, 10, 40, 10);
        Raquette raquette2 = new Raquette(280 + DECAL, 140 + DECAL, 10, 40, 10);
        this.player1 = new Player(nameP1, raquette1, new ArrayList<>(), Color.BLANC);
        this.player2 = new Player(nameP2, raquette2, new ArrayList<>(), Color.NOIR);
        this.ball = new Ball(200,160,10,2,2);
        createTableJeu();
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

    public void setServeurUdp(ServeurUdp serveurUdp) {
        this.serveurUdp = serveurUdp;
    }

    public void setSize(Integer size) {
        this.size = size;
        createTableJeu();
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    public void setPlayer1(String name){
        Raquette r = new Raquette(120 + DECAL, 140 + DECAL, 10, 40, 10);
        this.player1 = new Player(name , r, new ArrayList<>(), Color.BLANC);
        player1.setNumPlayer(1);
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer2(String name){
        Raquette r = new Raquette(280 + DECAL, 140 + DECAL, 10, 40, 10);
        this.player2 = new Player(name, r, new ArrayList<>(), Color.NOIR);
        player2.setNumPlayer(2);
    }

    public boolean isFull(){
        return player1 != null && player2 != null;
    }

    public boolean isPlayer1Set(){
        return player1 != null;
    }

    public boolean isPlayer2Set(){
        return player2 != null;
    }

    public boolean isSizeSet(){
        return size != null && size > 0 && size <= 8 && size%2 == 0;
    }



    public void createDame(Integer pv){
        player1.getPieces().add(new Reine(pv,0 + DECAL,120 + DECAL,Color.BLANC));
        player2.getPieces().add(new Reine(pv,360 + DECAL,120 + DECAL,Color.NOIR));
    }
    public void createRoi(Integer pv){
        player1.getPieces().add(new Roi(pv,0 + DECAL,160 + DECAL,Color.BLANC));
        player2.getPieces().add(new Roi(pv,360 + DECAL,160 + DECAL,Color.NOIR));
    }
    public void createCavalier(Integer pv){
        player1.getPieces().add(new Cavalier(pv,0 + DECAL,80 + DECAL,Color.BLANC));
        player1.getPieces().add(new Cavalier(pv,0 + DECAL,200 + DECAL,Color.BLANC));
        player2.getPieces().add(new Cavalier(pv,360 + DECAL,80 + DECAL,Color.NOIR));
        player2.getPieces().add(new Cavalier(pv,360 + DECAL,200 + DECAL,Color.NOIR));
    }
    public void createFou(Integer pv){
        player1.getPieces().add(new Fou(pv,0 + DECAL,40 + DECAL,Color.BLANC));
        player1.getPieces().add(new Fou(pv,0 + DECAL,240 + DECAL,Color.BLANC));
        player2.getPieces().add(new Fou(pv,360 + DECAL,40 + DECAL,Color.NOIR));
        player2.getPieces().add(new Fou(pv,360 + DECAL,240 + DECAL,Color.NOIR));
    }
    public void createTour(Integer pv){
        player1.getPieces().add(new Tour(pv,0 + DECAL,0 + DECAL,Color.BLANC));
        player1.getPieces().add(new Tour(pv,0 + DECAL,280 + DECAL,Color.BLANC));
        player2.getPieces().add(new Tour(pv,360 + DECAL,0 + DECAL,Color.NOIR));
        player2.getPieces().add(new Tour(pv,360 + DECAL,280 + DECAL,Color.NOIR));
    }

    public void createTableJeu(){
        int y = 160 - (size/2)*40;
        this.hitbox = new Rectangle(0 + DECAL/2, y + DECAL , 400 + DECAL, size*40 );
    }


    public void setPieces(Integer pionPv , Integer cavalierPv , Integer fouPv , Integer tourPv , Integer reinePv , Integer roiPv) {
        if (size%2 != 0) throw new IllegalArgumentException("Nombre paire");
        if (size <= 0) throw new IllegalArgumentException("Nombre doit etre superieur 0");
        if (size > 8) throw new IllegalArgumentException("Nombre doit etre inferieur ou egal 8");
        // Initialisation des pieces pour player1 (BLANC)
        int pionPosY = 160 - (size/2)*40;

        for (int i = 0; i < size; i++) {
            player1.getPieces().add(new Pion("Pion", pionPv, Color.BLANC, 40 + DECAL, (i*40) + pionPosY + DECAL));
            player2.getPieces().add(new Pion("Pion", pionPv, Color.NOIR, 320 + DECAL, (i*40) + pionPosY + DECAL));
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

    public void update() {
        ball.move();
        if (ball.getHitbox().getY() <= hitbox.getY() || ball.getHitbox().getY() + ball.getDiameter() >= hitbox.getY() + hitbox.getHeight()) {
            ball.reverseY();
        } 
        if (ball.getHitbox().getX() <= hitbox.getX() || ball.getHitbox().getX() + ball.getDiameter() >= hitbox.getX() + hitbox.getWidth()) {
            ball.reverseX();
        }
        serveurUdp.sendToAll("BALL "+ball.getX()+" "+ball.getY());
        player1.update(ball);
        player2.update(ball);
    }

    public void start(){
        while (!isGameOver()) {
            update();
        }
    }

    public void setPieces(String pieces) {
        String[] pieceArray = pieces.split(" ");
        int size = Integer.parseInt(pieceArray[1]);
        setSize(size);
        Integer pion = Integer.parseInt(pieceArray[2]);
        Integer roi = 0, reine = 0, cavalier = 0, fou = 0, tour = 0;
        if(size >= 2){
            roi = Integer.parseInt(pieceArray[3]);
            reine = Integer.parseInt(pieceArray[4]);
        }
        if (size >= 4){
            cavalier = Integer.parseInt(pieceArray[5]);
        }
        if (size >= 6){
            fou = Integer.parseInt(pieceArray[6]);
        }
        if (size >= 8){
            tour = Integer.parseInt(pieceArray[7]);
        }
        setPieces(pion, cavalier, fou, tour, reine, roi);
    }

    public boolean isGameOver(){
        return player1.hasLost() || player2.hasLost();
    }

    public void processMoveMessage(String message){
        message = message.trim();   
        if (message.startsWith("MOVE")) {
            System.out.println("\n\n\n\n\n\nMove reçu par le serveur \n\n\n\n\n");
            String[] parts = message.split(" ");
            if (parts.length >= 3) {
                String direction = parts[1];
                int playerNum = Integer.parseInt(parts[2]);
                
                Player movingPlayer = (playerNum == 1) ? player1 : player2;
                
                switch (direction) {
                    case "UP":
                        System.out.println("Déplacement vers le haut pour le joueur " + playerNum);
                        movingPlayer.getRaquette().moveUp();
                        break;
                    case "DOWN":
                        System.out.println("Déplacement vers le bas pour le joueur " + playerNum);
                        movingPlayer.getRaquette().moveDown();
                        break;
                    default:
                        System.out.println("Direction inconnue: " + direction);
                }
            } else {
                System.out.println("Message MOVE invalide: " + message);
            }
        } else {
            System.out.println("Message inconnu: " + message);
        }
    }

}
