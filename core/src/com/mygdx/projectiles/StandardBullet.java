package com.mygdx.projectiles;

import com.mygdx.hellinspace.GameMain;

public class StandardBullet extends Bullet {
    private float vx, vy, bearing;

    public StandardBullet(GameMain game, float x, float y, float shooterVx, float shooterVy, boolean isGood, float bearing, float damageValue) {
        super(game, x, y, shooterVx, shooterVy, 0,50, isGood, bearing, damageValue);
    }

    //same as top, but get to choose max vx vy
    public StandardBullet(GameMain game, float x, float y, float shooterVx, float shooterVy, float maxVx, float maxVy, boolean isGood, float bearing, float damageValue) {
        super(game, x, y, shooterVx, shooterVy, maxVx, maxVy, isGood, bearing, damageValue);
    }

    public StandardBullet(GameMain game, float x, float y, boolean isGood, float damageValue) {
        super(game, x, y, 0, 50, 0,50, isGood, (float) Math.PI / 2, damageValue);
        vx = 50;
        vy = 50;
        bearing = (float) Math.PI / 2;
    }
}
