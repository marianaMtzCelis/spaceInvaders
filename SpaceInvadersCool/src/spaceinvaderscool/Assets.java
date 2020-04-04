/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.image.BufferedImage;

/**
 *
 * @author marianamtzcelis
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage player;     // to store player image
    public static BufferedImage playerShot; // to store playerShot image
    public static BufferedImage alienShot;  // to store alienShot image
    public static BufferedImage alien;      // to store alien image
    public static BufferedImage spritesPlasma;
    public static BufferedImage spritesPlasma2;
    public static BufferedImage plasma;
    public static BufferedImage plasmaShock[];
    public static BufferedImage plasmaShock2[];
    public static BufferedImage spritesExplosion;
    public static BufferedImage explosion[];
    public static BufferedImage trash1;
    public static BufferedImage trash2;
    public static BufferedImage trash3;
    public static BufferedImage gameOver;
    public static SoundClip attack;
    public static SoundClip attacked;
    public static SoundClip end;
    

    /**
     * Initializing the images of the game
     */
    public static void init() {
        
        // Getting images from file
        background = ImageLoader.loadImage("/images/background.png");
        player = ImageLoader.loadImage("/images/alien3.png");
        playerShot = ImageLoader.loadImage("/images/shot.png");
        alienShot = ImageLoader.loadImage("/images/bomb.png");
        alien = ImageLoader.loadImage("/images/enemy.png");
        trash1 = ImageLoader.loadImage("/images/trash1.png");
        trash2 = ImageLoader.loadImage("/images/trash2.png");
        trash3 = ImageLoader.loadImage("/images/trash3.png");
        gameOver = ImageLoader.loadImage("/images/gameOver.jpg");
        
        // Getting sounds from file
        attack = new SoundClip("/sounds/attack.wav");
        attacked = new SoundClip("/sounds/attacked.wav");
        end = new SoundClip("/sounds/end.wav");
        
        // Get images from a spriteSheet
        spritesPlasma = ImageLoader.loadImage("/images/plasma.png");
        SpriteSheet spriteSheetP = new SpriteSheet(spritesPlasma);
        plasma = spriteSheetP.crop(930, 78, 165, 180);
        spritesPlasma2 = ImageLoader.loadImage("/images/plasma2.png");
        SpriteSheet spriteSheetP2 = new SpriteSheet(spritesPlasma2);
        spritesExplosion = ImageLoader.loadImage("/images/boomboom.png");
        SpriteSheet spriteSheetE = new SpriteSheet(spritesExplosion);
        
        // Create arrays of images
        plasmaShock = new BufferedImage[3];
        plasmaShock2 = new BufferedImage[4];
        explosion = new BufferedImage[4];
        
        for (int i = 0; i < 3; i++) {
            plasmaShock[i] = spriteSheetP.crop(i*263+33, 37, 263, 274);
        }
        
        for (int i = 0; i < 4; i++) {
            plasmaShock2[i] = spriteSheetP.crop(i*225+13, 45, 225, 167);
        }
        
         for (int i = 0; i < 4; i++) {
            explosion[i] = spriteSheetE.crop(i*220+45, 52, 220, 193);
        }

    }
}
