/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author marianamtzcelis and diegomezcota
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
    private Player player;          // to set the player
    private KeyManager keyManager;  // to set key manager
    private Shot shotPlayer;        // to set player's shot
    private LinkedList<Alien> listaAliens; // to store miltiple Aliens
    private boolean isPaused;       // to pause or unpause game
    private int vidas;
    private LinkedList<Integer> highScores; // to store the top five high scores  

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
        vidas = 5;
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

        shotPlayer = new Shot(player.getX(), player.getY(), 8, 8, -4, player, 0, this);

        listaAliens = new LinkedList<Alien>();

        // formation of 24 aliens
        int yo = 5; // initial y value
        for (int i = 1; i <= 4; i++) {
            int xo = 150; //  initial x  value
            for (int j = 1; j <= 6; j++) {
                // create each alien
                Alien alien = new Alien(xo, yo, 12, 12, this, 1);
                // add each alien to the linked list
                listaAliens.add(alien);
                xo += 20; // update x position
            }
            yo += 15; // update y position
        }

    }

    /**
     * Runs the game
     */
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
    
    private void Save(String strFileName) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(strFileName));
            // variables to save (vidas, score, hits)
            int vidasToSave = this.vidas;
            // number of enemies to save its directions
            int malos = 24;
////            // saving more attributes
////            writer.print("" + vidasToSave + "/" + score + "/" + counterVidas + "/" + isPaused + "/" + azarMalos);
////            // variables to store coordinates
////            int x, y;
////            // save every enemy coordinate
////            for (Enemy enemy : lista) {
////                x = enemy.getX();
////                y = enemy.getY();
////                writer.print("/" + x + "/" + y);
////            }
////            // save every ally coordinate
////            writer.print("/" + azarBuenos);
////            for (GoodGuy gg : listaBuenos) {
////                x = gg.getX();
////                y = gg.getY();
////                writer.print("/" + x + "/" + y);
////            }
////            // save player's direction and coordinates
////            writer.print("/" + player.getX() + "/" + player.getY() + "/" + player.getDirection());
////            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not found CALL 911");
        }
    }

    /**
     * Ticks the whole game
     */
    private void tick() {

        // Ticks each object
        keyManager.tick();
        player.tick();
        shotPlayer.tick();

        // Checks if the user pressed the save option
        if (getKeyManager().save) {
            getKeyManager().releaseKey(KeyEvent.VK_G);
            //Save("Progress.txt");
        }
        
        // Checks if the user pressed the load option
        if (getKeyManager().load) {
            //isPaused = true;
            getKeyManager().releaseKey(KeyEvent.VK_C);
            //Load("Progress.txt");
        }
        
        // player shoots when user clicks the space bar
        if (keyManager.space) {
            shotPlayer.setIsShot(true);
        }

        for (Alien alien : listaAliens) {
            alien.tick(); // ticks each alien

            
            // Repositions aliens once they get to the boundaries on the x axis
            if (alien.getX() <= 10 || alien.getX() >= this.getWidth() - 10) {
                for (Alien alien2 : listaAliens) {
                    alien2.setY(alien2.getY() + 15);
                    // Changes alien's direction 
                    if (alien2.getDirection() == 0) {
                        alien2.setDirection(1);
                    } else {
                        alien2.setDirection(0);
                    }
                }
            }
            
            
            // checks if aliens and shotPlayer collide
            if (shotPlayer.colision(alien)) {
                shotPlayer.setIsShot(false); 
                alien.setJustCrashed(true);
                alien.getShot().setIsVisible(false);
            }
            
            // checks player's colusion with alien bombs
            if (player.colision(alien.getShot())) {
                vidas--;
                alien.getShot().setIsShot(false);
                player.setJustCrashed(true);
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
            g.drawString("Vidas:" + vidas, 30, 320);

            // renders each alien on the linked list and each alien's shot
            for (Alien alien : listaAliens) {
                alien.getShot().render(g);
                alien.render(g);
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
     * setting the thread for the game
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
