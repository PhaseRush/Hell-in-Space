package com.mygdx.starfield;

import com.badlogic.gdx.Gdx;

public class UpwardStar extends Star{

    public UpwardStar(){
        super();
    }

    @Override
    void update(){
        move();
        super.twinkle();
    }

    @Override
    void move(){
        y += v;
        if (y > Gdx.graphics.getHeight()) {
            y = 0; //could also randomize x
            x = r.nextInt(Gdx.graphics.getWidth());
        }
    }
}
