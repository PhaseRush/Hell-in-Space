package com.mygdx.starfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Star {

    protected float x, y, v;
    protected int width, height;
    protected Color color;
    protected Texture starTexture;
    protected ThreadLocalRandom r = ThreadLocalRandom.current();

    public Star(){
        x = r.nextInt(Gdx.graphics.getWidth());
        v = r.nextFloat()*100; //use Math.max here for "jumpy" startrails
        //y = Gdx.graphics.getHeight();
        y = r.nextInt(Gdx.graphics.getHeight());

        int length = r.nextInt(3) + (int)v/50; //slightly favor closer = bigger
        width = length;
        height = length;

        color = Color.WHITE;
        starTexture = generateTexture();
    }

    void update(float delta){
        move(delta);
        twinkle();
    }

    /**
     * change the color or something
     */
    protected void twinkle() {
    }

    void move(float delta){
        y -= v * delta;
        if (y < 0) {
            y = Gdx.graphics.getHeight(); //could also randomize x
            x = r.nextInt(Gdx.graphics.getWidth());
        }
    }

    protected Texture getStarAsTexture(){
        return starTexture;
    }

    private Texture generateTexture(){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888); //may change format
        pixmap.setColor(color);
        pixmap.fillRectangle(0,0, width, height);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }


    //getters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
