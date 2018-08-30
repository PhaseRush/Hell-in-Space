package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Enemy {

    protected float x, y, vx, vy;
    protected boolean isAlive;
    protected int width, height;

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();

    public Enemy() {
        y = Gdx.graphics.getHeight();
        x = ThreadLocalRandom.current().nextInt(gameWidth);
        vx = 0;
        vy = 3;
        width = 10;
        height = 10;

        isAlive = true;
    }

    public void update(){

    }

    public void fire(){

    }

    public boolean hasDied() {
        if (!isAlive)
            return true;
        return x < 0 || x > gameWidth || y < 0;
    }
}
