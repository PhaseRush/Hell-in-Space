package com.mygdx.starfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.StandardBullet;
import com.mygdx.screens.GameScreen;
import com.mygdx.util.Updatable;

public abstract class Starfighter implements Disposable, Updatable {
    private GameMain game;
    //movement
    private float maxXSpeed, maxYSpeed, maxXAccel, maxYAccel, maxRotAccel, maxRotSpeed, angularFriction, linearFriction;

    //rotation state
    int rotationState; //0 up, 1 right, 2 down, 3 left
    private float bearing;

    //other
    private float projectileDamage;

    private int health, energy; //might add shield mechanic

    Texture fighter, upStarfighter, rightStarfighter, leftStarfighter, downStarfighter;

    private Vector2 pos = new Vector2();
    private Vector2 v = new Vector2();
    private Vector2 a = new Vector2();


    int width, height, clock;

    Starfighter pet; //could have a small pet following or something

    //meta
    int gameWidth = Gdx.graphics.getWidth();
    int gameHeight = Gdx.graphics.getHeight();

    public Starfighter(GameMain game){
        this.game = game;

        //sprite stuff
        upStarfighter = new Texture(Gdx.files.internal("Starfighter\\UpStarfighter.png"));
        rightStarfighter = new Texture(Gdx.files.internal("Starfighter\\RightStarfighter.png"));
        leftStarfighter = new Texture(Gdx.files.internal("Starfighter\\LeftStarfighter.png"));
        downStarfighter = new Texture(Gdx.files.internal("Starfighter\\DownStarfighter.png"));
        fighter = upStarfighter; //init the regular fighter so not null on first render

        width = upStarfighter.getWidth();
        height = upStarfighter.getHeight();

        //position setting etc.
        pos.x = gameWidth/2 - width/2;
        pos.y = gameHeight/2 - height/2;
        v.x = 0;
        v.y = 0;
        clock = 0;
        bearing = 0;

        //stats
        health = 100;
        energy = 60;
        projectileDamage = 10;

        //movement
        maxXSpeed = 15;
        maxYSpeed = 15;
        maxXAccel = 5;
        maxYAccel = 5;
        maxRotSpeed = 10;
        maxRotAccel = 10;
        linearFriction = .5f;
        angularFriction = 1;
    }

    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) shoot();

        move(delta);

        clock++;
    }

    //todo might want to mess around with pos.x and pos.y -- Currently not centered or smth?
    private void shoot() { //just add to the updateManager
        Bullet b = new StandardBullet(game, pos.x, pos.y, true, projectileDamage);
        GameScreen.updateManager.add(b);
    }

    //based on this http://steigert.blogspot.com/2012/05/11-libgdx-tutorial-vectors.html
    private void move(float delta){
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            rotateShip(-1);
            bearing += maxRotAccel * delta;
            //System.out.println("Q: bearing: " + bearing);
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            rotateShip(1);
            bearing -= maxRotAccel * delta;
            //System.out.println("E: bearing: " + bearing);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.A))
            a.x = -maxXAccel * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            a.x = maxXAccel * delta;
        else a.x = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            a.y = maxYAccel * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            a.y = -maxYAccel * delta;
        else a.y = 0;

        //friction aka "retrothrusters"
        float vMag = v.len();
        if (vMag > 0) {
            v.x -= (v.x/vMag) * linearFriction * delta;
            v.y -= (v.y/vMag) * linearFriction * delta;
        }

        v.add(a);

        //check if velocity is within bound
        //todo, maybe flash screen red if hit max v
        if (v.x < 0)
            v.x = Math.max(v.x, -maxXSpeed);
        else if (v.x > 0)
            v.x = Math.min(v.x, maxXSpeed);
        if (v.y < 0)
            v.y = Math.max(v.y, -maxYSpeed);
        else if (v.y > 0)
            v.y = Math.min(v.y, maxYSpeed);

        pos.add(v);

        //check if position is within bound
        if (pos.x < 0) {
            pos.x = 0;
            v.x = 0; //might want to get rid of this
        } else if (pos.x  > gameWidth - upStarfighter.getWidth()) {
            pos.x = gameWidth - upStarfighter.getWidth();
            v.x = 0;
        }
        if (pos.y < 0) {
            pos.y = 0;
            v.y = 0;
        } else if (pos.y > gameHeight - upStarfighter.getHeight()) {
            pos.y = gameHeight - upStarfighter.getHeight();
            v.y = 0;
        }

    }

    /**
     * rotates ship
     * @param i +1 = rotate clockwise, -1 = counter cw
     */
    private void rotateShip(int i) {
        if (i == 1) {
            if (rotationState == 3) rotationState = 0;
            else rotationState++;
        } else {
            if (rotationState == 0) rotationState = 3;
            else rotationState--;
        }

        //update the sprite
        switch (rotationState) {
            case 0:
                fighter = upStarfighter;
                break;
            case 1:
                fighter = rightStarfighter;
                break;
            case 2:
                fighter = downStarfighter;
                break;
            case 3:
                fighter = leftStarfighter;
        }
    }

    public boolean checkCollision(Bullet b) {
        return Intersector.overlaps(b.getBulletAsCircle(), new Rectangle(pos.x, pos.y, width, height));
        //return rectangularRepresentation.overlaps(b.getBulletAsCircle()); //bullet not circle anymore.
    }

    public void decreaseHealth(float f) {
        health -= f;
    }

    public Texture getFighterTexture() {
        return fighter;
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public void dispose(){
        //stuff;
    }
}
