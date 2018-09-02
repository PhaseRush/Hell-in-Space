package com.mygdx.starfighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.projectiles.Bullet;

public abstract class Starfighter {
    private int maxXSpeed, maxYSpeed, maxXAccel, maxYAccel, maxRotAccel, maxRotSpeed, angularFriction;
    private float linearFriction;


    Texture fighterSprite;
    Rectangle rectangularRepresentation;

    Vector2 pos = new Vector2();
    Vector2 v = new Vector2();
    Vector2 a = new Vector2();

    float bearing;

    int width, height, clock;

    Starfighter pet; //could have a small pet following or something

    //meta
    int gameWidth = Gdx.graphics.getWidth();
    int gameHeight = Gdx.graphics.getHeight();

    public Starfighter(){
        fighterSprite = new Texture(Gdx.files.internal("StandardFighter.png"));
        width = fighterSprite.getWidth();
        height = fighterSprite.getHeight();
        rectangularRepresentation = new Rectangle(pos.x, pos.y, width, height);

        pos.x = gameWidth/2 - width/2;
        pos.y = gameHeight/2 - height/2;
        v.x = 0;
        v.y = 0;
        clock = 0;
        bearing = 0;

        maxXSpeed = 15;
        maxYSpeed = 15;
        maxXAccel = 5;
        maxYAccel = 5;
        maxRotSpeed = 10;
        maxRotAccel = 10;
        linearFriction = .5f;
        angularFriction = 1;


    }

    public void update(float delta){
        move(delta);
        clock++;
    }

    //based on this http://steigert.blogspot.com/2012/05/11-libgdx-tutorial-vectors.html
    private void move(float delta){
        //handle rotation
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            bearing += maxRotAccel * delta;
        }



        if (Gdx.input.isKeyPressed(Input.Keys.A))
            a.x = -maxXAccel * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            a.x = maxXAccel * delta;
        else a.x = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            a.y = maxYAccel * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            a.y = -maxYAccel * delta;
        else a.y = 0;

        //friction -todo
        float vMag = v.len();
        if (vMag > 0) {
            v.x -= (v.x/vMag) * linearFriction * delta;
            v.y -= (v.y/vMag) * linearFriction * delta;
        }

        v.add(a);

        //check if velocity is within bound
        //todo, maybe flash screen red if hit max v
        if (v.x < 0)
            v.x = Math.max(v.x, -maxXSpeed);
        else if (v.x > 0)
            v.x = Math.min(v.x, maxXSpeed);
        if (v.y < 0)
            v.y = Math.max(v.y, -maxYSpeed);
        else if (v.y > 0)
            v.y = Math.min(v.y, maxYSpeed);

        pos.add(v);

        //check if position is within bound
        if (pos.x < 0) {
            pos.x = 0;
            v.x = 0; //might want to get rid of this
        } else if (pos.x  > gameWidth - fighterSprite.getWidth()) {
            pos.x = gameWidth - fighterSprite.getWidth();
            v.x = 0;
        }
        if (pos.y < 0) {
            pos.y = 0;
            v.y = 0;
        } else if (pos.y > gameHeight - fighterSprite.getHeight()) {
            pos.y = gameHeight - fighterSprite.getHeight();
            v.y = 0;
        }

    }

    public boolean checkCollision(Bullet b) {
        return rectangularRepresentation.overlaps(b.getBulletAsRectangle());
    }

    public Texture getFighterSprite() {
        return fighterSprite;
    }

    public Vector2 getPos() {
        return pos;
    }
}
