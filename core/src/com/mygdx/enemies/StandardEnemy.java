package com.mygdx.enemies;

public class StandardEnemy extends Enemy {

    public StandardEnemy() {
        super();
    }

    public void update(){
        y += vy;
    }

    public void fire(){
        //make bullets first
        System.out.println("pew pew");
    }
}
