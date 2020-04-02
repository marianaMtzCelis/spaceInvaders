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

    /**
     * Initializing the images of the game
     */
    public static void init() {
        
        // Getting images from file
        background = ImageLoader.loadImage("/images/background.png");
        player = ImageLoader.loadImage("/images/player.png");
        playerShot = ImageLoader.loadImage("/images/shot.png");
        alienShot = ImageLoader.loadImage("/images/bomb.png");
        alien = ImageLoader.loadImage("/images/alien.png");
        
        // Get images from a spriteSheet
        spritesPlasma = ImageLoader.loadImage("/images/plasma.png");
        SpriteSheet spriteSheetP = new SpriteSheet(spritesPlasma);
        plasma = spriteSheetP.crop(930, 78, 165, 180);
        spritesPlasma2 = ImageLoader.loadImage("/images/plasma2.png");
        SpriteSheet spriteSheetP2 = new SpriteSheet(spritesPlasma2);
        
        // Create arrays of images
        plasmaShock = new BufferedImage[3];
        plasmaShock2 = new BufferedImage[4];
        
        for (int i = 0; i < 3; i++) {
            plasmaShock[i] = spriteSheetP.crop(i*263+33, 37, 263, 274);
        }
        
        for (int i = 0; i < 4; i++) {
            plasmaShock2[i] = spriteSheetP.crop(i*225+13, 45, 225, 167);
        }

    }
}
