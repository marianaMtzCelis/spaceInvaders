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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

//dasdasd

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
    private LinkedList<Alien> nuevosAliens; // to store miltiple Aliens
    private boolean isPaused;       // to pause or unpause game
    private int vidas;              // to store vidas value
    private int score;              // to store game score
    private int[] highScores;       // to store the top five high scores  
    private boolean gameOver;       // to store if game is over or not
    private int enemies;            // to store alive enemies
    private boolean winner;

    /**
     * Loads High Scores from file
     *
     * @param strFileName
     */
    public void loadHighScores(String strFileName) {
        try {
            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            line = reader.readLine();
            datos = line.split("/");
            for (int i = 0; i < 5; ++i) {
                highScores[i] = Integer.parseInt(datos[i]);
            }
        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }

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
        vidas = 10;
        highScores = new int[5];
        loadHighScores("HighScores.txt");
        score = 0;
        gameOver = false;
        enemies = 24;
        winner = false;
        // line for debugging highscores later
//        for (int i = 0; i < 5; i++)
//            System.out.println(highScores[i]);
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

    public Shot getShotPlayer() {
        return shotPlayer;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();

        player = new Player(this.width / 2 - 35, 420, 1, 70, 70, this);

        display.getJframe().addKeyListener(keyManager);

        shotPlayer = new Shot(player.getX(), player.getY(), 20, 20, -4, player, 0, this);

        listaAliens = new LinkedList<Alien>();

        // formation of 24 aliens
        int yo = 5; // initial y value
        for (int i = 1; i <= 4; i++) {
            int xo = 150; //  initial x  value
            for (int j = 1; j <= 6; j++) {
                // create each alien
                Alien alien = new Alien(xo, yo, 40, 40, this, 1);
                // add each alien to the linked list
                listaAliens.add(alien);
                xo += 50; // update x position
            }
            yo += 40; // update y position
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

    /**
     * Saves current game into txt file
     *
     * @param strFileName
     */
    private void Save(String strFileName) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(strFileName));
            // variables to save (vidas, score, hits)
            int vidasToSave = this.vidas;
            int scoreToSave = this.score;
            //  saving more attributes
            writer.print("" + vidasToSave + "/" + scoreToSave + "/");
            //  store player progress
            boolean bJustCrashed = this.player.isJustCrashed();
            int iJustCrashed = bJustCrashed == true ? 1 : 0;
            int counterCrashed = this.player.getCounterCrashed();
            int setXPlayer = this.player.getX();
            int setYPlayer = this.player.getY();
            writer.print("" + iJustCrashed + "/" + counterCrashed + "/" + setXPlayer + "/" + setYPlayer);
            //  store player shot
            int sp_X = this.shotPlayer.getX();
            int sp_Y = this.shotPlayer.getY();
            boolean b_sp_shot = this.shotPlayer.isIsShot();
            int i_sp_shot = b_sp_shot ? 1 : 0;
            boolean b_sp_visible = this.shotPlayer.isIsVisible();
            int i_sp_visible = b_sp_visible ? 1 : 0;
            int sp_counterCrashed = this.shotPlayer.getCounterCrashed();
            writer.print("/" + sp_X + "/" + sp_Y + "/" + i_sp_shot + "/" + i_sp_visible + "/" + sp_counterCrashed);
            //  store all aliens information      
            // variables to store coordinates
            int alien_x, alien_y, as_x, as_y;
            boolean alien_bIsVisible, alien_bJustCrashed, as_bIsShot, as_bIsVisible;
            int alien_iIsVisible, alien_iJustCrashed, alien_iCounterCrashed, as_iIsShot, as_iIsVisible, as_counterCrashed;
            Shot alienShot;
            for (Alien alien : listaAliens) {
                alien_x = alien.getX();
                alien_y = alien.getY();
                alien_bIsVisible = alien.isIsVisible();
                alien_iIsVisible = alien_bIsVisible ? 1 : 0;
                alien_bJustCrashed = alien.isJustCrashed();
                alien_iJustCrashed = bJustCrashed ? 1 : 0;
                alien_iCounterCrashed = alien.getCounterCrashed();
                writer.print("/" + alien_x + "/" + alien_y + "/" + alien_iIsVisible + "/" + alien_iJustCrashed + "/" + alien_iCounterCrashed);
                //  alien shot info
                alienShot = alien.getShot();
                as_x = alienShot.getX();
                as_y = alienShot.getY();
                as_bIsShot = alienShot.isIsShot();
                as_iIsShot = as_bIsShot ? 1 : 0;
                as_bIsVisible = alienShot.isIsVisible();
                as_iIsVisible = as_bIsVisible ? 1 : 0;
                as_counterCrashed = alienShot.getCounterCrashed();
                writer.print("/" + as_x + "/" + as_y + "/" + as_iIsShot + "/" + as_iIsVisible + "/" + as_counterCrashed);
            }
            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not found CALL 911");
        }
    }

    /**
     * Loads previously saved game from txt file
     *
     * @param strFileName
     */
    public void Load(String strFileName) {
        try {
            // to read from file
            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            line = reader.readLine();
            datos = line.split("/");
            int nDatos = 0; // to keep track of the information read
            // Loads each saved variable and sets it to the game's variable
            int loadedVidas = Integer.parseInt(datos[nDatos++]);
            this.vidas = loadedVidas;
            int loadedScore = Integer.parseInt(datos[nDatos++]);
            this.score = loadedScore;
            // load player status
            this.player.setJustCrashed(Integer.parseInt(datos[nDatos++]) == 1);
            this.player.setCounterCrashed(Integer.parseInt(datos[nDatos++]));
            this.player.setX(Integer.parseInt(datos[nDatos++]));
            this.player.setY(Integer.parseInt(datos[nDatos++]));
            // load player shot status
            this.shotPlayer.setX(Integer.parseInt(datos[nDatos++]));
            this.shotPlayer.setY(Integer.parseInt(datos[nDatos++]));
            this.shotPlayer.setIsShot(Integer.parseInt(datos[nDatos++]) == 1);
            this.shotPlayer.setIsVisible(Integer.parseInt(datos[nDatos++]) == 1);
            this.shotPlayer.setCounterCrashed(Integer.parseInt(datos[nDatos++]));
            // load every alien info
            nuevosAliens = new LinkedList<Alien>();
            // Loads to new list of enemies each enemy's x and y position
            for (int i = 1; i <= 24; i++) {
                // load alien info
                int loadedX = Integer.parseInt(datos[nDatos++]);
                int loadedY = Integer.parseInt(datos[nDatos++]);
                Alien alienToSet = new Alien(loadedX, loadedY, 40, 40, this, 1);
                alienToSet.setIsVisible(Integer.parseInt(datos[nDatos++]) == 1);
                alienToSet.setJustCrashed(Integer.parseInt(datos[nDatos++]) == 1);
                alienToSet.setCounterCrashed(Integer.parseInt(datos[nDatos++]));
                //writer.print("/" + as_x + "/" + as_y + "/" + as_iIsShot + "/" + as_iIsVisible + "/" + as_counterCrashed);
                // load alien shot info
                loadedX = Integer.parseInt(datos[nDatos++]);
                loadedY = Integer.parseInt(datos[nDatos++]);
                Shot shotToSet = new Shot(loadedX, loadedY, 20, 20, 2, alienToSet, 1, this);
                shotToSet.setIsShot(Integer.parseInt(datos[nDatos++]) == 1);
                shotToSet.setIsVisible(Integer.parseInt(datos[nDatos++]) == 1);
                shotToSet.setCounterCrashed(Integer.parseInt(datos[nDatos++]));
                alienToSet.setShot(shotToSet);
                nuevosAliens.add(alienToSet);
            }
            // Copies new list into game's list
            this.listaAliens = this.nuevosAliens;
            reader.close();
        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }

    private void updateHighScore(){
        int[] allHighScores = new int[6];
        for (int i = 0; i < 5; ++i){
            allHighScores[0] = highScores[0];
        }
        allHighScores[5] = score;
        Arrays.sort(allHighScores);
        for (int i = 0; i < 5; ++i){
            highScores[i] = allHighScores[5-i];
            System.out.println(highScores[i]);
        }
        try{
            PrintWriter writer = new PrintWriter(new FileWriter("HighScores.txt"));
            writer.print("" + highScores[0] + "/" + highScores[1] + "/" + highScores[2] + "/" + highScores[3] + "/" + highScores[4]);
            writer.close();
        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }
    
    /**
     * Ticks the whole game
     */
    private void tick() {

        if (!gameOver && !winner) {

            // Ticks each object
            keyManager.tick();
            player.tick();
            shotPlayer.tick();

            // Checks if the user pressed the save option
            if (getKeyManager().save) {
                getKeyManager().releaseKey(KeyEvent.VK_G);
                Save("Progress.txt");
            }

            // Checks if the user pressed the load option
            if (getKeyManager().load) {
                //isPaused = true;
                getKeyManager().releaseKey(KeyEvent.VK_C);
                Load("Progress.txt");
            }

            // player shoots when user clicks the space bar
            if (keyManager.space) {
                shotPlayer.setIsShot(true);
            }

            for (Alien alien : listaAliens) {
                alien.tick(); // ticks each alien

                // Repositions aliens once they get to the boundaries on the x axis
                if (alien.getX() <= 10 || alien.getX() >= this.getWidth() - 40) {
                    for (Alien alien2 : listaAliens) {
                        alien2.setY(alien2.getY() + 20);
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
                    Assets.attack.play();
                    score += 100;
                    if (score % 1200 == 0 && vidas < 10) ++vidas; 
                    enemies--;
                    if (enemies <= 0) {
                        winner = true;
                        Assets.end.play();
                    }
                }

                // checks player's colusion with alien bombs
                if (player.colision(alien.getShot())) {
                    vidas--;
                    alien.getShot().setIsShot(false);
                    player.setJustCrashed(true);
                    shotPlayer.setIsVisible(false);
                    Assets.attacked.play();
                    if (vidas <= 0) {
                        updateHighScore();
                        gameOver = true;
                        Assets.apollo.play();
                    }
                }

                // checks y position of alien 
                if (alien.getY() >= 380) {
                    gameOver = true;
                    Assets.end.play();
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
            g.drawLine(0, 480, this.width, 480);
            player.render(g);

            // renders instructions for user
            g.drawString("Score: " + score, 20, 500);
            g.drawString("Presione G para guardar", 20, 515);
            g.drawString("Presione C para cargar", 20, 530);
            g.drawString("Presione flechas para moverse", 20, 545);
            g.drawString("Presione espacio para disparar", 20, 560);

            // renders each alien on the linked list and each alien's shot
            for (Alien alien : listaAliens) {
                alien.getShot().render(g);
                alien.render(g);
            }

            // renders vidas rectangle
            g.drawString("Vidas:" + vidas, 620, 560);
            g.drawRect(470, 500, 200, 40);
            switch (vidas) {
                case 10:
                    g.fillRect(470, 500, 200, 40);
                    break;
                case 9:
                    g.fillRect(470, 500, 180, 40);
                    break;
                case 8:
                    g.fillRect(470, 500, 160, 40);
                    break;
                case 7:
                    g.fillRect(470, 500, 140, 40);
                    break;
                case 6:
                    g.fillRect(470, 500, 120, 40);
                    break;
                case 5:
                    g.fillRect(470, 500, 100, 40);
                    break;
                case 4:
                    g.fillRect(470, 500, 80, 40);
                    break;
                case 3:
                    g.fillRect(470, 500, 60, 40);
                    break;
                case 2:
                    g.fillRect(470, 500, 40, 40);
                    break;
                case 1:
                    g.fillRect(470, 500, 20, 40);
                    break;
            }

            // renders game over image and high scores
            if (gameOver) {
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
                g.setColor(Color.cyan);
                g.drawRect(width/2-90, height/2, 200, 130);
                g.drawString("High Scores", width/2-25, height/2 + 30);
                g.drawString("1) " + highScores[0], width/2-10, height/2 + 50);
                g.drawString("2) " + highScores[1], width/2-10, height/2 + 65);
                g.drawString("3) " + highScores[2], width/2-10, height/2 + 80);
                g.drawString("4) " + highScores[3], width/2-10, height/2 + 95);
                g.drawString("5) " + highScores[4], width/2-10, height/2 + 110);
                
            }
            
            // renders mission accomplished message and high scores
            if (winner) {
                g.drawImage(Assets.missionA, 0, 0, width, height, null);
                g.setColor(Color.magenta);
                g.drawRect(width/2-110, height/2, 200, 130);
                g.drawString("High Scores", width/2-50, height/2 + 30);
                g.drawString("1) " + highScores[0], width/2-32, height/2 + 50);
                g.drawString("2) " + highScores[1], width/2-32, height/2 + 65);
                g.drawString("3) " + highScores[2], width/2-32, height/2 + 80);
                g.drawString("4) " + highScores[3], width/2-32, height/2 + 95);
                g.drawString("5) " + highScores[4], width/2-32, height/2 + 110);
            }

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
