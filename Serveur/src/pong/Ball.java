package pong;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;


public class Ball {
    private Integer x;
    private Integer y;
    private Integer diameter;
    private Integer speedX;
    private Integer speedY;
    private Ellipse2D.Double hitbox;

    public Ball(Integer x, Integer y, Integer diameter, Integer speedX, Integer speedY) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.speedX = speedX;
        this.speedY = speedY;
        hitbox = new Ellipse2D.Double(x, y, diameter, diameter);
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
    public Ellipse2D.Double getHitbox() {
        return hitbox;
    }
    /// set
    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
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
    public void setHitbox(Ellipse2D.Double hitbox) {
        this.hitbox = hitbox;
    }

    public void move() {
        this.x += this.speedX;
        this.y += this.speedY;
        this.hitbox.setFrame(this.x, this.y, this.diameter, this.diameter);
    }

    public void reverseX() {
        this.speedX = -this.speedX;
    }
    public void reverseY() {
        this.speedY = -this.speedY;
    }

    public void resetPosition(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.hitbox.setFrame(this.x, this.y, this.diameter, this.diameter);
    }

    public void paint(Graphics g) {
        g.fillOval(x, y, diameter, diameter);
    }

}
