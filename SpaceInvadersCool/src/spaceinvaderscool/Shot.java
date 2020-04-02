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
    private Item item;

    public Shot(int x, int y, int width, int height, int dy, Item item, int type, Game game) {
        super(x, y, width, height);
        this.dy = dy;
        this.item = item;
        this.isShot = false;
        this.type = type;
        this.game = game;
    }

    @Override
    public void tick() {
        
        if (type == 0) {
        if (isShot) {
        setY(y+dy);
        } else {
            setX(game.getPlayer().getX()+6);
            setY(game.getPlayer().getY());
        }
        
        if (y <= 0) {
            isShot = false;
        }
        }
        
        if (type == 1) {
            if (isShot) {
                setY(y+dy);
            } else {
                setX(item.getX()+4);
                setY(item.getY()+5);
            }
            if (y >= 290) {
            isShot = false;
        }
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
