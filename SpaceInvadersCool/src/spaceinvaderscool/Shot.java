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
    private boolean isVisible;      // to store if shot is visible
    private int counterCrashed;     // to store how much time has the shot being shot
    private Animation plasmaAnimation;  // to store plasma animation
    private int randTrash;          // to store random item to be thrown by spaceman
    private boolean missedShot;

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
        randTrash = (int) (Math.random() * 3) + 1; // Generates random number to set which type of trash to throw
        missedShot = false;
        this.plasmaAnimation = new Animation(Assets.plasmaShock, 100);
    }

    public boolean isMissedShot() {
        return missedShot;
    }

    public void setMissedShot(boolean missedShot) {
        this.missedShot = missedShot;
    }
    
    /**
     * isVisible getter
     *
     * @return isVisible
     */
    public boolean isIsVisible() {
        return isVisible;
    }

    /**
     * counterCrashed getter
     *
     * @return counterCrashed
     */
    public int getCounterCrashed() {
        return counterCrashed;
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
     * Ticks the shot
     */
    @Override
    public void tick() {

        // Player type of shot
        if (type == 0) {
            if (isShot) {
                missedShot = false;
                setY(y + dy); // Move upwards
                plasmaAnimation.tick();
            } else {
                setX(game.getPlayer().getX() + 25);
                setY(game.getPlayer().getY());
            }

            // Checks upper boundary on y axis
            if (y <= 0) {
                isShot = false;
                missedShot = true;
            }

        }

        // Alien type of shot
        if (type == 1) {
            if (isVisible) {
                if (isShot) {
                    setY(y + dy); // Move downwards
                } else {
                    setX(item.getX() + 15);
                    setY(item.getY() + 10);
                }

                // Checks lower boundary on y axis
                if (y >= 480) {
                    isShot = false;
                }
            } else {
                setY(-80);
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
        if (type == 0 && isVisible == true) {
            g.drawImage(plasmaAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }

        // Alien's shot
        if (type == 1 && isVisible == true) {
            // Renders image depending on the random number
            switch (randTrash) {
                case 1:
                    g.drawImage(Assets.trash1, getX(), getY(), getWidth(), getHeight(), null);
                    break;
                case 2:
                    g.drawImage(Assets.trash2, getX(), getY(), getWidth(), getHeight(), null);
                    break;
                case 3:
                    g.drawImage(Assets.trash3, getX(), getY(), getWidth(), getHeight(), null);
                    break;
            }
        }

    }

}
