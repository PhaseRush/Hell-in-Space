package com.mygdx.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.projectiles.SideEnemyBullet;
import com.mygdx.screens.GameScreen;

public class SideEnemy extends Enemy {

    boolean left;

    public SideEnemy(GameMain game, boolean left) {
        super(game);

        this.left = left;

        // Get Texture
        if (left) {
            enemyTexture = new Texture(Gdx.files.internal("Enemies/RightEnemy.png"));
            width = enemyTexture.getWidth();
            height = enemyTexture.getHeight();
        } else {
            enemyTexture = new Texture(Gdx.files.internal("Enemies/LeftEnemy.png"));
            width = enemyTexture.getWidth();
            height = enemyTexture.getHeight();
        }

        // Set Initial Position, pad the screen's edges
        padding = width > height ? width : height;
        pos = new Vector2();
        pos.x = left ? 0 : gameWidth - padding;
        pos.y = gameHeight + padding;

        // Set Velocity
        v = new Vector2();
        v.x = 0;
        v.y = -1;

        // Set Enemy Information
        projectileDamage = 10;
        shotFrequency = 20;
        isAlive = true;

        clock = 0;
    }

    @Override
    public void shoot(){
        if (clock % 400 < 200) {
            int vx = left ? 500 : -500;
            Bullet b = new SideEnemyBullet(game, pos.x + width / 2, pos.y, false, projectileDamage, vx/10, 0);
            GameScreen.updateManager.add(b);
        }
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }
}
