/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.Graphics;

/**
 *
 * @author marianamtzcelis and diegomezcota
 */
public class Alien extends Item {

    private Game game;
    private int direction;
    private Shot shot;
    private int rand;
    private boolean isVisible;
    private Animation plasmaAnimation;
    private boolean justCrashed;
    private int counterCrashed;

    /**
     * Constructor for Alien
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param direction
     */
    public Alien(int x, int y, int width, int height, Game game, int direction) {
        super(x, y, width, height);
        this.game = game;
        this.direction = direction;
        shot = new Shot(x, y, 20, 20, 2, this, 1, game);
        isVisible = true;
        justCrashed = false;
        counterCrashed = 50;

        // inicialization of shock animation
        this.plasmaAnimation = new Animation(Assets.plasmaShock, 50);
    }

    /**
     * x getter
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * isVisible getter
     *
     * @return
     */
    public boolean isIsVisible() {
        return isVisible;
    }

    /**
     * counterCrashed setter
     *
     * @param counterCrashed
     */
    public void setCounterCrashed(int counterCrashed) {
        this.counterCrashed = counterCrashed;
    }

    /**
     * x setter
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * y getter
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * y setter
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * dx getter
     *
     * @return dx
     */
    public int getDx() {
        return dx;
    }

    /**
     * direction setter
     *
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * direction getter
     *
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * shot getter
     *
     * @return shot
     */
    public Shot getShot() {
        return shot;
    }

    /**
     * shot setter
     *
     * @param shot
     */
    public void setShot(Shot shot) {
        this.shot = shot;
    }

    /**
     * isVisible setter
     *
     * @param isVisible
     */
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * justCrashed getter
     *
     * @return
     */
    public boolean isJustCrashed() {
        return justCrashed;
    }

    /**
     * justCrashed setter
     *
     * @param justCrashed
     */
    public void setJustCrashed(boolean justCrashed) {
        this.justCrashed = justCrashed;
    }

    /**
     * counterCrashed getter
     *
     * @return
     */
    public int getCounterCrashed() {
        return counterCrashed;
    }

    /**
     * Ticks Alien
     */
    @Override
    public void tick() {

        if (isVisible) {
            rand = (int) (Math.random() * 250) + 1; // Random number from 1 to 250 to throw bombs
            shot.tick(); // Ticks alien shot
            if (rand == 11) {
                shot.setIsShot(true); // Shoots shot
            }

            // direction 1 means left and 0 means right
            if (direction == 1) {
                setX(this.getX() - 1);
            } else {
                setX(this.getX() + 1);
            }

            if (justCrashed) {
                plasmaAnimation.tick();
            }

        }
    }

    /**
     * Render for Alien class
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        if (isVisible) {
            g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
        }

        if (justCrashed) {
            counterCrashed--;
            g.drawImage(plasmaAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
            if (counterCrashed <= 0) {
                isVisible = false;
                this.setY(-80);
            }
        }
    }

}
