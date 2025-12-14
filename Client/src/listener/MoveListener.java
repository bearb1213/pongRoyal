package listener;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import player.Player;
import serveur.Client;

public class MoveListener implements  KeyListener  {

    private Player player;
    private Client client;

    public MoveListener(Player player , Client client){
        this.player = player;
        this.client = client;
    }   

    public MoveListener(){   
    }

    @Override
    public void keyPressed(KeyEvent key) {
        
        
        int keyCode = key.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_UP:
                new Thread(() -> {
                    try {
                        client.sendMessage("MOVE UP "+player.getNumPlayer()+"\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case KeyEvent.VK_DOWN:
                new Thread(() -> {
                    try {
                        client.sendMessage("MOVE DOWN "+player.getNumPlayer()+"\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
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
