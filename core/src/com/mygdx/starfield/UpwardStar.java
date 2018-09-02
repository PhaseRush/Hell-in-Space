package com.mygdx.starfield;

import com.badlogic.gdx.Gdx;

public class UpwardStar extends Star{

    public UpwardStar(){
        super();
    }

    @Override
    void update(float delta){
        move(delta);
        super.twinkle();
    }

    @Override
    void move(float delta){
        y += v*delta;
        if (y > Gdx.graphics.getHeight()) {
            y = 0; //could also randomize x
            x = r.nextInt(Gdx.graphics.getWidth());
        }
    }
}
