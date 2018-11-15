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
    private float energyPercent = 1; //0 = 0%, 1 = 100%

    private int totalTime;

    public PlayerHUD(final GameMain game, Starfighter fighter) {
        this.game = game;
        this.fighter = fighter;

        healthBar = new Texture("Health.png");
        energyBar = new Texture("Energy.png");
    }

    public void show(float delta) {
        if (healthPercent > 0) {
            game.batch.draw(healthBar, gameWidth / 2 - healthBar.getWidth() / 2, gameHeight - 20, healthBar.getWidth() * healthPercent, 8);

        }
        if (energyPercent > 0) {
            game.batch.draw(energyBar, gameWidth / 2 - energyBar.getWidth() / 2, gameHeight - 40, energyBar.getWidth() * energyPercent, 8);
        }

        totalTime++;

        int seconds = (totalTime) / 60;

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(game.batch, Float.toString(seconds), gameWidth - energyBar.getWidth() / 2, gameHeight - energyBar.getHeight() / 2 - 5);

    }

    public void updateScore(int score) {

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(game.batch, "Score: " + score, gameWidth - energyBar.getWidth() / 2, gameHeight - energyBar.getHeight() / 2 - 20);

    }

    public void takeDamage(float damage) {
        healthPercent -= damage/fighter.getHealth();
    }

    public void loseEnergy(float loss) {
        energyPercent -= loss/fighter.getEnergy();
    }

    @Override
    public void dispose() {
    }
}
