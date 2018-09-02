package com.mygdx.starfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.projectiles.Bullet;

public abstract class Starfighter {
    private int MAX_X_SPEED, MAX_Y_SPEED, MAX_X_ACCEL, MAX_Y_ACCEL;

    Texture fighterSprite;
    Rectangle rectangularRepresentation;

    Vector2 pos = new Vector2();
    Vector2 v = new Vector2();
    Vector2 a = new Vector2();

    int width, height, clock;

    Starfighter pet; //could have a small pet following or something

    //meta
    int gameWidth = Gdx.graphics.getWidth();
    int gameHeight = Gdx.graphics.getHeight();

    public Starfighter(){
        fighterSprite = new Texture(Gdx.files.internal("StandardFighter.png"));
        width = fighterSprite.getWidth();
        height = fighterSprite.getHeight();
        rectangularRepresentation = new Rectangle(pos.x, pos.y, width, height);

        pos.x = gameWidth/2 - width/2;
        pos.y = gameHeight/2 - height/2;
        v.x = 0;
        v.y = 0;
        clock = 0;

        MAX_X_SPEED = 15;
        MAX_Y_SPEED = 15;
        MAX_X_ACCEL = 5;
        MAX_Y_ACCEL = 5;


    }

    public void update(float delta){
        move(delta);
        clock++;
    }

    //based on this http://steigert.blogspot.com/2012/05/11-libgdx-tutorial-vectors.html
    private void move(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            a.x = -MAX_X_ACCEL * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            a.x = MAX_X_ACCEL * delta;
        else a.x = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.W))
            a.y = MAX_Y_ACCEL * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            a.y = - MAX_Y_ACCEL * delta;
        else a.y = 0;

        v.add(a);

        //check if velocity is within bound
        //todo, maybe flash screen red if hit max v
        if (v.x < 0)
            v.x = Math.max(v.x, - MAX_X_SPEED);
        else if (v.x > 0)
            v.x = Math.min(v.x, MAX_X_SPEED);
        if (v.y < 0)
            v.y = Math.max(v.y, - MAX_Y_SPEED);
        else if (v.y > 0)
            v.y = Math.min(v.y, MAX_Y_SPEED);

        pos.add(v);

        //check if position is within bound
        if (pos.x < 0) {
            pos.x = 0;
            v.x = 0; //might want to get rid of this
        } else if (pos.x  > gameWidth - fighterSprite.getWidth()) {
            pos.x = gameWidth - fighterSprite.getWidth();
            v.x = 0;
        }
        if (pos.y < 0) {
            pos.y = 0;
            v.y = 0;
        } else if (pos.y > gameHeight - fighterSprite.getHeight()) {
            pos.y = gameHeight - fighterSprite.getHeight();
            v.y = 0;
        }

    }

    public boolean checkCollision(Bullet b) {
        return rectangularRepresentation.overlaps(b.getBulletAsRectangle());
    }

    public Texture getFighterSprite() {
        return fighterSprite;
    }

    public Vector2 getPos() {
        return pos;
    }
}
