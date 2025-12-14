package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import player.Player;

public class MoveListener implements  KeyListener  {

    private Player player;

    public MoveListener(Player player){
        this.player = player;
    }   

    public MoveListener(){   
    }

    @Override
    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_UP:
                player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        
    }

    @Override
    public void keyTyped(KeyEvent e){

    }
    
}
