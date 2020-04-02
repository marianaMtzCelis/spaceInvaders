/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author antoniomejorado
 */
public class KeyManager implements KeyListener {

    public boolean right;      // flag to move right the player
    public boolean left;       // flag to move left the player
    public boolean space;      // flag for player to shoot
    private boolean keys[];    // to store all the flags for every key

    /**
     * Initializes boolean array to store if keys are being pressed
     */
    public KeyManager() {
        keys = new boolean[256];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
    }

    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        right = keys[KeyEvent.VK_RIGHT];
        left = keys[KeyEvent.VK_LEFT];
        space = keys[KeyEvent.VK_SPACE];
    }
}
