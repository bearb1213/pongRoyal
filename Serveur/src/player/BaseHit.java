package player;
import java.awt.Rectangle;

public class BaseHit {
    private Rectangle hitbox;
    
    public BaseHit(Integer x, Integer y, Integer width, Integer height) {
        hitbox = new Rectangle(x, y, width, height);
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setX(Integer x) {
        this.hitbox.x = x;
    }
    public void setY(Integer y) {
        this.hitbox.y = y;
    }

    // this mandona an'i other
    // 0 aucun , 1 top bottom , 2 right left , 3 corner
    public int colide(BaseHit other) {
        if (!this.hitbox.intersects(other.getHitbox())) return 0; 
        
        int dx = Math.abs(this.hitbox.x - (other.getHitbox().x + other.getHitbox().width));
        int dx1 = Math.abs((this.hitbox.x + this.hitbox.width) - other.getHitbox().x);

        int dy = Math.abs(this.hitbox.y - (other.getHitbox().y + other.getHitbox().height));
        int dy1 = Math.abs((this.hitbox.y + this.hitbox.height) - other.getHitbox().y);

        int vraiDx = Math.min(dx, dx1);
        int vraiDy = Math.min(dy, dy1);
        if(vraiDx < vraiDy){
            return 2; // right left
        } else if (vraiDy < vraiDx){
            return 1; // top bottom
        } else {
            return 3; // corner
        }
        
        
    }


}
