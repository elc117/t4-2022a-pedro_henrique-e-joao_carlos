package com.badlogic.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;


public class Fireball extends Rectangle {
    private int speed;
    public Texture texture;

    public Fireball(int speed) {
        this.x = 50;
        this.y = MathUtils.random(115, 400);
        this.height = 30;
        this.width = 70;
        this.speed = speed;
        this.texture = new Texture(Gdx.files.internal("fireball.png"));
    }

    public void Move() {
        this.x += speed * Gdx.graphics.getDeltaTime();
    }

    public void dispose() {
        this.texture.dispose();
    }
}