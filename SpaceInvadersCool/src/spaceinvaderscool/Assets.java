/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaderscool;

import java.awt.image.BufferedImage;

/**
 *
 * @author marianamtzcelis
 */
public class Assets {
    
    public static BufferedImage background; // to store background image
    public static BufferedImage player;
    public static BufferedImage playerShot;
    public static BufferedImage alienShot;
    public static BufferedImage alien;
    
    
     public static void init() {
        background = ImageLoader.loadImage("/images/background.png");
        player = ImageLoader.loadImage("/images/player.png");
        playerShot = ImageLoader.loadImage("/images/shot.png");
        alienShot = ImageLoader.loadImage("/images/bomb.png");
        alien = ImageLoader.loadImage("/images/alien.png");
        
        
    }
    
}
