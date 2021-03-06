/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author antoniomejorado
 */
public abstract class Item {

    protected int x;        // to store x position
    protected int y;        // to store y position
    protected int dx;       // to store change in x position
    protected int dy;       /// to store change in y position
    protected int width;    // to store width
    protected int height;   // to store height

    /**
     * Set the initial values to create the item
     *
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     * @param width <b>width</b> width of the object
     * @param height <b>height</b> height of the object
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
//        this.dx = 2;
    }

    /**
     * Get x value
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Set x value
     *
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     *
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * gets the height from item
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * gets the width from item
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();

    /**
     * To paint the item
     *
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);

    /**
     * Checks if the player and enemy/goodGuy crash
     *
     * @param o
     * @return status
     */
    public boolean colision(Object o) {
        boolean status = false; // It does not colision
        if (o instanceof Item) {
            Rectangle rItem = new Rectangle(this.getX(), this.getY(),
                    this.getWidth(), this.getHeight());
            Item i = (Item) o;
            Rectangle rOther = new Rectangle(i.getX(), i.getY(), i.getWidth(),
                    i.getHeight());

            status = rItem.intersects(rOther);
        }
        return status;
    }
}
