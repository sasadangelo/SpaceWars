/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Alien Invaders project.
 *
 *  Alien Invaders is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Alien Invaders is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License.
 */
package org.androidforfun.spacewars.view;

import android.graphics.Color;

import org.androidforfun.framework.Gdx;
import org.androidforfun.framework.Polygon;
import org.androidforfun.spacewars.model.Point;
import org.androidforfun.spacewars.model.Ship;
import org.androidforfun.spacewars.model.Space;
import org.androidforfun.spacewars.model.SpaceWarsWorld;

/*
 * The responsibility of this class is to draw the model representation of Space Wars world.
 *
 * @author Salvatore D'Angelo
 */
public class SpaceWarsWorldRenderer {
    // Each cell is a 4x4 matrix. The reason we need cells is that ship and aliens do move across a
    // cells requires 4 steps in horizontal and 4 in vertical.
    //static final int CELL_WIDTH_PIXEL = 5;
    //static final int CELL_HEIGHT_PIXEL = 5;

    //private int lastAlienXPosition;
    //private boolean alienMove=false;

    //private Pixmap alienGood;
    //private Pixmap alienBad;
    //private Pixmap alienUgly;

    public SpaceWarsWorldRenderer() {
        //alienGood = Assets.alienGood1;
        //alienBad = Assets.alienBad1;
        //alienUgly = Assets.alienUgly1;
    }

    /*
     This method draw the model representation of Droids world.
     */
    public void draw() {
        SpaceWarsWorld world = SpaceWarsWorld.getInstance();
        Space space = world.getSpace();

        Gdx.graphics.drawRect(0, 0, 320, 480, 0);
        for (Point p : space.getStars()) {
            Gdx.graphics.drawPixel(p.getX(), p.getY(), Color.WHITE);
        }

        //Polygon polygon = world.getShip().getShape();
        //float vertices[] = polygon.getTransformedVertices();
        float vertices[] = world.getShip().getWorldVertices();
        float revThrusterVertices[] = world.getShip().getRevThrusterWorldVertices();

        Gdx.graphics.drawLine((int) vertices[0], (int)vertices[1], (int)vertices[2], (int)vertices[3], Color.WHITE);
        Gdx.graphics.drawLine((int) vertices[2], (int)vertices[3], (int)vertices[4], (int)vertices[5], Color.WHITE);
        Gdx.graphics.drawLine((int) vertices[4], (int)vertices[5], (int)vertices[0], (int)vertices[1], Color.WHITE);

        if (world.getShip().isShipMoving()) {
            Gdx.graphics.drawLine((int) revThrusterVertices[0], (int)revThrusterVertices[1], (int)revThrusterVertices[2], (int)revThrusterVertices[3], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[2], (int)revThrusterVertices[3], (int)revThrusterVertices[4], (int)revThrusterVertices[5], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[4], (int)revThrusterVertices[5], (int)revThrusterVertices[6], (int)revThrusterVertices[7], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[6], (int)revThrusterVertices[7], (int)revThrusterVertices[8], (int)revThrusterVertices[9], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[8], (int)revThrusterVertices[9], (int)revThrusterVertices[10], (int)revThrusterVertices[11], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[10], (int)revThrusterVertices[11], (int)revThrusterVertices[12], (int)revThrusterVertices[13], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[12], (int)revThrusterVertices[13], (int)revThrusterVertices[14], (int)revThrusterVertices[15], Color.WHITE);
            Gdx.graphics.drawLine((int) revThrusterVertices[14], (int)revThrusterVertices[15], (int)revThrusterVertices[0], (int)revThrusterVertices[1], Color.WHITE);
        }

        /*
        Gdx.graphics.drawPixmap(Assets.ship, ship.getX() * CELL_WIDTH_PIXEL, ship.getY() * CELL_HEIGHT_PIXEL);

        for (Shield shield : SpaceWarsWorld.getInstance().getShields()) {
            if (shield.getSize().equals(Shield.ShieldSize.LARGE)) {
                g.drawPixmap(Assets.shieldLarge, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            } else if (shield.getSize().equals(Shield.ShieldSize.MEDIUM)) {
                g.drawPixmap(Assets.shieldMedium, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            } else if (shield.getSize().equals(Shield.ShieldSize.SMALL)) {
                g.drawPixmap(Assets.shieldSmall, shield.getX()*CELL_WIDTH_PIXEL, shield.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        if (SpaceWarsWorld.getInstance().getAliens().size()>0 &&
                SpaceWarsWorld.getInstance().getAliens().get(0).getX() != lastAlienXPosition) {
            if (alienMove) {
                alienGood = Assets.alienGood2;
                alienBad = Assets.alienBad2;
                alienUgly = Assets.alienUgly2;
            } else {
                alienGood = Assets.alienGood1;
                alienBad = Assets.alienBad1;
                alienUgly = Assets.alienUgly1;
            }
            alienMove=!alienMove;
            lastAlienXPosition= SpaceWarsWorld.getInstance().getAliens().get(0).getX();
        }

        for (Alien alien : SpaceWarsWorld.getInstance().getAliens()) {
            if (alien instanceof GoodAlien) {
                g.drawPixmap(alienGood, alien.getX()*CELL_WIDTH_PIXEL, alien.getY()*CELL_HEIGHT_PIXEL);
            } else if (alien instanceof BadAlien) {
                g.drawPixmap(alienBad, alien.getX()*CELL_WIDTH_PIXEL, alien.getY()*CELL_HEIGHT_PIXEL);
            } else if (alien instanceof UglyAlien) {
                g.drawPixmap(alienUgly, alien.getX()*CELL_WIDTH_PIXEL, alien.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        for (Projectile projectile : SpaceWarsWorld.getInstance().getProjectiles()) {
            if (projectile instanceof ShipProjectile) {
                g.drawPixmap(Assets.shipProjectile, projectile.getX()*CELL_WIDTH_PIXEL, projectile.getY()*CELL_HEIGHT_PIXEL);
            } else {
                g.drawPixmap(Assets.alienProjectile, projectile.getX()*CELL_WIDTH_PIXEL, projectile.getY()*CELL_HEIGHT_PIXEL);
            }
        }

        for (int i=1; i<ship.getLives();i++) {
            g.drawPixmap(Assets.shipLife, 13*i, 400);
        }*/
    }
}
