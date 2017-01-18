package org.androidforfun.spacewars.model;

public class UfoProjectile extends Projectile {
    public UfoProjectile(int x, int y) {
        super(x, y);
    }

    public void move() {
        moveBy(0, 1);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof UfoProjectile) {
                return true;
            }
        }
        return false;
    }
}
