package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;
import org.androidforfun.framework.Game;
import org.androidforfun.framework.Polygon;

public class Ship extends Actor {
    private static final long SHIP_EXPLOSION_TIME = 2;

    static final double SHIP_ANGLE_STEP = Math.PI / Game.FPS;
    static final float SHIP_SPEED_STEP = (float) 15.0 / Game.FPS;
    static final double MAX_SHIP_SPEED  = 1.25 * Asteroid.MAX_ROCK_SPEED;

    private Polygon shape;
    private Polygon fwdThrusters;
    private Polygon revThruster;
    private int lives;
    private long lastShot;
    private long lastMovement;
    private float stateTime;
    private ShipState status;
    private float angle;

    // the possible ship status values
    public enum ShipState {
        Alive,
        Exploding
    }

    public Ship() {
        super(0, 0, 0, 0);
        float vertexes[] = { 0, -10, 7, 10, -7, 10 };
        shape = new Polygon(vertexes);
        shape.setPosition(160, 200);

        float fwdThrusterVertexes[] = { 0, 12, -3, 16, 0, 26 };
        fwdThrusters = new Polygon(fwdThrusterVertexes);
        fwdThrusters.setPosition(160, 200);

        float revThrusterVertexes[] = { -2, 12, -4, 14, -2, 20, 0, 14, 2, 12, 4, 14, 2, 20, 0, 14 };
        revThruster = new Polygon(revThrusterVertexes);
        revThruster.setPosition(160, 200);

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
        if (status == ShipState.Exploding)
            return;

        if ((System.currentTimeMillis() - lastMovement) > 20) {
            angle-=SHIP_ANGLE_STEP;
            shape.rotate(angle);
            lastMovement=System.currentTimeMillis();
        }
    }

    public void rotateRight() {
        if (status == ShipState.Exploding)
            return;

        if ((System.currentTimeMillis() - lastMovement) > 20) {
            angle+=SHIP_ANGLE_STEP;
            shape.rotate(angle);
            lastMovement=System.currentTimeMillis();
        }
    }

    public void moveUp() {
        if (status == ShipState.Exploding)
            return;

        float dx = (float) (SHIP_SPEED_STEP * -Math.sin(angle));
        float dy = (float) (SHIP_SPEED_STEP *  Math.cos(angle));
        shape.setPosition((float)(shape.getX()+dx), (float)(shape.getY()+dy));
        //shape.setPosition((float)(shape.getX()+1), (float)(shape.getY()+1));

        // Don't let ship go past the speed limit.
        //float speed = (float) Math.sqrt(dx * dx + dy * dy);
        //if (speed > MAX_SHIP_SPEED) {
        //    dx = (float) (MAX_SHIP_SPEED * -Math.sin(angle));
        //    dy = (float) (MAX_SHIP_SPEED *  Math.cos(angle));
        //    shape.setPosition((float)(shape.getX()+dx), (float)(shape.getY()+dy));
        //}
    }

    public void moveDown() {
        if (status == ShipState.Exploding)
            return;

        float dx = (float) (SHIP_SPEED_STEP * -Math.sin(angle));
        float dy = (float) (SHIP_SPEED_STEP *  Math.cos(angle));
        shape.setPosition((float)(shape.getX()-dx), (float)(shape.getY()-dy));
        //shape.setPosition((float)(shape.getX()-1), (float)(shape.getY()-1));

        // Don't let ship go past the speed limit.
        //float speed = (float) Math.sqrt(dx * dx + dy * dy);
        //if (speed > MAX_SHIP_SPEED) {
        //    dx = (float) (MAX_SHIP_SPEED * -Math.sin(angle));
        //    dy = (float) (MAX_SHIP_SPEED *  Math.cos(angle));
        //    shape.setPosition((float)(shape.getX()+dx), (float)(shape.getY()+dy));
        //}
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

    public Polygon getShape() {
        return shape;
    }

    public Polygon getFwdThrusters() {
        return fwdThrusters;
    }

    public Polygon getRevThruster() {
        return revThruster;
    }

}
