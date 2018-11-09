package com.mygdx.managers;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.enemies.Enemy;
import com.mygdx.enemies.StandardEnemy;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.starfighter.Starfighter;
import com.mygdx.util.Updatable;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Updatable, Disposable {
    List<Updatable> objects = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<Enemy>();
    GameMain game;
    Starfighter fighter;
    Enemy enemy;
    int clock,enemyFrequency,maxEnemies,numEnemies;

    public Manager(GameMain game, Starfighter fighter, Enemy enemy){
        this.game = game;
        this.fighter = fighter;
        this.enemy = enemy;

        clock = 0;
        enemyFrequency = 60;
        maxEnemies = 4;
        numEnemies = 0;
    }

    @Override
    public void update(float delta) {
        List<Updatable> toRemove = new ArrayList<>(); //list of objects to remove
        List<Enemy> EnemiesToRemove = new ArrayList<Enemy>(); //list of enemies to remove

        clock++;

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
                numEnemies--;
            }
        }

        for (Updatable object : new ArrayList<>(objects)) { //new ArrayList<>(objects)
            object.update(delta);
            //bullets
            if (object instanceof Bullet) {
                if (!((Bullet) object).isGood()) { //check starfighter collisions
                    if (fighter.checkCollision((Bullet) object)) {
                        objects.remove(object);
                        fighter.decreaseHealth(((Bullet) object).getDamageValue());
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
                            numEnemies--;
                        }
                    }
                }
            }
        }
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