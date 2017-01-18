package org.androidforfun.spacewars.model;

/**
 * Created by sasadangelo on 18/01/2017.
 */

public class Point {
    private int x;
    private int y;

    Point() {
    }

    Point(int x, int y) {
        this.x=x;
        this.y=y;
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
}
