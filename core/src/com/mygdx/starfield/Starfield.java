package com.mygdx.starfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Starfield {
    List<Star> stars = new ArrayList<>();
    private int numStars = 500;
    private int downwardsAffinity = 5; // n/(n+1) stars are going downwards
    SpriteBatch batch;


    Texture backgroundFadeRect;

    public Starfield(SpriteBatch batch){
        initBackgroundFadeRect();
        this.batch = batch;

        int numStandardStars = ThreadLocalRandom.current().nextInt(numStars*downwardsAffinity/(downwardsAffinity+1), numStars);

        for (int i = 0; i < numStandardStars; i++) //favors more small stars
            stars.add(new StandardStar());

        for (int i = numStandardStars; i < numStars; i++)
            stars.add(new UpwardStar());
    }

    public void show(float delta){
        for (Star s : stars) {
            s.update(delta);
            batch.draw(s.getStarAsTexture(), s.getX(), s.getY(), s.getWidth(), s.getHeight());

        }
        batch.draw(backgroundFadeRect,0,0);
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

}
