package pong;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import player.BaseHit;


public class Ball extends BaseHit{
    private Integer x;
    private Integer y;
    private Integer diameter;
    private Integer speedX;
    private Integer speedY;
    private Ellipse2D.Double image;
    private Integer degat;
    private Integer time;


    private static final Integer TIME_COOLDOWN = 2;

    public Ball(Integer x, Integer y, Integer diameter, Integer speedX, Integer speedY) {
        super(x, y, diameter, diameter);
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.speedX = speedX;
        this.speedY = speedY;
        image = new Ellipse2D.Double(x, y, diameter, diameter);
        this.degat = 1;
        time=0;
    }
    /// get
    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }
    public Integer getDiameter() {
        return diameter;
    }
    public Integer getSpeedX() {
        return speedX;
    }
    public Integer getSpeedY() {
        return speedY;
    }
    public Ellipse2D.Double getImage() {
        return image;
    }
    public Integer getDegat() {
        return degat;
    }
    /// set
    public void setX(Integer x) {
        this.x = x;
        super.setX(x);
    }
    public void setY(Integer y) {
        this.y = y;
        super.setY(y);
    }
    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }
    public void setSpeedX(Integer speedX) {
        this.speedX = speedX;
    }
    public void setSpeedY(Integer speedY) {
        this.speedY = speedY;
    }
    public void setImage(Ellipse2D.Double image) {
        this.image = image;
    }

    public void move() {
        this.x += this.speedX;
        this.y += this.speedY;
        this.image.setFrame(this.x, this.y, this.diameter, this.diameter);
        super.setX(x);
        super.setY(y);
        if (time>=0) {
            time--;
        }
    }

    public void reverseX() {
        if (time<=0) {
            this.speedX = -this.speedX;
        }
    }
    public void reverseY() {
        if (time<=0) {
            this.speedY = -this.speedY;   
        }
    }
    public void addTimer(){
        time = TIME_COOLDOWN;
    }

    public void resetPosition(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.image.setFrame(this.x, this.y, this.diameter, this.diameter);
        super.setX(x);
        super.setY(y);
    }

    public void paint(Graphics g) {
        g.fillOval(x, y, diameter, diameter);
    }

}
