package com.mygdx.managers;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.enemies.Enemy;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.projectiles.Bullet;
import com.mygdx.starfighter.Starfighter;
import com.mygdx.util.Updatable;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Updatable, Disposable {
    List<Updatable> objects = new ArrayList<>();
    GameMain game;
    Starfighter fighter;

    public Manager(GameMain game, Starfighter fighter){
        this.game = game;
        this.fighter = fighter;
    }
    @Override
    public void update(float delta) {
        List<Updatable> toRemove = new ArrayList<>(); //list of objects to remove

        for (Updatable object : objects) {
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
                    for (Updatable enenmy : objects) {
                        if (enenmy instanceof Enemy) {
                            if (((Enemy) enenmy).checkCollision((Bullet) object)) {
                                toRemove.add(object); //remove bullet
                                //decrease enemy health
                                //put explosion, etc
                            }
                        }
                    }
                }
            }
        }
        objects.removeAll(toRemove);
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