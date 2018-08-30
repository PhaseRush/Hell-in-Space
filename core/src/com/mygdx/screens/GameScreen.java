package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.starfield.Starfield;

public class GameScreen implements Screen {
    final GameMain game;

    int frameCount = 0;
    Texture backgroundFadeRect;
    Starfield starfield;

    OrthographicCamera camera;

    int gameWidth = Gdx.graphics.getWidth();
    int gameHeight = Gdx.graphics.getHeight();

    public GameScreen(final GameMain game) {
        this.game = game;
        starfield = new Starfield(game.batch);
        initBackgroundFadeRect();
        //load textures/sprites/sounds here

        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWidth, gameHeight); //might be problem
    }



    private void initBackgroundFadeRect(){
        int gameWidth = Gdx.graphics.getWidth();
        int gameHeight = Gdx.graphics.getHeight();

        Pixmap pixmap = new Pixmap(gameWidth, gameHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0, 0.25f); //change alpha to determine fade amount
        pixmap.fillRectangle(0,0, gameWidth, gameHeight);
        backgroundFadeRect = new Texture(pixmap);
        pixmap.dispose();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();

        starfield.show();
        game.batch.draw(backgroundFadeRect,0,0);


        game.batch.end();

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
		backgroundFadeRect.dispose();
	}
}
