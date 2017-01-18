package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;
import org.androidforfun.framework.Polygon;

public class Ship extends Actor {
    private static final long SHIP_EXPLOSION_TIME = 2;

    private Polygon shape;
    private int lives;
    private long lastShot;
    private long lastMovement;
    private float stateTime;
    private ShipState status;

    // the possible ship status values
    public enum ShipState {
        Alive,
        Exploding
    }

    public Ship() {
        super(0, 0, 0, 0);
        float vertexes[] = { 0, -10, 7, 10, -7, 10 };
        shape = new Polygon(vertexes);
        lives = 3;
        status=ShipState.Alive;
        lastShot = System.currentTimeMillis();
    }

    public void exploding(float deltaTime) {
        if (status == ShipState.Exploding) {
            if (stateTime >= SHIP_EXPLOSION_TIME) {
                lives--;
                stateTime = 0;
                //x=2* SpaceWarsWorld.CELL_WIDTH;
                //y=19* SpaceWarsWorld.CELL_HEIGHT;
                status=ShipState.Alive;
            }
        }
        stateTime += deltaTime;
    }

    public void rotateLeft() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastMovement) > 20) {
                shape.rotate(1);
                lastMovement=System.currentTimeMillis();
            }
        }
    }

    public void rotateRight() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastMovement) > 20) {
                shape.rotate(-1);
                lastMovement=System.currentTimeMillis();
            }
        }
    }

    public void  shoot() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastShot) > 320) {
                SpaceWarsWorld.getInstance().getProjectiles().add(new ShipProjectile(x+2, y));
                lastShot = System.currentTimeMillis();
            }
        }
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public boolean isExploding() {
        return status==ShipState.Exploding;
    }

    public void kill() {
        status=ShipState.Exploding;
    }

    public void destroy() {
        kill();
        lives=0;
    }

    public int getLives() {
        return lives;
    }
}
