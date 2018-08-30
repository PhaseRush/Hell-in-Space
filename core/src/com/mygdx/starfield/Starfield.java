package com.mygdx.starfield;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Starfield {
    List<Star> stars = new ArrayList<>();
    private int numStars = 500;
    private int downwardsAffinity = 5; // n/(n+1) stars are going downwards
    SpriteBatch batch;

    public Starfield(SpriteBatch batch){
        this.batch = batch;

        int numStandardStars = ThreadLocalRandom.current().nextInt(numStars*downwardsAffinity/(downwardsAffinity+1), numStars);

        for (int i = 0; i < numStandardStars; i++) //favors more small stars
            stars.add(new StandardStar());

        for (int i = numStandardStars; i < numStars; i++)
            stars.add(new UpwardStar());


    }

    public void show(){
        for (Star s : stars) {
            s.update();
            batch.draw(s.getStarAsTexture(), s.getX(), s.getY(), s.getWidth(), s.getHeight());

        }
    }

}
