package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.StandardBullet;
import com.mygdx.projectiles.StandardEnemyBullet;
import com.mygdx.screens.GameScreen;
import com.mygdx.util.Updatable;

import java.util.Random;

public abstract class Enemy implements Disposable, Updatable {

    private GameMain game;
    protected Vector2 pos, v;

    protected boolean isAlive;
    protected int width, height;
    protected int clock;
    protected int shotFrequency;

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();
    protected int padding;

    private float projectileDamage;

    protected Texture enemyTexture;
    protected Color color;

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

        // Set Enemy Statistics
        projectileDamage = 10;
        shotFrequency = 40;
        isAlive = true;

        color = Color.GOLD;

        //generateTexture();

        clock = 0;
    }

//    private void generateTexture(){
//        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888); //may change format
//        pixmap.setColor(color); //green if good, red if not good
//        pixmap.fillRectangle(0,0, width, height);
//        enemyTexture = new Texture(pixmap);
//        //TextureData textureData = enemyTexture.getTextureData();
//        //textureData.prepare();
//
//        //Pixmap pixmap = enemyTexture.getTextureData().consumePixmap();
//        //enemyTexture = new Texture(pixmap);
//        pixmap.dispose();
//    }


    public void update(float delta){

        render(delta);
        move(delta);
        if(clock % shotFrequency == 0) {
            shoot();
        }

//        if (clock % enemyFrequency == 0) {
//            Enemy enemy = new StandardEnemy(game);
//            GameScreen.updateManager.add(enemy);
//        }

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
        //return intersects(b.getBulletAsCircle(), rectangularRepresentation);
        //return rectangularRepresentation.overlaps(b.getBulletAsCircle());
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

//    boolean intersects(Circle circle, Rectangle rect)
//    {
//        int cx = abs((int)circle.x - (int)rect.x);
//        int cy = abs((int)circle.y - (int)rect.y);
//
//        if (cx > (rect.width/2 + circle.radius)) { return false; }
//        if (cy > (rect.height/2 + circle.radius)) { return false; }
//
//        if (cx <= (rect.width/2)) { return true; }
//        if (cy <= (rect.height/2)) { return true; }
//
//        int cornerDistance_sq = (cx - (int)rect.width/2)^2 + (cy - (int)rect.height/2)^2;
//
//        return (cornerDistance_sq <= ((int)circle.radius^2));
//    }
}
