package org.androidforfun.spacewars.model;

import java.util.ArrayList;
import java.util.List;

public class Space {
    private List<Point> stars;

    public Space(int width, int height) {
        int numStars = width * height / 5000;
        stars = new ArrayList<Point>();
        for (int i = 0; i < numStars; i++)
            stars.add(new Point((int) (Math.random() * width), (int) (Math.random() * height)));
    }

    public List<Point> getStars() {
        return stars;
    }
}
