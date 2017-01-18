package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;

import java.util.Random;

public abstract class Asteroid extends Actor {
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
