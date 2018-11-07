package com.mygdx.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.util.Updatable;

public abstract class Bullet implements Disposable, Updatable {
    private GameMain game;
    protected float bearing;
    protected Vector2 pos, v;

    protected boolean isAlive, isGood;
    protected int width; //got rid of height since it is circle

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();

    protected Texture bulletTexture;
    protected Color color;

    protected Circle circularRepresentation;

    public Bullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing){
        this.game = game;
        width = 10;

        pos = new Vector2();
        pos.x = x;
        pos.y = y;

        v = new Vector2();
        v.x = vx;
        v.y = vy;

        this.bearing = bearing;
        this.isGood = isGood;
        isAlive = true;


        if(isGood) {
            color = Color.GREEN;
        }else {
            color = Color.RED;
        }

        generateTexture();
        circularRepresentation = new Circle(pos.x, pos.y, width);
    }

    public void update(float delta){
        move(delta);
        render(delta);
    }

    protected void render(float delta) {
        game.batch.draw(bulletTexture, pos.x, pos.y);
    }

    private void move(float delta) {
        pos.mulAdd(v, delta);
    }

    private void generateTexture(){
        Pixmap pixmap = new Pixmap(width, width, Pixmap.Format.RGBA8888); //may change format
        pixmap.setColor(color); //green if good, red if not good
        pixmap.fillCircle(0,0, width);
        bulletTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    void updateIsAlive() {
        if(pos.x > gameWidth || pos.x < 0 || pos.y > gameHeight || pos.y < 0)
            isAlive = false;
    }

    public Texture getBulletTexture(){
        return bulletTexture;
    }

    public Circle getBulletAsCircle() {
        return circularRepresentation;
    }

    @Override
    public void dispose() {
        bulletTexture.dispose();
    }
}
