package com.mygdx.managers;

import java.util.ArrayList;
import java.util.List;

public class EnemyManger {
    List<Object> objects = new ArrayList<>();

    public void update() {
        for (Object o : objects) {
            //o.update(); //problem
        }
    }

    public void add(Object o) {
        objects.add(o);
    }

}
