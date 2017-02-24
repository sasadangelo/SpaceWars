/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Alien Invaders project.
 *  This file derives from the Mr Nom project developed by Mario Zechner for the Beginning Android
 *  Games book (chapter 6).
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

import android.util.Log;

import org.androidforfun.spacewars.model.SpaceWarsWorld;
import org.androidforfun.spacewars.model.Settings;
import org.androidforfun.framework.Gdx;
import org.androidforfun.framework.Input.TouchEvent;
import org.androidforfun.framework.Rectangle;
import org.androidforfun.framework.Screen;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/*
 * This class represents the game screen. The processing and rendering depends on the game state managed
 * by State pattern. The update and draw method are delegated to:
 *    GamePause.update, GamePause.draw
 *    GameReady.update, GameReady.draw
 *    GameRunning.update, GameRunning.draw
 *    GameOver.update, GameOver.draw
 *
 * depending on the status of the game.
 *
 * @author Salvatore D'Angelo
 */
class GameScreen implements Screen {
    private static final String LOG_TAG = "Ufo.GameScreen";

    private Map<SpaceWarsWorld.GameState, GameState> states = new EnumMap<>(SpaceWarsWorld.GameState.class);
    private int shipMovingLeftPointer=-1;
    private int shipMovingRightPointer=-1;
    private int shipMovingUpPointer=-1;

    private Rectangle gameoverScreenBounds;
    //private Rectangle gameScreenBounds;
    private Rectangle pauseButtonBounds;
    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;
    private Rectangle upButtonBounds;
    private Rectangle shootButtonBounds;
    private Rectangle xButtonBounds;
    private Rectangle pauseMenuBounds;
    private Rectangle readyMenuBounds;
    private Rectangle homeMenuBounds;

    private SpaceWarsWorldRenderer renderer;

    //private SpaceWarsWorld.WorldListener worldListener;

    GameScreen() {
        Log.i(LOG_TAG, "constructor -- begin");
        states.put(SpaceWarsWorld.GameState.Paused, new GamePaused());
        states.put(SpaceWarsWorld.GameState.Ready, new GameReady());
        states.put(SpaceWarsWorld.GameState.Running, new GameRunning());
        states.put(SpaceWarsWorld.GameState.GameOver, new GameOver());

        gameoverScreenBounds=new Rectangle(0, 0, 320, 480);
        //gameScreenBounds=new Rectangle(0, 0, 320, 480);
        pauseButtonBounds=new Rectangle(5, 20, 50, 50);
        leftButtonBounds=new Rectangle(20, 425, 50, 50);
        rightButtonBounds=new Rectangle(80, 425, 50, 50);
        upButtonBounds=new Rectangle(140, 425, 50, 50);
        //downButtonBounds=new Rectangle(200, 425, 50, 50);
        shootButtonBounds=new Rectangle(260, 425, 50, 50);
        pauseMenuBounds=new Rectangle(100, 100, 160, 48);
        readyMenuBounds=new Rectangle(65, 60, 188, 70);
        homeMenuBounds=new Rectangle(80, 148, 160, 48);
        xButtonBounds=new Rectangle(128, 200, 50, 50);

        renderer = new SpaceWarsWorldRenderer();
/*
        worldListener=new SpaceWarsWorld.WorldListener() {
            public void explosion() {
                if(Settings.soundEnabled)
                    Assets.explosion.play(1);
            }
            public void laserClash() {
                if(Settings.soundEnabled)
                    Assets.laserClash.play(1);
            }
            public void shieldImpact() {
                if(Settings.soundEnabled)
                    Assets.shieldImpact.play(1);
            }
        };
        SpaceWarsWorld.getInstance().setWorldListener(worldListener);*/
    }

    /*
     * The update method is delegated to:
     *    GamePause.update
     *    GameReady.update
     *    GameRunning.update
     *    GameOver.update
     *
     * depending on the status of the game.
     */
    public void update(float deltaTime) {
        //Log.i(LOG_TAG, "update -- begin");
        List<TouchEvent> touchEvents = Gdx.input.getTouchEvents();
        Gdx.input.getKeyEvents();
        states.get(SpaceWarsWorld.getInstance().getState()).update(touchEvents, deltaTime);
    }

