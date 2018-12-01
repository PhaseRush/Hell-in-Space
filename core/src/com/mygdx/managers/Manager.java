package com.mygdx.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.enemies.Enemy;
import com.mygdx.enemies.HomingEnemy;
import com.mygdx.enemies.SideEnemy;
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
    Set<Enemy> enemies = new HashSet<>();
    GameMain game;
    Starfighter fighter;
    private int clock,enemyFrequency,sideEnemyFrequency,maxEnemies,numEnemies,score, maxSideEnemies, numSideEnemies;
    PlayerHUD hud;

    Texture endGame;

    public Manager(GameMain game, Starfighter fighter, PlayerHUD hud) {
        this.game = game;
        this.fighter = fighter;
        this.hud = hud;

        endGame = new Texture("EndGame.png");

        clock = 0;
        enemyFrequency = 60;
        sideEnemyFrequency = 500;
        maxEnemies = 4;
        numEnemies = 0;
        // How many side enemy pairs are allowed at once
        maxSideEnemies = 2;
        numSideEnemies = 0;

        score = 0;
    }

    @Override
    public void update(float delta) {
        List<Updatable> toRemove = new ArrayList<>(); //list of objects to remove
        Set<Enemy> enemiesToRemove = new HashSet<>(); //list of enemies to remove

        clock++;

//        if (fighter.getHealth() <= 0) {
//            game.batch.draw(endGame, Gdx.graphics.getWidth()/2 - endGame.getWidth()/2, Gdx.graphics.getHeight()/2 - endGame.getHeight()/2);
//            return;
//        }

        if (clock % sideEnemyFrequency == 0 / 2 && numSideEnemies <= maxSideEnemies) {
            addSideEnemy();
        } else if (clock % enemyFrequency == 0 && numEnemies < maxEnemies) {//(clock % enemyFrequency == 0 && numEnemies < maxEnemies)
            addStandardEnemy();
            //System.out.println("num enemies: " + numEnemies);
        }

        //no homing enemies for github gameoff :(
//        if (clock == 100) {
//            addHomingEnemy();
//            System.out.println("SPAWNED HOMING ENEMY");
//        }

        for (Enemy enemy : enemies) {
            enemy.update(delta);
            if (enemy.hasDied()) {
                enemiesToRemove.add(enemy);

                // went off screen
                if (score > 0) {
                    score--;
                }
// side enemies
                numEnemies--;
            }
        }

        enemies.removeAll(enemiesToRemove);

        for (Updatable object : new ArrayList<>(objects)) {
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
                            enemiesToRemove.add(enemy);
                            //numEnemies--;
                            if (enemy instanceof SideEnemy) {
                                numSideEnemies--;
                            }
                        }
                    }
                }
            }
        }

        score += enemiesToRemove.size();
        hud.updateScore(score);

        objects.removeAll(toRemove);
        numEnemies -= enemiesToRemove.size();
        enemies.removeAll(enemiesToRemove);
    }



    public void add (Updatable up) {
        objects.add(up);
    }
    public void remove (Updatable up) {
        objects.remove(up);
    }

    public void dispose() { }

    public void addSideEnemy() {
        numEnemies += 2;
        numSideEnemies += 2;

        Enemy leftEnemy = new SideEnemy(game, true);
        Enemy rightEnemy = new SideEnemy(game, false);

        enemies.add(leftEnemy);
        enemies.add(rightEnemy);
    }

    private void addHomingEnemy() {
        numEnemies++;
        enemies.add(new HomingEnemy(game));
    }

    public void addStandardEnemy() {
        numEnemies++;

        Enemy enemy = new StandardEnemy(game);

        enemies.add(enemy);
    }

}