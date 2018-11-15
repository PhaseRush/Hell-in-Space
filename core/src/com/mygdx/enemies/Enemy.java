package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.StandardEnemyBullet;
import com.mygdx.screens.GameScreen;
import com.mygdx.util.Updatable;

import java.util.Random;

public abstract class Enemy implements Disposable, Updatable {

    private GameMain game;

    // position and velocity
    protected Vector2 pos, v;

    // enemy information
    protected boolean isAlive;
    protected int width, height;

    protected int shotFrequency;
    protected float projectileDamage;

    protected Texture enemyTexture;
    protected Color color;

    // game information
    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();
    protected int padding;

    protected int clock;

    public Enemy(GameMain game) {

        this.game = game;

        // Get Texture
        enemyTexture = new Texture(Gdx.files.internal("StandardFighterMap2.png"));
        width = enemyTexture.getWidth();
        height = enemyTexture.getHeight();

        // Set Initial Position, pad the screen's edges
        padding = width > height ? width : height;
        Random r = new Random();
        pos = new Vector2();
        pos.x = r.nextInt(gameWidth - padding);
        pos.y = r.nextInt((gameHeight - gameHeight/2) + 1) + gameHeight/2;

        // Set Velocity
        v = new Vector2();
        v.x = 0;
        v.y = -1;

        // Set Enemy Information
        projectileDamage = 10;
        shotFrequency = 40;
        isAlive = true;

        color = Color.GOLD;

        clock = 0;
    }

    public void update(float delta){

        render(delta);
        move(delta);

        if(clock % shotFrequency == 0) {
            shoot();
        }

        clock++;

    }

    public void shoot(){
        Bullet b = new StandardEnemyBullet(game, pos.x + width/2, pos.y, false, projectileDamage);
        GameScreen.updateManager.add(b);
    }

    public void move(float delta){
        pos.y += v.y;
        //pos.mulAdd(v, delta);
    }

    public boolean checkCollision(Bullet b) {
        return Intersector.overlaps(b.getBulletAsCircle(), new Rectangle(pos.x, pos.y, width, height));
    }

    public void render(float delta) {
        game.batch.draw(enemyTexture, pos.x, pos.y);
    }

    public boolean hasDied() {
        if (!isAlive)
            return true;
        return pos.x < -width || pos.x > gameWidth + width || pos.y < -height;
    }

    public Texture getEnemyTexture() {
        return enemyTexture;
    }

    public Rectangle getEnemyAsRectangle() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    @Override
    public void dispose() {
        enemyTexture.dispose();
    }

    public Vector2 getPos() {
        return pos;
    }
}
