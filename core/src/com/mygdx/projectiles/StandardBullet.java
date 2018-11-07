package com.mygdx.projectiles;

import com.mygdx.hellinspace.GameMain;

public class StandardBullet extends Bullet {
    private float vx, vy, bearing;

    public StandardBullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing) {
        super(game, x, y, vx, vy, isGood, bearing);
    }

    public StandardBullet(GameMain game, float x, float y, boolean isGood) {
        super(game, x, y, 50, 50, isGood, (float) Math.PI / 2);
        vx = 50;
        vy = 50;
        bearing = (float) Math.PI / 2;
    }
}
