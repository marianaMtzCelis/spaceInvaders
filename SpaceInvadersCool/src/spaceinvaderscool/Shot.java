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
public class Shot extends Item {

    private int type;               // player shot = 0 -- alien shot = 1
    private boolean isShot;         // to establish if the shot is being shot
    private Game game;              // to access game's attributes
    private Item item;              // to know who does the shot belong to
    private boolean isVisible;
    private int counterCrashed;

    /**
     * Shot class constructor
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param dy
     * @param item
     * @param type
     * @param game
     */
    public Shot(int x, int y, int width, int height, int dy, Item item, int type, Game game) {
        super(x, y, width, height);
        this.dy = dy;
        this.item = item;
        this.isShot = false;
        this.type = type;
        this.game = game;
        isShot = false;
        isVisible = true;
        counterCrashed = 20;
    }

    /**
     * Ticks the shot
     */
    @Override
    public void tick() {

        // Player type of shot
        if (type == 0) {
            if (isShot) {
                setY(y + dy); // Move upwards
            } else {
                setX(game.getPlayer().getX() + 6);
                setY(game.getPlayer().getY());
            }

            // Checks upper boundary on y axis
            if (y <= 0) {
                isShot = false;
            }
            
        }

        // Alien type of shot
        if (type == 1) {
            if (isShot) {
                setY(y + dy); // Move downwards
            } else {
                setX(item.getX() + 4);
                setY(item.getY() + 5);
            }

            // Checks lower boundary on y axis
            if (y >= 290) {
                isShot = false;
            }
        }
    }

    /**
     * isShot getter
     *
     * @return isShot
     */
    public boolean isIsShot() {
        return isShot;
    }

    /**
     * isShot setter
     *
     * @param isShot
     */
    public void setIsShot(boolean isShot) {
        this.isShot = isShot;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
     

    /**
     * Renders Shot's image
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {

        // Player's shot
        if (type == 0) {
            //g.drawImage(Assets.playerShot, getX(), getY(), getWidth(), getHeight(), null);
             g.drawImage(Assets.plasma, getX(), getY(), getWidth(), getHeight(), null);
        } 
        
        // Alien's shot
        if (type == 1 && isVisible == true) {
            g.drawImage(Assets.alienShot, getX(), getY(), getWidth(), getHeight(), null);
        }

    }

}
