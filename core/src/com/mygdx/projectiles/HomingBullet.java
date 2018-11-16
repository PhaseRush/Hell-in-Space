package com.mygdx.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.hellinspace.GameMain;

public class HomingBullet extends Bullet {
    Vector2 target;

    public HomingBullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing, float damageValue, Vector2 target) {
        super(game, x, y, vx, vy, isGood, bearing, damageValue);
        this.target = target;
    }

    @Override
    public void move(float delta) {
        super.move(delta);
    }

    public Vector2 getTarget () {
        return target;
    }

    public void setTarget(Vector2 newTarget) {
        target = newTarget;
    }
}
