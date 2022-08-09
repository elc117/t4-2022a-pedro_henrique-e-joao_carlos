package com.badlogic.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class Knight extends Rectangle {
    private int speed;

    public boolean attacking;
    public boolean defending;
    public boolean knockback;

    public float animation_time;
    public float knockback_time;
    public float defending_time;
    public float attacking_cooldown;
     
    private Music sound_effect_attack;

    private Texture walkUpSheet;
    private Texture walkDownSheet;
    private Texture walkLeftSheet;
    private Texture walkRightSheet;
    private Texture idleSheet;
    private Texture attackSheet;
    private Texture defendingSheet;

    private TextureRegion[][] tmpUp;
    private TextureRegion[][] tmpDown;
    private TextureRegion[][] tmpLeft;
    private TextureRegion[][] tmpRight;
    private TextureRegion[][] tmpIdle;
    private TextureRegion[][] tmpAttack;
    private TextureRegion[][] tmpDefending;

    private TextureRegion[] walkUpFrames;
    private TextureRegion[] walkDownFrames;
    private TextureRegion[] walkLeftFrames;
    private TextureRegion[] walkRightFrames;
    private TextureRegion[] idleFrames;
    private TextureRegion[] attackFrames;
    private TextureRegion[] defendingFrames;

    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> defendingAnimation;

    private Animation<TextureRegion> currentAnimation;

    public Knight() {
        this.x = 900;
        this.y = 300;
        this.height = 50;
        this.width = 64;
        this.speed = 170;

        this.animation_time = 0f;
        this.attacking_cooldown = 0f;
        this.defending_time = 0f;
        this.knockback_time = 0f;

        this.attacking = false;
        this.defending = false;
        this.knockback = false;

        this.sound_effect_attack = Gdx.audio.newMusic(Gdx.files.internal("attack_effect.mp3"));

        this.walkUpSheet = new Texture(Gdx.files.internal("knight_walk_up.png"));
        this.walkDownSheet = new Texture(Gdx.files.internal("knight_walk_down.png"));
        this.walkLeftSheet = new Texture(Gdx.files.internal("knight_walk_left.png"));
        this.walkRightSheet = new Texture(Gdx.files.internal("knight_walk_right.png"));
        this.idleSheet = new Texture(Gdx.files.internal("knight.png"));
        this.attackSheet = new Texture(Gdx.files.internal("knight_attack.png"));
        this.defendingSheet = new Texture(Gdx.files.internal("knight_defending.png"));

        createWalkUpAnimation();
        createWalkDownAnimation();
        createWalkLeftAnimation();
        createWalkRightAnimation();
        createIdleAnimation();
        createAttackAnimation();
        createDefendingAnimation();
    }

    public void inputs() {
        if(!this.attacking && !this.knockback && !this.defending) {
            if(Gdx.input.isKeyJustPressed(Keys.SPACE) && this.attacking_cooldown >= 0) {
                setAnimation(6);
                this.sound_effect_attack.play();
                this.attacking = true;
                this.animation_time = 0f;
                this.attacking_cooldown = -1f;
            }
            else if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                this.y += this.speed * Gdx.graphics.getDeltaTime();
                setAnimation(1);
            }
            else if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                this.y -= this.speed * Gdx.graphics.getDeltaTime();
                setAnimation(2);
            }
            else if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                this.x -= this.speed * Gdx.graphics.getDeltaTime();
                setAnimation(3);
            }
            else if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                this.x += this.speed * Gdx.graphics.getDeltaTime();
                setAnimation(4);
            }
            else {
                setAnimation(5);
            }
        }

        if(this.y > 400){
            this.y = 400;
        }
        if(this.y < 115){
            this.y = 115;
        }
        if(this.x > 900){
            this.x = 900;
        }
        if(this.x < 200){
            this.x = 200;
        }
    }

    public void manageAttack(Dragon dragon){
        if(this.attacking && (this.attackAnimation.isAnimationFinished(this.animation_time))) {
            if(overlaps(dragon)){
                dragon.hp--;
                this.knockback = true;
                this.knockback_time = 0f;
                dragon.sound_effect_UI.play();
            }
            this.attacking = false;
            this.animation_time = 0f;
        }
    }

    public void manageKnockback(){
        if(this.knockback){
            setAnimation(5);
            if(x < 900){
                x += 50;
            }
            if(this.knockback_time >= 0.4f){
                this.knockback = false;
            }
        }
    }

    public void manageDefense(){
        if(this.defending){
            setAnimation(7);
            if(this.defending_time >= 0.125f){
                this.defending = false;
            }
        }
    }

    public void createWalkUpAnimation() {
        this.tmpUp = TextureRegion.split(this.walkUpSheet, this.walkUpSheet.getWidth() / 9, this.walkUpSheet.getHeight() / 1);
        this.walkUpFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkUpFrames[index] = this.tmpUp[0][index];
        }

        this.walkUpAnimation = new Animation<TextureRegion>(1f/10f, this.walkUpFrames); 
    }

    public void createWalkDownAnimation() {
        this.tmpDown = TextureRegion.split(this.walkDownSheet, this.walkDownSheet.getWidth() / 9, this.walkDownSheet.getHeight() / 1);
        this.walkDownFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkDownFrames[index] = this.tmpDown[0][index];
        }

        this.walkDownAnimation = new Animation<TextureRegion>(1f/10f, this.walkDownFrames); 
    }

    public void createWalkLeftAnimation() {
        this.tmpLeft = TextureRegion.split(this.walkLeftSheet, this.walkLeftSheet.getWidth() / 9, this.walkLeftSheet.getHeight() / 1);
        this.walkLeftFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkLeftFrames[index] = this.tmpLeft[0][index];
        }

        this.walkLeftAnimation = new Animation<TextureRegion>(1f/10f, this.walkLeftFrames); 
    }

    public void createWalkRightAnimation() {
        this.tmpRight = TextureRegion.split(this.walkRightSheet, this.walkRightSheet.getWidth() / 9, this.walkRightSheet.getHeight() / 1);
        this.walkRightFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkRightFrames[index] = this.tmpRight[0][index];
        }

        this.walkRightAnimation = new Animation<TextureRegion>(1f/10f, this.walkRightFrames); 
    }

    public void createIdleAnimation() {
        this.tmpIdle = TextureRegion.split(this.idleSheet, this.idleSheet.getWidth() / 1, this.idleSheet.getHeight() / 1);
        this.idleFrames = new TextureRegion[1];
  
        this.idleFrames[0] = this.tmpIdle[0][0];
        
        this.idleAnimation = new Animation<TextureRegion>(1f/10f, this.idleFrames); 
    }

    public void createAttackAnimation() {
        this.tmpAttack = TextureRegion.split(this.attackSheet, this.attackSheet.getWidth() / 6, this.attackSheet.getHeight() / 1);
        this.attackFrames = new TextureRegion[6];

        for(int index = 0; index < 6; index++) {
            this.attackFrames[index] = this.tmpAttack[0][index];
        }

        this.attackAnimation = new Animation<TextureRegion>(1f/10f, this.attackFrames); 
    }

    public void createDefendingAnimation() {
        this.tmpDefending = TextureRegion.split(this.defendingSheet, this.defendingSheet.getWidth() / 1, this.defendingSheet.getHeight() / 1);
        this.defendingFrames = new TextureRegion[1];
        
        this.defendingFrames[0] = this.tmpDefending[0][0];
    
        this.defendingAnimation = new Animation<TextureRegion>(1f/10f, this.defendingFrames); 
    }

    public TextureRegion getAnimationFrame() {
        return this.currentAnimation.getKeyFrame(this.animation_time, true);
    }

    public void setAnimation(int n) {
        switch(n) {
            case 1: 
                this.currentAnimation = this.walkUpAnimation;
                break;

            case 2: 
                this.currentAnimation = this.walkDownAnimation;
                break;

            case 3: 
                this.currentAnimation = this.walkLeftAnimation;
                break;

            case 4: 
                this.currentAnimation = this.walkRightAnimation;
                break;

            case 5: 
                this.currentAnimation = this.idleAnimation;
                break;
            case 6: 
                this.currentAnimation = this.attackAnimation;
                break;
            case 7: 
                this.currentAnimation = this.defendingAnimation;
                break;
        }
    }

    public void dispose(){
        this.walkUpSheet.dispose();
        this.walkDownSheet.dispose();
        this.walkLeftSheet.dispose();
        this.walkRightSheet.dispose();
        this.idleSheet.dispose();
        this.attackSheet.dispose();
        this.defendingSheet.dispose();
        this.sound_effect_attack.dispose();
    }
}
