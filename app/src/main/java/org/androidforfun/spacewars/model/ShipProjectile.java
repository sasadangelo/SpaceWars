package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;

public class ShipProjectile extends Actor {
    static final double PROJECTILE_SPEED  = 2 * Asteroid.MAX_ROCK_SPEED;

    private boolean active;
    private float angle;

    ShipProjectile(int x, int y, float angle) {
        super(x, y);
        this.active = true;
        this.angle=angle;
    }

    public void kill() {
        this.active = false;
    }

    public boolean isInactive() {
        return !this.active;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof ShipProjectile) {
                ShipProjectile projectile = (ShipProjectile) object;
                if (active==projectile.active && angle==projectile.angle) {
                    return true;
                }
            }
        }
        return false;
    }

    void move() {
        this.x -= (float) Math.round(PROJECTILE_SPEED * -Math.sin(angle));
        this.y -= (float) Math.round(PROJECTILE_SPEED *  Math.cos(angle));
    }
}
