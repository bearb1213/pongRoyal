package pong;

import java.awt.Graphics;
import java.awt.Rectangle;

import player.BaseHit;

public class Raquette extends BaseHit{
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private Integer speed;
    public Raquette(Integer x, Integer y, Integer width, Integer height, Integer speed) {
        super(x, y, width, height);
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
        super.setX(x);
    }
    public void setY(Integer y) {
        this.y = y;
        super.setY(y);
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
        this.y -= this.speed;
        super.setY(y);
    }
    public void moveDown() {
        this.y += this.speed;
        super.setY(y);
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, width, height);
    }

    public void update(Ball ball) {
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

        
    }
}
