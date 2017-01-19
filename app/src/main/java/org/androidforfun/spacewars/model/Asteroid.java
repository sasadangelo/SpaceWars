package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;
import org.androidforfun.framework.Game;

import java.util.Random;

public abstract class Asteroid extends Actor {
    //static final int    MIN_ROCK_SIDES =   6; // Ranges for asteroid shape, size
    //static final int    MAX_ROCK_SIDES =  16; // speed and rotation.
    //static final int    MIN_ROCK_SIZE  =  20;
    //static final int    MAX_ROCK_SIZE  =  40;
    //static final double MIN_ROCK_SPEED =  40.0 / 60;
    static final double MAX_ROCK_SPEED = 240.0 / Game.FPS;
    //static final double MAX_ROCK_SPIN  = Math.PI / 60;

    private long lastShot;

    public Asteroid(int x, int y) {
        super(x, y, 1, 1);
        this.lastShot = System.currentTimeMillis() + new Random().nextInt(15);
    }

    abstract public int getScore();

    void moveLeft() {
        x -= 1;
    }

    void moveRight() {
        x += 1;
    }

    public void moveForward() {
        y += 2;
    }

    public Projectile shoot() {
        if ((System.currentTimeMillis() - lastShot) > 320) {
            lastShot = System.currentTimeMillis();
            return new UfoProjectile(x, y);
        }
        return null;
    }
}
