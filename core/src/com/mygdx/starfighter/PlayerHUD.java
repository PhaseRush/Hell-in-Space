package com.mygdx.starfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;

public class PlayerHUD implements Disposable {
    final GameMain game;
    final Starfighter fighter;

    private int gameWidth = Gdx.graphics.getWidth();
    private int gameHeight = Gdx.graphics.getHeight();

    Texture bar;
    private float healthPercent = 1; //0 = 0%, 1 = 100%

    public PlayerHUD(final GameMain game, Starfighter fighter) {
        this.game = game;
        this.fighter = fighter;

        bar = new Texture("200x15.png");
    }

    public void show(float delta) {
        game.batch.draw(bar, gameWidth/2, 10, bar.getWidth() * healthPercent, 15);
    }

    public void takeDamage(int damage) {
        healthPercent -= damage/fighter.getHealth();
    }





    @Override
    public void dispose() {
    }
}
