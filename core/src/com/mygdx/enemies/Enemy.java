package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.StandardBullet;
import com.mygdx.screens.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Enemy {

    private GameMain game;
    protected float x, y, vx, vy;
    protected boolean isAlive;
    protected int width, height;

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();

    private float projectileDamage;

    protected Texture enemyTexture;
    protected Rectangle rectangularRepresentation;
    protected Color color;

    public Enemy(GameMain game) {
        this.game = game;
        enemyTexture = new Texture(Gdx.files.internal("StandardFighterMap.png"));

        y = Gdx.graphics.getHeight();
        x = ThreadLocalRandom.current().nextInt(gameWidth);
        vx = 0;
        vy = 3;
        width = enemyTexture.getWidth();
        height = enemyTexture.getHeight();
        rectangularRepresentation = new Rectangle(x, y, width, height);

        projectileDamage = 10;

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


    public void update(float delta){

        //move(delta);
        if(ThreadLocalRandom.current().nextInt(gameWidth) % 2 == 0) {
            shoot();
        }
        //render(delta);
    }

    public void shoot(){
        Bullet b = new StandardBullet(game, x, y, false, projectileDamage);
        GameScreen.updateManager.add(b);
    }

    public void move(float delta){

    }

    public boolean checkCollision(Bullet b) {
        return Intersector.overlaps(b.getBulletAsCircle(), rectangularRepresentation);
    }

    public boolean hasDied() {
        if (!isAlive)
            return true;
        return x < 0 || x > gameWidth || y < 0;
    }

//    public void generateNewEnemy() {
//        x = new Random().nextInt(3);
//        if (delayTimer == 0) {
//            while (xIndexEnemy == xIndexEnemyOld) {
//                xIndexEnemy = new Random().nextInt(3);
//            }
//        }
//
//        Enemy enemy = new Enemy(xCoordinates[xIndexEnemy]);
//        enemies.add((Enemy) enemy);
//        xIndexEnemyOld = xIndexEnemy;
//        // Now that you used the new index you can store it as the Old one
//    }
}
