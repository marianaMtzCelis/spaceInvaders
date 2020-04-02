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

    // Constructor for Alien
    public Alien(int x, int y, int width, int height, Game game, int direction) {
        super(x, y, width, height);
        this.game = game;
        this.direction = direction;
        shot = new Shot(x, y, 3, 3, 2, this, 1, game);
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

    @Override
    public void tick() {

        // Random number from 1 to 100 to throw bombs
        rand = (int) (Math.random() * 100 + 1);
        shot.tick();
        if (rand == 11) {
            shot.setIsShot(true);
        }

        // direction 1 means left and 0 means right
        if (direction == 1) {
            setX(this.getX() - 1);
        } else {
            setX(this.getX() + 1);
        }
    }

    /**
     * Render for Alien class
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
    }

}
