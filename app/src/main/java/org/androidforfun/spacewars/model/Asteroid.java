package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Actor;
import org.androidforfun.framework.Game;
import org.androidforfun.framework.Polygon;

public class Asteroid extends Actor {
    static final int   MIN_ASTEROID_SIDES =   6; // Ranges for asteroid shape, size
    static final int   MAX_ASTEROID_SIDES =  16; // speed and rotation.
    static final int   MIN_ASTEROID_SIZE  =  20;
    static final int   MAX_ASTEROID_SIZE  =  40;
    static final float MIN_ASTEROID_SPEED =  40.0F / 60;
    static final float MAX_ASTEROID_SPEED = 240.0F / Game.FPS;
    static final float MAX_ASTEROID_SPIN  = (float) Math.PI / 60;

    private Polygon shape;
    private float angle;
    private float deltaAngle;
    private float speed;
    private float deltaX;
    private float deltaY;

    public Asteroid() {
        super(0, 0, 1, 1);

        int side = MIN_ASTEROID_SIDES + (int) (Math.random() * (MAX_ASTEROID_SIDES - MIN_ASTEROID_SIDES));
        float vertices[] = new float[side*2];
        for (int j = 0; j < side; j++) {
            float theta = (float) (2 * Math.PI / side * j);
            float radius = MIN_ASTEROID_SIZE + (int) (Math.random() * (MAX_ASTEROID_SIZE - MIN_ASTEROID_SIZE));
            int x = (int) -Math.round(radius * Math.sin(theta));
            int y = (int)  Math.round(radius * Math.cos(theta));
            vertices[2*j]=x;
            vertices[2*j+1]=y;

        }
        shape = new Polygon(vertices);
        angle = (float) (Math.random() * 2 * MAX_ASTEROID_SPIN - MAX_ASTEROID_SPIN);
        deltaAngle = (float) (Math.random() * 2 * MAX_ASTEROID_SPIN - MAX_ASTEROID_SPIN);
        speed=MIN_ASTEROID_SPEED;

        // Place the asteroid at one edge of the screen
        if (Math.random() < 0.5) {
            this.x = -SpaceWarsWorld.WORLD_WIDTH / 2;
            if (Math.random() < 0.5)
                this.x = SpaceWarsWorld.WORLD_WIDTH / 2;
            this.y = (int) Math.round(Math.random() * SpaceWarsWorld.WORLD_HEIGHT);
        } else {
            this.x = (int) Math.round(Math.random() * SpaceWarsWorld.WORLD_WIDTH);
            this.y = -SpaceWarsWorld.WORLD_HEIGHT / 2;
            if (Math.random() < 0.5)
                this.y = SpaceWarsWorld.WORLD_HEIGHT / 2;
        }

        // Set a random motion for the asteroid.
        this.deltaX = (float) Math.random() * speed;
        if (Math.random() < 0.5)
            this.deltaX = -this.deltaX;
        this.deltaY = (float) Math.random() * speed;
        if (Math.random() < 0.5)
            this.deltaY = -this.deltaY;

        if (speed < MAX_ASTEROID_SPEED)
            speed += 0.5;
    }

    //abstract public int getScore();

    //void moveLeft() {
    //    x -= 1;
    //}

    //void moveRight() {
    //    x += 1;
    //}

    public void move() {
        this.x -= (float) Math.round(speed * -Math.sin(angle));
        this.y -= (float) Math.round(speed *  Math.cos(angle));
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

        shape.setPosition(x, y);
    }

    public Polygon getShape() {
        return shape;
    }
}
