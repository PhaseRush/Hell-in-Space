package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.managers.Manager;
import com.mygdx.starfield.Starfield;
import com.mygdx.starfighter.PlayerHUD;
import com.mygdx.starfighter.StandardFighter;
import com.mygdx.starfighter.Starfighter;

public class GameScreen implements Screen {
    final GameMain game;

    public static int frameCount = 0;
    Starfield starfield;

    OrthographicCamera camera;

    int gameWidth = Gdx.graphics.getWidth();
    int gameHeight = Gdx.graphics.getHeight();

    public static Starfighter fighter;
    PlayerHUD hud;

    public static Manager updateManager;

    public GameScreen(final GameMain game) {
        this.game = game;
        starfield = new Starfield(game.batch);
        //load textures/sprites/sounds here

        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWidth, gameHeight); //might be problem


        //stuff that probably shouldn't be here but is anyways
        fighter = new StandardFighter(game);
        hud = new PlayerHUD(game, fighter);

        //init update manager

        updateManager = new Manager(game, fighter, hud);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clears screen every time
//        Gdx.gl.glClearColor(0,0,0,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin(); //start

        starfield.show(delta);
        fighter.update(delta);
        game.batch.draw(fighter.getFighterTexture(), fighter.getPos().x, fighter.getPos().y);
        hud.show(delta);

        //updatable manager
        updateManager.update(delta);


        game.batch.end(); //end

        //other stuff to do each frame
        frameCount++;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
	public void dispose () {
		game.batch.dispose();
	}
}
