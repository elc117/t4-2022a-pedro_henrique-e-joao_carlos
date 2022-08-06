package com.badlogic.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class Dragon extends Rectangle {
    public int hp;

    private Texture flySheet;
    private TextureRegion[][] tmp;
    private TextureRegion[] flyFrames;
    private Animation<TextureRegion> flyAnimation;
    public Music sound_effect_UI;

    public Dragon() {
        this.height = 100;
        this.width = 20;
        this.x = 200;
        this.y = 250;
        this.hp = 5;
        this.sound_effect_UI = Gdx.audio.newMusic(Gdx.files.internal("dragon_effect_UI.mp3"));
        this.sound_effect_UI.setVolume(3);
        this.flySheet = new Texture(Gdx.files.internal("dragon.png"));
        createFlyAnimation();
    }

    public void createFlyAnimation() {
        this.tmp = TextureRegion.split(flySheet, flySheet.getWidth() / 2, flySheet.getHeight() / 1);
        this.flyFrames = new TextureRegion[2];

        for(int index = 0; index < 2; index++) {
            this.flyFrames[index] = tmp[0][index];
        }

        this.flyAnimation = new Animation<TextureRegion>(1f/2f, flyFrames); 
    }

    public TextureRegion getAnimationFrame(float state_time) {
        return flyAnimation.getKeyFrame(state_time, true);
    }

    public void dispose() {
        this.flySheet.dispose();
        this.sound_effect_UI.dispose();
    }
}