package com.badlogic.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class Castle {
    public int x;
    public int y;
    public int hits;
    private Texture state1;
    private Texture state2;
    private Texture state3;

    public Castle(int x, int y) {
        this.x = x;
        this.y = y;
        this.hits = 0;
        this.state1 = new Texture(Gdx.files.internal("Castle1.png"));
        this.state2 = new Texture(Gdx.files.internal("Castle2.png"));
        this.state3 = new Texture(Gdx.files.internal("Castle3.png"));
    }

    public Texture getTexture() {
        if(hits >= 3 && hits < 5){
            return state2;
        }
        else if(hits >= 5){
            return state3;
        }

        return state1;
    }

    public void dispose() {
        this.state1.dispose();
        this.state2.dispose();
        this.state3.dispose();
    }
}
