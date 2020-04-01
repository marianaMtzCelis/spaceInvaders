/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaderscool;

import java.awt.Graphics;

/**
 *
 * @author marianamtzcelis
 */
public class Shot extends Item {
    
    private int type;  // player shot = 0 -- alien shot = 1
    private boolean isShot = false;
    private Game game;
    

    public Shot(int x, int y, int width, int height, int dy, int type, Game game) {
        super(x, y, width, height);
        this.dy = dy;
        this.type = type;
        this.isShot = false;
        this.game = game;
    }

    @Override
    public void tick() {
        
        if (isShot) {
        setY(y+dy);
        } else {
            setX(game.getPlayer().getX()+5);
            setY(game.getPlayer().getY());
        }
        
        if (type == 0 && y <= 0) {
            isShot = false;
        }
    }

    public boolean isIsShot() {
        return isShot;
    }

    public void setIsShot(boolean isShot) {
        this.isShot = isShot;
    }
    

    @Override
    public void render(Graphics g) {
        
       
        if (type == 0) {
            g.drawImage(Assets.playerShot, getX(), getY(), getWidth(), getHeight(), null);
        } else if (type == 1) {
            g.drawImage(Assets.alienShot, getX(), getY(), getWidth(), getHeight(), null);
        }
     
        
    }



    
}
