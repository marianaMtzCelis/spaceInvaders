/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaderscool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author marianamtzcelis
 */
public class Game implements Runnable {

    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player;          // player
    private KeyManager keyManager;
    private Shot shotPlayer;
    //private Alien alien;
    private LinkedList<Alien> listaAliens;

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();

    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To get the player of the game window
     *
     * @return a <code>Playert</code> value with the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();

        player = new Player(270, 280, 1, 15, 10, this);

        display.getJframe().addKeyListener(keyManager);

        shotPlayer = new Shot(player.getX(), player.getY(), 2, 10, -4, player, 0, this);

        // alien = new Alien(100,100,12,12,this);
        listaAliens = new LinkedList<Alien>();

        int yo = 5;
        for (int i = 1; i <= 4; i++) {
            int xo = 150;
            for (int j = 1; j <= 6; j++) {
                Alien alien = new Alien(xo, yo, 12, 12, this, 1);
                listaAliens.add(alien);
                xo += 20;
            }
            yo += 15;
        }

    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    /**
     * gets the keyManager
     *
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {

        keyManager.tick();
        player.tick();
        shotPlayer.tick();

        if (keyManager.space) {
            shotPlayer.setIsShot(true);
        }

        for (Alien alien : listaAliens) {
            alien.tick();
            
            if (shotPlayer.colision(alien)) {
                alien.setX(-20);
                alien.setY(-20);
            }

            if (alien.getX() <= 10 || alien.getX() >= this.getWidth() - 10) {
                for (Alien alien2 : listaAliens) {
                    alien2.setY(alien2.getY() + 15);
                    if (alien2.getDirection() == 0) {
                        alien2.setDirection(1);
                    } else {
                        alien2.setDirection(0);
                    }
                }
            }
        }

    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            shotPlayer.render(g);
            g.setColor(Color.green);
            g.drawLine(0, 290, this.width, 290);
            player.render(g);
            //alien.render(g);

            for (Alien alien : listaAliens) {
                alien.render(g);
                alien.getShot().render(g);
            }

//            // displays vidas and score
//            g.setColor(Color.black);
//            g.drawString("Vidas:" + vidas, 100, 100);
//            g.drawString("Score: " + score, 100, 120);
            // displays end image if vidas gets to 0 
//            if (vidas <= 0) {
//                g.drawImage(Assets.end, 0, 0, getWidth(), getHeight(), null);
//                Assets.backSound.stop();
//                Assets.buenos.stop();
//                Assets.malos.stop();
//              
//                
//            }
            bs.show();
            g.dispose();
        }

    }

    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
