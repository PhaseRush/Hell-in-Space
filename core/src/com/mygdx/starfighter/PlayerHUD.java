package com.mygdx.starfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;

public class PlayerHUD implements Disposable {
    final GameMain game;
    final Starfighter fighter;

    private int gameWidth = Gdx.graphics.getWidth();
    private int gameHeight = Gdx.graphics.getHeight();

    Texture healthBar, energyBar;
    private float healthPercent = 1; //0 = 0%, 1 = 100%
    private double energyPercent = 1; //0 = 0%, 1 = 100%

    private float margin = 0.1f;
    private double energyLossWhileMoving = 0.01;

    private int totalTime, score;

    public PlayerHUD(final GameMain game, Starfighter fighter) {

        this.game = game;
        this.fighter = fighter;

        healthBar = new Texture("HUD/Health.png");
        energyBar = new Texture("HUD/Energy.png");
    }

    public void show(float delta) {

        update();
        render();

    }

    public void takeDamage(float damage) {
        healthPercent -= damage/fighter.getHealth();
    }

    public void loseEnergy(double loss) {
        fighter.setEnergy(fighter.getEnergy() - loss);
        energyPercent = fighter.getEnergy()/600;
    }

    public void update() {

        if (!fighter.getAcceleration().isZero(margin)) {
            loseEnergy(energyLossWhileMoving);
        }

        totalTime++;
    }

    public void render() {

        if (healthPercent > 0) {
            game.batch.draw(healthBar, gameWidth / 2 - healthBar.getWidth() / 2, gameHeight - 20, healthBar.getWidth() * healthPercent, 8);
        }

        if (energyPercent > 0) {
            game.batch.draw(energyBar, gameWidth / 2 - energyBar.getWidth() / 2, gameHeight - 40, energyBar.getWidth() * (float)energyPercent, 8);
        }

        int seconds = (totalTime) / 60;

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(game.batch, Float.toString(seconds), gameWidth - energyBar.getWidth() / 2, gameHeight - energyBar.getHeight() / 2 - 5);

        font.draw(game.batch, "Score: " + score, gameWidth - energyBar.getWidth() / 2, gameHeight - energyBar.getHeight() / 2 - 20);
    }

    public void setScore(int s) {
        score += s;
    }

    @Override
    public void dispose() {
    }
}
