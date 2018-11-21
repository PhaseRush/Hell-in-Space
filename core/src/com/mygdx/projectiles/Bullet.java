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
    protected float bearing, damageValue;
    protected Vector2 pos, initalV, maxV, actualV;

    protected boolean isAlive, isGood;
    protected int width; //got rid of height since it is circle

    protected int gameWidth = Gdx.graphics.getWidth();
    protected int gameHeight = Gdx.graphics.getHeight();

    protected Texture bulletTexture;
    protected Circle circleRepresentation;
    protected Color color;

    public Bullet(GameMain game, float x, float y, float vx, float vy, float maxVx, float maxVy, boolean isGood, float bearing, float damageValue){
        this.game = game;
        width = 10;

        pos = new Vector2();
        pos.x = x;
        pos.y = y;

        initalV = new Vector2();
        initalV.x = vx;
        initalV.y = vy;

        this.maxV = new Vector2(maxVx, maxVy); //max velocity
        actualV = new Vector2(initalV.x + maxV.x, initalV.y + maxV.y);

        this.bearing = bearing;
        this.damageValue = damageValue;
        this.isGood = isGood;
        isAlive = true;


        if(isGood) {
            color = Color.GREEN;
        }else {
            color = Color.RED;
        }

        generateTexture();
        circleRepresentation = new Circle(pos.x, pos.y, width);
        // I removed circular representation because the circle's x,y values changed over time, so
        // the representation is created when it's needed.
    }



    public Bullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing, float damageValue, Color color){
        this.game = game;
        width = 10;

        pos = new Vector2();
        pos.x = x;
        pos.y = y;

        initalV = new Vector2();
        initalV.x = vx;
        initalV.y = vy;

        this.bearing = bearing;
        this.damageValue = damageValue;
        this.isGood = isGood;
        isAlive = true;


        if(isGood) {
            color = Color.GREEN;
        }else {
            color = Color.RED;
        }

        generateTexture();
        circleRepresentation = new Circle(pos.x, pos.y, width);

        //set color
        this.color = color;
    }


        public void update(float delta){
        move(delta);
        render(delta);
    }

    public void render(float delta) {
        game.batch.draw(bulletTexture, pos.x, pos.y);
    }

    public void move(float delta) {
        //initalV.mulAdd(maxV, delta); //added
        //initalV.y += maxV.y;
        pos.mulAdd(actualV, delta);
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

    //changed to not create a new object every time.
    public Circle getBulletAsCircle() {
        circleRepresentation.setPosition(pos.x, pos.y);
        return circleRepresentation;
    }

    public boolean isGood() {
        return isGood;
    }

    public float getDamageValue() {
        return damageValue;
    }

    @Override
    public void dispose() {
        bulletTexture.dispose();
    }
}
