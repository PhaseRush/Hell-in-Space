package com.mygdx.enemies;

import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.HomingBullet;
import com.mygdx.screens.GameScreen;

public class HomingEnemy extends Enemy{

    public HomingEnemy(GameMain game) {
        super(game);
    }

    @Override
    public void shoot(){
        Bullet b = new HomingBullet(game, pos.x + width/2, pos.y, 10, 10, 25,false, 0, projectileDamage,
                GameScreen.fighter.getPos(),
                GameScreen.fighter.getVel());

        GameScreen.updateManager.add(b);
    }

    //use default movement (for testing or whatever

}
