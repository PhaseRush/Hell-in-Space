package com.mygdx.projectiles;

import com.mygdx.hellinspace.GameMain;

public class StandardEnemyBullet extends Bullet {
    private float vx, vy, bearing;

    public StandardEnemyBullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing, float damageValue) {
        super(game, x, y, vx, vy, isGood, bearing, damageValue);
    }

    public StandardEnemyBullet(GameMain game, float x, float y, boolean isGood, float damageValue) {
        super(game, x, y, 0, -150, isGood, (float) Math.PI / 2, damageValue);
        //vx = 50;
        //vy = -150;
        bearing = (float) Math.PI / 2;
    }
}
