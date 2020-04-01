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
        this.dx = 2;
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
        if (game.getKeyManager().right) {
           setX(this.x+dx);
          
        }
        if (game.getKeyManager().left) {
           setX(this.x-dx);
         
        }
    
        // reset x position and y position if colision
        if (getX() + 20 >= game.getWidth()) {
            setX(game.getWidth() - 20);
        }
        else if (getX() <= 5) {
            setX(5);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
