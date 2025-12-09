package chess;

import javax.swing.ImageIcon;
import javax.swing.*;
import util.Color;
import util.Utils;

import java.awt.*;



public class Pion {
    private String name;
    private Integer pv;
    private ImageIcon image;
    private Integer x,y;
    private Integer width,height;
    private Color color;
    private Rectangle hitbox;

    public Pion() {
        this.name = "Pion";
        this.pv = 1;
        this.image = new ImageIcon("images/pion.png");
        this.x = 0;
        this.y = 0;
        this.width = 40;
        this.height = 40;
        image=Utils.resizeIcon(image,this.width,this.height);
        
    }
    public Pion(String name, Integer pv, ImageIcon image, Integer x, Integer y,Color color) {
        this.name = name;
        this.pv = pv;
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.color = color;
        hitbox = new Rectangle(x, y, width, height);
        if (image!=null) image=Utils.resizeIcon(image,this.width,this.height);
        
    }
    public Pion(String name, Integer pv, ImageIcon image, Integer x, Integer y, Integer width, Integer height, Color color) {
        this.name = name;
        this.pv = pv;
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        hitbox = new Rectangle(x, y, width, height);
        if (image!=null)  image=Utils.resizeIcon(image,this.width,this.height);

    }
    public Pion(String name , Integer pv , Color color,Integer x, Integer y) {
        this.name = name;
        this.pv = pv;
        
        if (color== Color.BLANC) {
            this.image = new ImageIcon("images/pionb.png");
        } else {
            this.image = new ImageIcon("images/pionn.png");
        }
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.color = color;
        hitbox = new Rectangle(x, y, width, height);
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
    }
    public void setY(Integer y) {
        this.y = y;
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

}
