package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;
import org.androidforfun.framework.Game;
import org.androidforfun.framework.Polygon;

public class Ship extends Actor {
    private static final long SHIP_EXPLOSION_TIME = 2;

    static final double SHIP_ANGLE_STEP = Math.PI / Game.FPS;
    static final float SHIP_SPEED_STEP = (float) 15.0 / Game.FPS;
    static final double MAX_SHIP_SPEED  = 1.25 * Asteroid.MAX_ROCK_SPEED;

    //private Polygon shape;
    private float localVertices[];
    private float worldVertices[];

    private float revThrusterLocalVertices[];
    private float revThrusterWorldVertices[];

    //private Polygon revThruster;
    private int lives;
    private long lastShot;
    private long lastMovement;
    private float stateTime;
    private ShipState status;
    private float angle;
    private float deltaAngle;
    private float deltaX, deltaY;
    private boolean dirty = true;

    private boolean shipRotatingLeft;
    private boolean shipRotatingRight;
    private boolean shipMoving;

    // the possible ship status values
    public enum ShipState {
        Alive,
        Exploding
    }

    public Ship() {
        super(153, 190, 20, 20);
        localVertices = new float[] { 0, -10, 7, 10, -7, 10 };
        revThrusterLocalVertices = new float[] { -2, 12, -4, 14, -2, 20, 0, 14, 2, 12, 4, 14, 2, 20, 0, 14 };
        //shape = new Polygon(vertexes);
        //shape.setPosition(160, 200);

        //float revThrusterVertexes[] = { -2, 12, -4, 14, -2, 20, 0, 14, 2, 12, 4, 14, 2, 20, 0, 14 };
        //revThruster = new Polygon(revThrusterVertexes);
        //revThruster.setPosition(160, 200);

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

        //if ((System.currentTimeMillis() - lastMovement) > 10) {
            angle-=SHIP_ANGLE_STEP;
            if (angle < 0)
                angle += 2 * Math.PI;
            //System.out.println("ROTATE LEFT: " + angle);
            //shape.rotate(angle);
            //shape.rotate(-(float)SHIP_ANGLE_STEP);
            //shape.setRotation(angle);
            //lastMovement=System.currentTimeMillis();
        //}
        deltaX=0;
        deltaY=0;
        dirty=true;
    }

    public void rotateRight() {
        if (status == ShipState.Exploding)
            return;

        //if ((System.currentTimeMillis() - lastMovement) > 10) {
            angle+=SHIP_ANGLE_STEP;
            if (angle > 2 * Math.PI)
                angle -= 2 * Math.PI;
            //System.out.println("ROTATE RIGHT: " + angle);
            //shape.rotate(angle);
            //shape.rotate((float)SHIP_ANGLE_STEP);
            //shape.setRotation(angle);
            //lastMovement=System.currentTimeMillis();
        //}
        deltaX=0;
        deltaY=0;
        dirty=true;
    }

    public void move() {
        if (status == ShipState.Exploding)
            return;

        float dx = (float) (SHIP_SPEED_STEP * -Math.sin(angle));
        float dy = (float) (SHIP_SPEED_STEP *  Math.cos(angle));
        deltaX-=dx;
        deltaY-=dy;

        // Don't let ship go past the speed limit.
        float speed = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (speed > MAX_SHIP_SPEED) {
            dx = (float) (MAX_SHIP_SPEED * -Math.sin(angle));
            dy = (float) (MAX_SHIP_SPEED *  Math.cos(angle));
            deltaX=-dx;
            deltaY=-dy;
        }
        dirty=true;
    }

    public void  shoot() {
        if (status == ShipState.Alive) {
            if ((System.currentTimeMillis() - lastShot) > 320) {
                SpaceWarsWorld.getInstance().getShipProjectiles().add(new ShipProjectile(x+2, y, angle));
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

    //public Polygon getShape() {
    //    return shape;
    //}

    //public Polygon getFwdThrusters() {
    //    return fwdThrusters;
    //}

    //public Polygon getRevThruster() {
    //    return revThruster;
    //}

    private void calculateWorldVertices() {

        if (worldVertices == null || worldVertices.length != localVertices.length) worldVertices = new float[localVertices.length];
        if (revThrusterWorldVertices == null || revThrusterWorldVertices.length != revThrusterLocalVertices.length) revThrusterWorldVertices = new float[revThrusterLocalVertices.length];

        x+=Math.round(deltaX);
        y+=Math.round(deltaY);

        if (this.x < 0) {
            this.x += SpaceWarsWorld.WORLD_WIDTH;
        }
        if (this.x > SpaceWarsWorld.WORLD_WIDTH) {
            this.x -= SpaceWarsWorld.WORLD_WIDTH;
        }
        if (this.y < 0) {
            this.y += SpaceWarsWorld.WORLD_HEIGHT;
        }
        if (this.y > SpaceWarsWorld.WORLD_HEIGHT) {
            this.y -= SpaceWarsWorld.WORLD_HEIGHT;
        }

        /*
         * Given a point x,y of a generic shape centered in the origin (0, 0), when it is rotated by
         * angle A the point (x,y) become (x',y') where:
         * x'=x*cos(A)-y*sin(A)
         * y'=x*sin(A)+y*cos(A)
         */
        for (int i=0; i<localVertices.length; i+=2) {
            worldVertices[i]=x+Math.round(localVertices[i] * Math.cos(angle) - localVertices[i+1] * Math.sin(angle));
            worldVertices[i+1]=y+Math.round(localVertices[i] * Math.sin(angle) + localVertices[i+1] * Math.cos(angle));
        }

        for (int i=0; i<revThrusterLocalVertices.length; i+=2) {
            revThrusterWorldVertices[i]=x+Math.round(revThrusterLocalVertices[i] * Math.cos(angle) - revThrusterLocalVertices[i+1] * Math.sin(angle));
            revThrusterWorldVertices[i+1]=y+Math.round(revThrusterLocalVertices[i] * Math.sin(angle) + revThrusterLocalVertices[i+1] * Math.cos(angle));
        }
    }

    public float[] getWorldVertices() {
        if (!dirty) return worldVertices;
        dirty=false;
        calculateWorldVertices();
        return worldVertices;
    }

    public float[] getRevThrusterWorldVertices() {
        if (!dirty) return revThrusterWorldVertices;
        dirty=false;
        calculateWorldVertices();
        return revThrusterWorldVertices;
    }

    public boolean isShipRotatingLeft() {
        return shipRotatingLeft;
    }

    public void setShipRotatingLeft(boolean shipRotatingLeft) {
        this.shipRotatingLeft = shipRotatingLeft;
    }

    public boolean isShipRotatingRight() {
        return shipRotatingRight;
    }

    public void setShipRotatingRight(boolean shipRotatingRight) {
        this.shipRotatingRight = shipRotatingRight;
    }

    public boolean isShipMoving() {
        return shipMoving;
    }

    public void setShipMoving(boolean shipMoving) {
        this.shipMoving = shipMoving;
    }
}
