package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.projectiles.Bullet;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Enemy {

    protected float x, y, vx, vy;
    protected boolean isAlive;
    protected int width, height;

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();

    protected Texture enemyTexture;
    protected Rectangle rectangularRepresentation;
    protected Color color;

    public Enemy() {
        y = Gdx.graphics.getHeight();
        x = ThreadLocalRandom.current().nextInt(gameWidth);
        vx = 0;
        vy = 3;
        width = 10;
        height = 10;

        color = Color.GOLD;

        generateTexture();
        isAlive = true;
    }

    private void generateTexture(){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888); //may change format
        pixmap.setColor(color); //green if good, red if not good
        pixmap.fillRectangle(0,0, width, height);
        enemyTexture = new Texture(pixmap);
        pixmap.dispose();
    }


    public void update(){
        move();
        fire();
    }

    public void fire(){

    }
    public void move(){

    }

    public boolean checkCollision(Bullet b) {
        return Intersector.overlaps(b.getBulletAsCircle(), rectangularRepresentation);
    }

    public boolean hasDied() {
        if (!isAlive)
            return true;
        return x < 0 || x > gameWidth || y < 0;
    }
}
