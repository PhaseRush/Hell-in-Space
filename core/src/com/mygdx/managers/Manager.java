package com.mygdx.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.enemies.Enemy;
import com.mygdx.enemies.StandardEnemy;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.starfighter.PlayerHUD;
import com.mygdx.starfighter.Starfighter;
import com.mygdx.util.Updatable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Manager implements Updatable, Disposable {
    List<Updatable> objects = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();
    //static List<> coordinates = new ArrayList<>();
    GameMain game;
    Starfighter fighter;
    int clock,enemyFrequency,maxEnemies,numEnemies,score;
    PlayerHUD hud;

    Texture endGame;

    public Manager(GameMain game, Starfighter fighter, PlayerHUD hud){
        this.game = game;
        this.fighter = fighter;
        this.hud = hud;

        endGame = new Texture("EndGame.png");

        clock = 0;
        enemyFrequency = 60;
        maxEnemies = 4;
        numEnemies = 0;
        score = 0;
    }

    @Override
    public void update(float delta) {
        List<Updatable> toRemove = new ArrayList<>(); //list of objects to remove
        Set<Enemy> EnemiesToRemove = new HashSet<>(); //list of enemies to remove

        clock++;

//        if (fighter.getHealth() <= 0) {
//            game.batch.draw(endGame, Gdx.graphics.getWidth()/2 - endGame.getWidth()/2, Gdx.graphics.getHeight()/2 - endGame.getHeight()/2);
//            return;
//        }

        if (clock % enemyFrequency == 0 && numEnemies <= maxEnemies) {
            numEnemies++;
            Enemy enemy = new StandardEnemy(game);
            enemies.add(enemy);
            game.batch.draw(enemy.getEnemyTexture(), enemy.getPos().x, enemy.getPos().y);
        }

        for (Enemy enemy : enemies) {
            enemy.update(delta);
            if (enemy.hasDied()) {
                EnemiesToRemove.add(enemy);

                // went off screen
                if (score > 0) {
                    score--;
                }

                numEnemies--;
            }
        }

        enemies.removeAll(EnemiesToRemove);

        for (Updatable object : new ArrayList<>(objects)) { //new ArrayList<>(objects)
            object.update(delta);
            //bullets
            if (object instanceof Bullet) {
                if (!((Bullet) object).isGood()) { //check starfighter collisions
                    if (fighter.checkCollision((Bullet) object)) {
                        objects.remove(object);
                        if (fighter.getHealth() > 0) {
                            fighter.decreaseHealth(((Bullet) object).getDamageValue());
                            hud.takeDamage(((Bullet) object).getDamageValue());
                            hud.show(delta);
                        }
                    }
                } else { //check enemies collision
                    //could easily optimize with x and vx etc.
                     // = something
                    for (Enemy enemy : enemies) {
                        if (enemy.checkCollision((Bullet) object)) {
                            toRemove.add(object); //remove bullet
                            //remove(object); // <- could use this instead
                            //decrease enemy health
                            //put explosion, etc
                            EnemiesToRemove.add(enemy);
                            // need a way to check if it's the same enemy
                            numEnemies--;
                        }
                    }
                }
            }
        }

        score += EnemiesToRemove.size();
        hud.updateScore(score);

        objects.removeAll(toRemove);
        enemies.removeAll(EnemiesToRemove);
    }

    public void add (Updatable up) {
        objects.add(up);
    }
    public void remove (Updatable up) {
        objects.remove(up);
    }

    public void dispose() {
    }

}