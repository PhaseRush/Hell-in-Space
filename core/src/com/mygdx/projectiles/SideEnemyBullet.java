package com.mygdx.projectiles;

import com.mygdx.hellinspace.GameMain;

public class SideEnemyBullet extends Bullet {

    public SideEnemyBullet(GameMain game, float x, float y, boolean isGood, float damageValue, float vx, float vy) {
        super(game, x, y, vx, vy, isGood, (float) Math.PI / 2, damageValue);
    }
}
