package com.mygdx.starfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.projectiles.Bullet;

public abstract class Starfighter {
    int MAX_X_SPEED, MAX_Y_SPEED, MAX_X_ACCEL, MAX_Y_ACCEL;

    Vector2 pos = new Vector2();
    Vector2 v = new Vector2();
    Vector2 a = new Vector2();

    int clock;

    Starfighter pet; //could have a small pet following or something

    //meta
    int gameWidth = Gdx.graphics.getWidth();
    int gameHeight = Gdx.graphics.getHeight();

    public Starfighter(){
        pos.x = gameWidth/2;
        pos.y = gameHeight/2;
        v.x = 0;
        v.y = 0;
        clock = 0;

        MAX_X_SPEED = 15;
        MAX_Y_SPEED = 15;
        MAX_X_ACCEL = 5;
        MAX_Y_ACCEL = 5;

    }

    void update(){


        clock++;
    }

    void move(){

    }
    void handleKeyPress(){

    }

    public boolean checkCollision(Bullet b) {
        return false;
    }
}
