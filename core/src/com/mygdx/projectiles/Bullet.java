package com.mygdx.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Bullet {
    protected float x, y, vx, vy;
    protected boolean isAlive, isGood;
    protected int width, height;

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();

    protected Texture bulletTexture;
    protected Color color;

    public Bullet(float x, float y, boolean isGood){
        this.x = x;
        this.y = y;
        this.isGood = isGood;
        isAlive = true;

        if(isGood) {
            color = Color.GREEN;
        }else {
            color = Color.RED;
        }

        generateTexture();
    }

    abstract void move();

    public Texture getBulletTexture(){
        return bulletTexture;
    }

    public Rectangle getBulletAsRectangle() {
        return new Rectangle(x, y, width, height);
    }

    private void generateTexture(){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888); //may change format
        pixmap.setColor(color); //green if good, red if not good
        pixmap.fillCircle(0,0, width);
        bulletTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    void updateIsAlive() {
        if(x > gameWidth || x < 0 || y > gameHeight || y < 0)
            isAlive = false;
    }

}
