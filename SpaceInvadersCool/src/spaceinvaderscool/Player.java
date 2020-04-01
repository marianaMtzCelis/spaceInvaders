/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaderscool;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item {

    private int direction;
    private Game game;
    
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
    }

    /**
     * gets the direction of the player
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * gets the width of the player
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * gets the height of the player
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    
    /**
     * sets the direction of the player
     * @param direction 
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * sets the width of the player
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * sets the height of the player
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public void tick() {
        // moving player depending on flags
//        if (game.getKeyManager().q) {
//           setY(getY() - 1);
//           setX(getX() - 1);
//          
//        }
//        if (game.getKeyManager().a) {
//           setY(getY() + 1);
//           setX(getX() - 1);
//         
//        }
    
        // reset x position and y position if colision
        if (getX() + 60 >= game.getWidth()) {
            setX(game.getWidth() - 60);
        }
        else if (getX() <= -30) {
            setX(-30);
        }
        if (getY() + 80 >= game.getHeight()) {
            setY(game.getHeight() - 80);
        }
        else if (getY() <= -20) {
            setY(-20);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
