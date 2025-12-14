package chess;

import javax.swing.ImageIcon;

import player.BaseHit;
import pong.Ball;

import javax.swing.*;
import util.Color;
import util.Utils;

import java.awt.*;



public class Pion extends BaseHit{
    private String name;
    private Integer pv;
    private ImageIcon image;
    private Integer x,y;
    private Integer width,height;
    private Color color;
    

    public Pion() {
        super(0, 0, 40, 40);
        this.name = "Pion";
        this.pv = 1;
        this.image = new ImageIcon("images/pionb.png");
        this.x = 0;
        this.y = 0;
        this.width = 40;
        this.height = 40;
        image=Utils.resizeIcon(image,this.width,this.height);
        
    }
    public Pion(String name, Integer pv, ImageIcon image, Integer x, Integer y,Color color) {
        super(x, y, 40, 40);
        this.name = name;
        this.pv = pv;
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.color = color;
        if (image!=null) image=Utils.resizeIcon(image,this.width,this.height);
        
    }
    public Pion(String name, Integer pv, ImageIcon image, Integer x, Integer y, Integer width, Integer height, Color color) {
        super(x, y, width, height);
        this.name = name;
        this.pv = pv;
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        if (image!=null)  image=Utils.resizeIcon(image,this.width,this.height);

    }
    public Pion(String name , Integer pv , Color color,Integer x, Integer y) {
        super(x, y, 40, 40);
        this.name = name;
        this.pv = pv;
        
        if (color == Color.BLANC) {
            this.image = new ImageIcon("images/pionb.png");
        } else {
            this.image = new ImageIcon("images/pionn.png");
        }
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.color = color;
        image=Utils.resizeIcon(image,this.width,this.height);

    }
    
    
    /// GET
    public String getName() {
        return name;
    }
    public Integer getPv() {
        return pv;
    }
    public ImageIcon getImage() {
        return image;
    }
    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }
    public Integer getWidth() {
        return width;
    }
    public Integer getHeight() {
        return height;
    }
    public Color getColor() {
        return color;
    }
    /// SET
    public void setX(Integer x) {
        this.x = x;
        super.setX(x);
    }
    public void setY(Integer y) {
        this.y = y;
        super.setY(y);
    }
    public void setPv(Integer pv) {
        this.pv = pv;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(ImageIcon image) {
        this.image = image;
        image=Utils.resizeIcon(image,this.width,this.height);

    }
    public void setWidth(Integer width) {
        this.width = width;
    }   
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setColor(Color color) {
        this.color = color;
    }


    public boolean isAlive() {
        return this.pv > 0;
    }

    public void takeDamage(Integer damage) {
        this.pv -= damage;
        if (this.pv < 0) {
            this.pv = 0;
        }
    }

    public void paint(Graphics g) {
        g.drawImage(image.getImage(), x, y, null);
    }

    public void update(Ball ball) {
        if (!this.isAlive()) return;

        int collisionType = this.colide(ball);
        if (collisionType == 0 ) {
            return;
        }
        if (collisionType == 1) {
            ball.reverseY();
            ball.addTimer();
        } else if (collisionType == 2) {
            ball.reverseX();
            ball.addTimer();
        } else if (collisionType == 3) {
            ball.reverseX();
            ball.reverseY();
            ball.addTimer();
        }
        this.takeDamage(ball.getDegat());

    }


}
