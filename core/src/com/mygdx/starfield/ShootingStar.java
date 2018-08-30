package com.mygdx.starfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class ShootingStar extends Star {

    private float x, y, vx, vy;
    private int width, height;
    private Color color;

    public ShootingStar(){
        Random r = new Random();
        x = r.nextInt(Gdx.graphics.getWidth());
        y = 0;


        vx = r.nextFloat()*50;
        vy = r.nextFloat()*50;

        int length = r.nextInt(3) + 10;
        width = length;
        height = length;

        color = Color.BLUE;
    }

    void update(){
        move();
    }

    public void move(){
        y += vy;
        x += vx;
        //delete this object if off screen (don't respawn star)
    }
}
