package com.mygdx.enemies;

import com.mygdx.hellinspace.GameMain;

public class StandardEnemy extends Enemy {

    public StandardEnemy(GameMain game) {
        super(game);
    }

    public void update(){
        pos.y += v.y;
    }

    public void fire(){
        //make bullets first
        System.out.println("pew pew");
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }
}
