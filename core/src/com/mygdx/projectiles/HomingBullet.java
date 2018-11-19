package com.mygdx.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hellinspace.GameMain;

public class HomingBullet extends Bullet {
    Vector2 targetPos, targetVel;

    public HomingBullet(GameMain game, float x, float y, float vx, float vy, boolean isGood, float bearing, float damageValue, Vector2 targetPos, Vector2 targetVel) {
        super(game, x, y, vx, vy, isGood, bearing, damageValue);

        //set position and velocity of the target
        this.targetPos = targetPos;
        this.targetVel = targetVel;

        //load custom laser sprite
        bulletTexture = new Texture(Gdx.files.internal("Projectiles\\redlaser.png"));
    }

    @Override
    public void move(float delta) {
        if (targetPos == null) { //no targetPos, use default movement
            super.move(delta);
            return;
        }

        rotate();

    }

    /**
     * rotates the bullet to face the targetPos
     */
    private void rotate() {
        double targetX = targetPos.x - pos.x;
        double targetY = targetPos.y - pos.y;
        bearing += Math.atan2(targetY, targetX) * 180 / Math.PI;
    }

    public Vector2 getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Vector2 newTarget) {
        targetPos = newTarget;
    }
}
