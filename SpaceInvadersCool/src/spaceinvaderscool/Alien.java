/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaderscool;

import java.awt.Graphics;

/**
 *
 * @author marianamtzcelis
 */
public class Alien extends Item {
    
    private Game game;
    private int direction;

    public Alien(int x, int y, int width, int height, Game game, int direction) {
        super(x, y, width, height);
        this.game = game;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
    
    

    @Override
    public void tick() {
        
        if (direction == 1) {
            setX(this.getX() - 1);
        } else {
            setX(this.getX() + 1);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}