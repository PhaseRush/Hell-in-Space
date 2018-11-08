package com.mygdx.enemies;

import com.mygdx.hellinspace.GameMain;

public class StandardEnemy extends Enemy {

    public StandardEnemy(GameMain game) {
        super(game);
    }

    public void update(){
        y += vy;
    }

    public void fire(){
        //make bullets first
        System.out.println("pew pew");
    }
}
