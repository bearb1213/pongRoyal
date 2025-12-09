package pong;

import java.awt.Graphics;

public class Raquette {
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private Integer speed;
    public Raquette(Integer x, Integer y, Integer width, Integer height, Integer speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }
    /// get
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
    public Integer getSpeed() {
        return speed;
    }

    /// set
    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    public void moveUp() {
        this.x -= this.speed;
    }
    public void moveDown() {
        this.x += this.speed;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}
