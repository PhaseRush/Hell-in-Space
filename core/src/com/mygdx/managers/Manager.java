package com.mygdx.managers;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hellinspace.GameMain;
import com.mygdx.util.Updatable;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Updatable, Disposable {
    List<Updatable> objects = new ArrayList<>();
    GameMain game;

    public Manager(GameMain game){
        this.game = game;
    }
    @Override
    public void update(float delta) {
        for (Updatable object : objects) {
            object.update(delta);
        }
    }

    public void add(Updatable up) {
        objects.add(up);
    }

    public void dispose(){

    }

}