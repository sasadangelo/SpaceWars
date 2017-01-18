package org.androidforfun.spacewars.model;

public class ShipProjectile extends Projectile {
    public ShipProjectile(int x, int y) {
        super(x, y);
    }

    public void move() {
        moveBy(0, -1);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof ShipProjectile) {
                return true;
            }
        }
        return false;
    }
}
