package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.hellinspace.GameMain;

public class MainMenuScreen implements Screen {
    final GameMain game;
    private int gameWidth, gameHeight;
    private Viewport viewport;
    private Sprite introScreenSprite;
    OrthographicCamera camera;

    public MainMenuScreen(final GameMain game) {
        this.game = game;
        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWidth, gameHeight);

        Texture introScreen = new Texture("core/assets/blackpurple.png");
        introScreenSprite = createScaledSprite(introScreen);
    }

    private Sprite createScaledSprite(Texture texture) {
        float horizontalScaleRatio = texture.getWidth()/Gdx.graphics.getWidth();
        float verticalScaleRatio = texture.getHeight()/Gdx.graphics.getHeight();
        Sprite textureSprite = new Sprite(texture);
        textureSprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureSprite.setSize(textureSprite.getWidth()/horizontalScaleRatio,
                textureSprite.getHeight()/verticalScaleRatio);
        return textureSprite;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(introScreenSprite, 0, 0);
        game.font.draw(game.batch, "Hell in Space ",gameWidth/10, gameHeight/7);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
