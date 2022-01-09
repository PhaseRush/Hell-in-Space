package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.HomingBullet;
import com.mygdx.screens.GameScreen;

public class HomingEnemy extends Enemy{

    public HomingEnemy(GameMain game) {
        super(game);
        enemyTexture = new Texture(Gdx.files.internal("core/assets/Enemies/UpEnemy.png"));
    }


//    public HomingEnemy(GameMain game) {
//        super(game);
//    }


    @Override
    public void update(float delta){

        render();
        move(delta);

        if(clock == 10) {
            shoot();
            System.out.println("Homing shot");
        }

        clock++;
    }


    @Override
    public void shoot(){
        Bullet b = new HomingBullet(game, pos.x + width/2-10, pos.y, v.x, v.y, 0, -150, false, 180, projectileDamage,
                GameScreen.fighter.getPos(), GameScreen.fighter.getVel());

        GameScreen.updateManager.add(b);
    }
    //use default movement (for testing or whatever

}