    /*
     * The draw method is delegated to:
     *    GamePause.draw
     *    GameReady.draw
     *    GameRunning.draw
     *    GameOver.draw
     *
     * depending on the status of the game.
     */
    public void draw(float deltaTime) {
        //Log.i(LOG_TAG, "draw -- begin");

        // draw the background
        //Gdx.graphics.drawPixmap(Assets.gamescreen, gameScreenBounds.getX(), gameScreenBounds.getY());
        Gdx.graphics.clear(0);
        // draw buttons
        Gdx.graphics.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 150,
                leftButtonBounds.getWidth() + 1, leftButtonBounds.getHeight() + 1); // left button
        Gdx.graphics.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 50, 200,
                rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
        Gdx.graphics.drawPixmap(Assets.buttons, upButtonBounds.getX(), upButtonBounds.getY(), 0, 50,
                upButtonBounds.getWidth()+1, upButtonBounds.getHeight()+1); // right button
        Gdx.graphics.drawPixmap(Assets.buttons, shootButtonBounds.getX(), shootButtonBounds.getY(), 0, 200,
                shootButtonBounds.getWidth() + 1, shootButtonBounds.getHeight() + 1); // down button

        states.get(SpaceWarsWorld.getInstance().getState()).draw();
    }

    /*
     * Draw text on the screen in the (x, y) position.
     */
    private void drawText(String text, int x, int y) {
        Log.i(LOG_TAG, "drawText -- begin");
        int len = text.length();
        for (int i = 0; i < len; i++) {
            char character = text.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            Gdx.graphics.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    /*
     * The screen is paused.
     */
    public void pause() {
        Log.i(LOG_TAG, "pause -- begin");

        if(SpaceWarsWorld.getInstance().getState() == SpaceWarsWorld.GameState.Running)
            SpaceWarsWorld.getInstance().setState(SpaceWarsWorld.GameState.Paused);

        if(SpaceWarsWorld.getInstance().getState() == SpaceWarsWorld.GameState.GameOver) {
            Settings.addScore(SpaceWarsWorld.getInstance().getScore());
            Settings.save(Gdx.fileIO);
        }
    }

    /*
     * The abstract class representing a generic State. Used to implement the State pattern.
     */
    abstract class GameState {
        abstract void update(List<TouchEvent> touchEvents, float deltaTime);
        abstract void draw();
    }

    /*
     * This class represents the game screen in running state. It will be responsible to update and
     * draw when the game is running.
     *
     * @author Salvatore D'Angelo
     */
    private class GameRunning extends GameState {
        /*
         * Update the game when it is in running state. The method catch the user input and,
         * depending on it will update the world.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            //Log.i(LOG_TAG, "GameRunning.update -- begin");

            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                switch(event.type) {
                    case TouchEvent.TOUCH_UP:
                        // Check if ship stopped to rotate on left
                        if (shipMovingLeftPointer==event.pointer) {
                            SpaceWarsWorld.getInstance().getShip().setShipRotatingLeft(false);
                            shipMovingLeftPointer=-1;
                        }
                        // Check if ship stopped to rotate on right
                        if (shipMovingRightPointer==event.pointer) {
                            SpaceWarsWorld.getInstance().getShip().setShipRotatingRight(false);
                            shipMovingRightPointer=-1;
                        }
                        // Check if ship stopped to move
                        if (shipMovingUpPointer==event.pointer) {
                            SpaceWarsWorld.getInstance().getShip().setShipMoving(false);
                            shipMovingUpPointer=-1;
                        }
                        break;
                    case TouchEvent.TOUCH_DRAGGED:
                        if (shipMovingLeftPointer==event.pointer) {
                            if(!leftButtonBounds.contains(event.x, event.y)) {
                                SpaceWarsWorld.getInstance().getShip().setShipRotatingLeft(false);
                                shipMovingLeftPointer=-1;
                            }
                        }
                        // Finish move on right
                        if (shipMovingRightPointer==event.pointer) {
                            if(!rightButtonBounds.contains(event.x, event.y)) {
                                SpaceWarsWorld.getInstance().getShip().setShipRotatingRight(false);
                                shipMovingRightPointer=-1;
                            }
                        }
                        if (shipMovingUpPointer==event.pointer) {
                            if(!upButtonBounds.contains(event.x, event.y)) {
                                SpaceWarsWorld.getInstance().getShip().setShipMoving(false);
                                shipMovingUpPointer=-1;
                            }
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(pauseButtonBounds.contains(event.x, event.y)) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            SpaceWarsWorld.getInstance().setState(SpaceWarsWorld.GameState.Paused);
                            return;
                        }
                        // Move ship on the left
                        if(leftButtonBounds.contains(event.x, event.y)) {
                            SpaceWarsWorld.getInstance().getShip().setShipRotatingLeft(true);
                            shipMovingLeftPointer=event.pointer;
                        }
                        // Move ship on the right
                        if(rightButtonBounds.contains(event.x, event.y)) {
                            SpaceWarsWorld.getInstance().getShip().setShipRotatingRight(true);
                            shipMovingRightPointer=event.pointer;
                        }
                        // Move ship up
                        if(upButtonBounds.contains(event.x, event.y)) {
                            SpaceWarsWorld.getInstance().getShip().setShipMoving(true);
                            shipMovingUpPointer=event.pointer;
                        }
                        if(shootButtonBounds.contains(event.x, event.y)) {
                            SpaceWarsWorld.getInstance().getShip().shoot();
                            //if(Settings.soundEnabled)
                            //    Assets.laserCanon.play(1);
                        }
                        break;
                }
            }

            if (SpaceWarsWorld.getInstance().getShip().isShipRotatingLeft()) {
                SpaceWarsWorld.getInstance().getShip().rotateLeft();
            }
            if (SpaceWarsWorld.getInstance().getShip().isShipRotatingRight()) {
                SpaceWarsWorld.getInstance().getShip().rotateRight();
            }
            if (SpaceWarsWorld.getInstance().getShip().isShipMoving()) {
                SpaceWarsWorld.getInstance().getShip().move();
            }

            SpaceWarsWorld.getInstance().update(deltaTime);
            if (SpaceWarsWorld.getInstance().getState() == SpaceWarsWorld.GameState.GameOver) {
                if(Settings.soundEnabled)
                    Assets.bitten.play(1);
            }

            if (!SpaceWarsWorld.getInstance().getShip().isAlive()) {
                SpaceWarsWorld.getInstance().setState(SpaceWarsWorld.GameState.GameOver);
            }

            //if(Settings.soundEnabled)
            //    if (!Assets.musicInvaders.isPlaying()) {
            //        Assets.musicInvaders.setLooping(true);
            //        Assets.musicInvaders.play();
            //    }
        }

        /*
         * Draw the game in running state.
         */
        void draw() {
            //Log.i(LOG_TAG, "GameRunning.draw -- begin");
            // draw pause button
            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            //Gdx.graphics.drawRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
            // render the game world.
            renderer.draw();

            // draw scores
            /*
            TextStyle style = new TextStyle();
            style.setColor(0xffffffff);
            style.setTextSize(14);
            style.setStyle(TextStyle.Style.BOLD);
            style.setAlign(TextStyle.Align.CENTER);
            Gdx.graphics.drawText("Score:", 100, 40, style);
            Gdx.graphics.drawText("" + SpaceWarsWorld.getInstance().getScore(), 100, 60, style);
            Gdx.graphics.drawText("Highscore:", 200, 40, style);
            Gdx.graphics.drawText("" + Settings.highscores[0], 200, 60, style);
            Gdx.graphics.drawText("Level:", 300, 40, style);
            Gdx.graphics.drawText("" + SpaceWarsWorld.getInstance().getLevel(), 300, 60, style);*/
        }
    }

    /*
     * This class represents the game screen in pause state. It will be responsible to update and
     * draw when the game is paused.
     *
     * @author Salvatore D'Angelo
     */
    private class GamePaused extends GameState {
        /*
         * Update the game when it is in paused state. The method catch the user input and
         * depending on it will resume the game or return to the start screen.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GamePaused.update -- begin");

            // Check if user asked to resume the game or come back to the start screen.
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (pauseMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        SpaceWarsWorld.getInstance().setState(SpaceWarsWorld.GameState.Running);
                        return;
                    }
                    if(homeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        return;
                    }
                }
            }
            //if(Settings.soundEnabled)
            //    if (Assets.musicInvaders.isPlaying())
            //        Assets.musicInvaders.pause();
        }

        /*
         * Draw the game in paused state.
         */
        void draw() {
            Log.i(LOG_TAG, "GamePaused.draw -- begin");
            // draw the pause menu
            Gdx.graphics.drawPixmap(Assets.pausemenu, pauseMenuBounds.getX(), pauseMenuBounds.getY());
        }
    }

    /*
     * This class represents the game screen in ready state. It will be responsible to update and
     * draw when the game is ready.
     *
     * @author Salvatore D'Angelo
     */
    private class GameReady extends GameState {
        /*
         * Update the game when it is in ready state. The method catch the user input and
         * resume the game.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameReady.update -- begin");
            if(touchEvents.size() > 0)
                SpaceWarsWorld.getInstance().setState(SpaceWarsWorld.GameState.Running);
        }

        /*
         * Draw the game in ready state.
         */
        void draw() {
            Log.i(LOG_TAG, "GameReady.draw -- begin");
            // draw ready menu
            Gdx.graphics.drawPixmap(Assets.readymenu, readyMenuBounds.getX(), readyMenuBounds.getY());
        }
    }

    /*
     * This class represents the game screen when it is over. It will be responsible to update and
     * draw when the game is over.
     *
     * @author Salvatore D'Angelo
     */
    private class GameOver extends GameState {
        /*
         * Update the game when it is over. The method catch the user input and return to the
         * start screen.
         */
        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameOver.update -- begin");

            // check if the x button is pressed.
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if (xButtonBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        SpaceWarsWorld.getInstance().clear();
                        return;
                    }
                }
            }
            //if(Settings.soundEnabled)
            //    if (Assets.musicInvaders.isPlaying())
            //        Assets.musicInvaders.stop();
        }

        /*
         * Draw the game when it is over.
         */
        void draw() {
            Log.i(LOG_TAG, "GameOver.draw -- begin");
/*
            // draw scores
            TextStyle style = new TextStyle();
            style.setColor(0xffffffff);
            style.setTextSize(14);
            style.setStyle(TextStyle.Style.BOLD);
            style.setAlign(TextStyle.Align.CENTER);
            Gdx.graphics.drawText("Score:", 100, 40, style);
            Gdx.graphics.drawText("" + SpaceWarsWorld.getInstance().getScore(), 100, 60, style);
            Gdx.graphics.drawText("Highscore:", 200, 40, style);
            Gdx.graphics.drawText("" + Settings.highscores[0], 200, 60, style);
            Gdx.graphics.drawText("Level:", 300, 40, style);
            Gdx.graphics.drawText("" + SpaceWarsWorld.getInstance().getLevel(), 300, 60, style);
*/
            // draw pause button
            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button

            // draw back transparent background
            Gdx.graphics.drawPixmap(Assets.gameoverscreen, gameoverScreenBounds.getX(), gameoverScreenBounds.getY());
            // draw X button
            Gdx.graphics.drawPixmap(Assets.buttons, xButtonBounds.getX(), xButtonBounds.getY(), 0, 100,
                    xButtonBounds.getWidth()+1, xButtonBounds.getHeight()+1);
            // draw final score
            drawText(""+ SpaceWarsWorld.getInstance().getScore(), 180, 280);
        }
    }

    /*
     * The screen is resumed.
     */
    public void resume() {
    }

    /*
     * The screen is disposed.
     */
    public void dispose() {
    }
}