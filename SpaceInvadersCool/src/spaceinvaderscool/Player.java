/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item {

    private int direction;  // to store player's direction
    private Game game;      // to access game's attributes
    private Animation explosion;
    private boolean justCrashed;
    private int counterCrashed;

    /**
     * Player class constructor
     *
     * @param x
     * @param y
     * @param direction
     * @param width
     * @param height
     * @param game
     */
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
        this.dx = 2;
        justCrashed = false;
        counterCrashed = 25;
        
        this.explosion = new Animation(Assets.explosion, 20);
    }

    /**
     * gets the direction of the player
     *
     * @return direction
     */
    public int getDirection() {
        return direction;
    }
    
    /**
     * gets the crashed counter of the player
     *
     * @return counterCrashed
     */
    public int getCounterCrashed() {
        return counterCrashed;
    }

    public void setCounterCrashed(int counterCrashed) {
        this.counterCrashed = counterCrashed;
    }
    

    /**
     * gets the width of the player
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * gets the height of the player
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * sets the direction of the player
     *
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * sets the width of the player
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * sets the height of the player
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isJustCrashed() {
        return justCrashed;
    }

    public void setJustCrashed(boolean justCrashed) {
        this.justCrashed = justCrashed;
    }
    

    /**
     * Ticks the player
     */
    @Override
    public void tick() {
        // moving player depending on flags
        if (game.getKeyManager().right) {
            setX(this.x + dx);
        }
        if (game.getKeyManager().left) {
            setX(this.x - dx);
        }

        // reset x position and y position if colision
        if (getX() + 20 >= game.getWidth()) {
            setX(game.getWidth() - 20);
        } else if (getX() <= 5) {
            setX(5);
        }
        
        if (justCrashed) {
            explosion.tick();
        }
    }

    /**
     * Renders the player's image
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
         if (justCrashed) {
            counterCrashed--;
            g.drawImage(explosion.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
            if (counterCrashed <= 0) {
                justCrashed = false;
                counterCrashed = 50;
            }
        } else {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
         }
    }
}
