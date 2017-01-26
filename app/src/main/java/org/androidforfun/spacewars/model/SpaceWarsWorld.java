package org.androidforfun.spacewars.model;

import org.androidforfun.framework.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SpaceWarsWorld {
    public interface WorldListener {
        void explosion();
        void laserClash();
        void shieldImpact();
    }

    // Each cell is a 4x4 matrix of boxes. The reason we need cells is that ship, aliens and
    // projectiles do move across boxes.
    //public static final int CELL_WIDTH = 4;
    //public static final int CELL_HEIGHT = 4;
    // The dimension of the matrix is 16x20 cells
    //public static final int MATRIX_WIDTH = 16;
    //public static final int MATRIC_HEIGHT = 20;
    // Ufo Invaders world is a matrix of 64x80 boxes
    //public static final int WORLD_WIDTH = MATRIX_WIDTH*CELL_WIDTH;
    public static final int WORLD_WIDTH = 320;
    public static final int WORLD_HEIGHT = 400;
    //public static final int WORLD_HEIGHT = MATRIC_HEIGHT*CELL_HEIGHT;
    // Earth level: the level where ship is
    //public static final int EARTH_LEVEL = MATRIC_HEIGHT-1;

    // the possible game status values
    public enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    // the game status
    private GameState state = GameState.Ready;

    // the space full of stars
    Space space;
    // the game level
    private int level;
    // the user score
    private int score;
    // the list of aliens
    private List<Ufo> ufos;
    // the list of asteroids
    private List<Asteroid> asteroids;
    // the list of projectiles currently on the screen
    private List<Projectile> projectiles;
    // the ship
    private Ship ship;
    // the private static instance used to implement the Singleton pattern.
    private static SpaceWarsWorld instance = null;

    private WorldListener worldListener;

    public void setWorldListener(WorldListener worldListener) {
        this.worldListener = worldListener;
    }

    private int timer;

    // the aliens army
    //private AlienArmy alienArmy;
    // the list of maximum 4 shields
    //private List<Shield> shields;

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
    //public List<Shield> getShields() { return shields; }
    public List<Ufo> getUfos() { return ufos; }

    public Ship getShip() {
        return ship;
    }

    public int getLevel() {
        return level;
    }

    private SpaceWarsWorld() {
        this.space=new Space(WORLD_WIDTH, WORLD_HEIGHT);
        this.ufos=new ArrayList<>();
        //this.alienArmy=new AlienArmy(aliens);
        this.projectiles=new ArrayList<>();
        //this.shields=new ArrayList<>();
        clear();
    }

    public static SpaceWarsWorld getInstance() {
        if (instance == null) {
            synchronized (SpaceWarsWorld.class) {
                if (instance == null) {
                    instance = new SpaceWarsWorld();
                }
            }
        }
        return instance;
    }

    public void update(float deltaTime) {
        if (state == GameState.GameOver)
            return;

        if (state == GameState.Running ) {
            timer += 1;

            //if (ship.isExploding()) {
            //    ship.exploding(deltaTime);
            //    return;
            //}

            //if ((timer % 40) == 0) {
            //    alienArmy.move();
            //}

            //if ((timer % 80) == 0) {
            //    if (ufos.size()!=0) {
            //        invasion();
            //    }
            //}

            for (Projectile projectile: projectiles) {
                // slow down a bit alien projectiles, we update them only once each two update
                //if ((projectile instanceof UfoProjectile) && ((timer % 8) != 0)) {
                //    continue;
                //}
                projectile.move();
                //if (!getBounds().overlaps(projectile.getBounds())) {
                //    projectile.kill();
                //}
            }
/*
            if ((timer % 2) == 0) {
                for (Projectile projectile: projectiles) {
                    projectile.move();
                    if (!getBounds().overlaps(projectile.getBounds())) {
                        projectile.kill();
                    }
                }
            }

            for (Iterator<Projectile> itr= projectiles.iterator(); itr.hasNext();) {
                Projectile projectile = itr.next();
                if (projectile.isInactive()) {
                    itr.remove();
                }
            }

            detectCollisions();
*/
            if (!ship.isAlive()) {
                state=GameState.GameOver;
            }

            // If Ufo Army arrived on earth, destroy the ship and the game is over.
            //if (alienArmy.isOnEarth()) {
            //    ship.destroy();
            //    worldListener.explosion();
            //}
/*
            if (ufos.size()==0) {
                resetLevel();
                ++level;
            }*/
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public void invasion() {/*
        Random rnd = new Random(System.currentTimeMillis());
        int i=rnd.nextInt(ufos.size());
        Projectile p = ufos.get(i).shoot();
        if (p != null) projectiles.add(p);*/
    }

    public void clear() {
        score = 0;
        timer = 0;
        level=1;
        state = GameState.Ready;
        ship=new Ship();
        resetLevel();
    }

    public void resetLevel() {
        ufos.clear();
        projectiles.clear();
        //shields.clear();
        //for (int i=0; i<10; ++i) {
        //    aliens.add(new UglyAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 7*CELL_HEIGHT));
        //   aliens.add(new BadAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 8*CELL_HEIGHT));
        //   aliens.add(new BadAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 9*CELL_HEIGHT));
        //  aliens.add(new GoodAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 10*CELL_HEIGHT));
        //  aliens.add(new GoodAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 11*CELL_HEIGHT));
        //}//
        //alienArmy.update();
        //alienArmy.reset();

        //shields.add(new Shield(3*CELL_WIDTH, 17*CELL_HEIGHT));
        //shields.add(new Shield(6*CELL_WIDTH, 17*CELL_HEIGHT));
        //shields.add(new Shield(9 * CELL_WIDTH, 17 * CELL_HEIGHT));
        //shields.add(new Shield(12 * CELL_WIDTH, 17 * CELL_HEIGHT));
    }

    private void detectCollisions() {
        // Check if aliens hit the ship
        for (Iterator<Ufo> itr = ufos.iterator(); itr.hasNext();) {
            Ufo alien = itr.next();
            if (ship.collide(alien)) {
                ship.kill();
                itr.remove();
                worldListener.explosion();
                break;
            }
        }

        // Check if ship projectile hit an alien
        for (Iterator<Projectile> itrProjectile=projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof ShipProjectile) {
                for (Iterator<Ufo> itrAlien = ufos.iterator(); itrAlien.hasNext();) {
                    Ufo ufo = itrAlien.next();
                    if (ufo.collide(projectile)) {
                        score+=ufo.getScore();
                        itrAlien.remove();
                        itrProjectile.remove();
                        worldListener.laserClash();
                        //alienArmy.increaseSpeed();
                        break;
                    }
                }
            }
        }

        // Check if alien projectile hit the ship
        //for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
        //    Projectile projectile = itrProjectile.next();
        //    if (projectile instanceof UfoProjectile) {
        //        if (ship.collide(projectile)) {
        //            ship.kill();
        //            itrProjectile.remove();
        ////            worldListener.explosion();
        //            break;
        //        }
        //    }
        //}

        // Check if alien projectile hit a ship projectile
        //Projectile alienProjectile=null;
        //Projectile shipProjectile=null;
        //search: {
        //    for (Projectile projectile1 : projectiles) {
        //        if (projectile1 instanceof UfoProjectile) {
        //            for (Projectile projectile2 : projectiles) {
        //                if (projectile2 instanceof ShipProjectile) {
        //                    if (projectile1.collide(projectile2)) {
        //                        score+=10;
        //                        alienProjectile=projectile1;
        //                        shipProjectile=projectile2;
        //                        break search;
        //                    }
        //                }
        //            }
        //        }
        //    }
        // }
        //if (alienProjectile != null) projectiles.remove(alienProjectile);
        //if (shipProjectile != null) projectiles.remove(shipProjectile);

        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof ShipProjectile) {
            }
        }
    }

    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public Space getSpace() {
        return space;
    }
}
