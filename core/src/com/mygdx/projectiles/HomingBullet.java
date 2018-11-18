package com.mygdx.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hellinspace.GameMain;

public class HomingBullet extends Bullet {
    Vector2 target;
    public HomingBullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing, float damageValue, Vector2 target) {
        super(game, x, y, vx, vy, isGood, bearing, damageValue);
        this.target = target;
        bulletTexture = new Texture(Gdx.files.internal("Projectiles\\redlaser.png"));
    }

    @Override
    public void move(float delta) {
        if (target == null) return;

        super.move(delta); //todo
    }

    public Vector2 getTarget () {
        return target;
    }

    public void setTarget(Vector2 newTarget) {
        target = newTarget;
    }
}
