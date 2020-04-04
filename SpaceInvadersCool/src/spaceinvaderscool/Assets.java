/*
 * Mariana Martínez Celis A01194953
 * Diego Gomez Cota A00824758
 * Parcial 2
 */
package spaceinvaderscool;

import java.awt.image.BufferedImage;

/**
 *
 * @author marianamtzcelis and diegomezcota
 */
public class Assets {

    public static BufferedImage background;     // to store background image
    public static BufferedImage player;         // to store player image
    public static BufferedImage playerShot;     // to store playerShot image
    public static BufferedImage alienShot;      // to store alienShot image
    public static BufferedImage alien;          // to store alien image
    public static BufferedImage spritesPlasma;  // to store plasma sprites
    public static BufferedImage spritesPlasma2; // to store plasma 2 sprites
    public static BufferedImage plasma;         // to store plasma image
    public static BufferedImage plasmaShock[];  // to store array of plasma images
    public static BufferedImage plasmaShock2[]; // to store array of schocked plasma images
    public static BufferedImage spritesExplosion;   // to store explosion sprites
    public static BufferedImage explosion[];    // to store explosion array
    public static BufferedImage trash1;         // to store apple image
    public static BufferedImage trash2;         // to store banana image
    public static BufferedImage trash3;         // to store wrapper image
    public static BufferedImage gameOver;       // to store gameOver image
    public static BufferedImage missionA;       // to store mission Accomplished image
    public static SoundClip attack;             // to store attack sound clip
    public static SoundClip attacked;           // to store attacked sound clip 
    public static SoundClip end;                // to store end sound clip
    public static SoundClip apollo;           // to store sound if player loses
  

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
        missionA = ImageLoader.loadImage("/images/missionAccomplished.png");

        // Getting sounds from file
        attack = new SoundClip("/sounds/attack.wav");
        attacked = new SoundClip("/sounds/attacked.wav");
        end = new SoundClip("/sounds/end.wav");
        apollo = new SoundClip("/sounds/apollo.wav");

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

        // Fills arrays of images for animations
        for (int i = 0; i < 3; i++) {
            plasmaShock[i] = spriteSheetP.crop(i * 263 + 33, 37, 263, 274);
        }

        for (int i = 0; i < 4; i++) {
            plasmaShock2[i] = spriteSheetP.crop(i * 225 + 13, 45, 225, 167);
        }

        for (int i = 0; i < 4; i++) {
            explosion[i] = spriteSheetE.crop(i * 220 + 45, 52, 220, 193);
        }

    }
}
