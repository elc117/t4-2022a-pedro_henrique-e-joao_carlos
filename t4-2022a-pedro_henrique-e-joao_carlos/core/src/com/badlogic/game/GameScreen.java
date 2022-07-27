package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ListIterator;

import java.util.ArrayList;



public class GameScreen implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;
    boolean game_over;

    float state_time;
    float animation_time;
    float attacking_cooldown;
    boolean attacking;

    ArrayList<Fireball> fireballs;
    Texture fireball_img;
    float fireball_cooldown;

    Texture game_background;
    Texture castelo1;
    Texture castelo2;
    Texture castelo3;
    int castle_hits;

    Texture walkUpSheet;
    Texture walkDownSheet;
    Texture walkLeftSheet;
    Texture walkRightSheet;
    Texture idleSheet;
    Texture attackSheet;
    Texture flySheet;

    Knight knight;
    Dragon dragon;

    public GameScreen(final ProtectManjaro passed_game) {
        game = passed_game;
        game_over = false;

        state_time = 0f;
        animation_time = 0f;
        attacking_cooldown = 0f;
        fireball_cooldown = 0f;
        attacking = false;

        game_background = new Texture(Gdx.files.internal("game_background.png"));
        castelo1 = new Texture(Gdx.files.internal("Castle1.png"));
        castelo2 = new Texture(Gdx.files.internal("Castle2.png"));
        castelo3 = new Texture(Gdx.files.internal("Castle3.png"));
        castle_hits = 0;

        walkUpSheet = new Texture(Gdx.files.internal("knight_walk_up.png"));
        walkDownSheet = new Texture(Gdx.files.internal("knight_walk_down.png"));
        walkLeftSheet = new Texture(Gdx.files.internal("knight_walk_left.png"));
        walkRightSheet = new Texture(Gdx.files.internal("knight_walk_right.png"));
        idleSheet = new Texture(Gdx.files.internal("knight.png"));
        attackSheet = new Texture(Gdx.files.internal("knight_attack.png"));

        knight = new Knight();

        knight.createWalkUpAnimation(walkUpSheet);
        knight.createWalkDownAnimation(walkDownSheet);
        knight.createWalkLeftAnimation(walkLeftSheet);
        knight.createWalkRightAnimation(walkRightSheet);
        knight.createIdleAnimation(idleSheet);
        knight.createAttackAnimation(attackSheet);
        knight.setAnimation(5);

        flySheet = new Texture(Gdx.files.internal("red_dragon.png"));
        dragon = new Dragon();
        dragon.createFlyAnimation(flySheet);

        fireballs = new ArrayList<Fireball>();
        fireball_img = new Texture(Gdx.files.internal("fireball.png"));
        
        game.camera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        state_time += Gdx.graphics.getDeltaTime();
        animation_time += Gdx.graphics.getDeltaTime();
        attacking_cooldown += Gdx.graphics.getDeltaTime();
        fireball_cooldown += Gdx.graphics.getDeltaTime();

        TextureRegion currentDragonFrame = dragon.getAnimationFrame(state_time);
        TextureRegion currentKnightFrame = knight.getAnimationFrame(animation_time);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(game_background, 0, 0);

        if(castle_hits < 3){
            game.batch.draw(castelo1, 1000, 280);
            game.batch.draw(castelo1, 1000, 100);
        }
        else if(castle_hits >= 3 && castle_hits < 6){
            game.batch.draw(castelo2, 1000, 280);
            game.batch.draw(castelo2, 1000, 100);
        }
        else if(castle_hits >= 6){
            game.batch.draw(castelo3, 1000, 280);
            game.batch.draw(castelo3, 1000, 100);
            game_over = true;
        }

        if(attacking){
            game.batch.draw(currentKnightFrame, knight.x - 70, knight.y - 8);
        }
        else{
            game.batch.draw(currentKnightFrame, knight.x, knight.y);
        }

        for(Fireball fireball : fireballs){
            game.batch.draw(fireball_img, fireball.x, fireball.y);
        }

        game.batch.draw(currentDragonFrame, dragon.x, dragon.y);
        game.font.draw(game.batch, "DRAGON HP: " + dragon.hp, 300, 500);
        game.batch.end();

        if(attacking && (animation_time >= 0.55f)) {
            if(knight.overlaps(dragon)){
                dragon.hp -= 20;
            }
            attacking = false;
            animation_time = 0f;
        }

        if(!attacking) {
            if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                knight.y += 100 * Gdx.graphics.getDeltaTime();
                knight.setAnimation(1);
            }
            else if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                knight.y -= 100 * Gdx.graphics.getDeltaTime();
                knight.setAnimation(2);
            }
            else if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                knight.x -= 100 * Gdx.graphics.getDeltaTime();
                knight.setAnimation(3);
            }
            else if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                knight.x += 100 * Gdx.graphics.getDeltaTime();
                knight.setAnimation(4);
            }
            else if((Gdx.input.isKeyPressed(Keys.P)) && attacking_cooldown >= 0) {
                knight.setAnimation(6);
                attacking = true;
                animation_time = 0f;
                attacking_cooldown = -1f;
            }
            else{
                knight.setAnimation(5);
            }
        }

        if(knight.y > 400){
            knight.y = 400;
        }
        if(knight.y < 100){
            knight.y = 100;
        }
        if(knight.x > 900){
            knight.x = 900;
        }
        if(knight.x < 200){
            knight.x = 200;
        }

        if(fireball_cooldown >= 0){
            fireballs.add(new Fireball());
            fireball_cooldown = -3f;
        }

        ListIterator<Fireball> iterator = fireballs.listIterator();
        while(iterator.hasNext()){
            Fireball fireball = iterator.next();
            fireball.Move();
            if(fireball.x > 1000){
                iterator.remove();
                castle_hits += 1;
            }
            if(fireball.overlaps(knight)){
                iterator.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

}

class Dragon extends Rectangle {
    public int hp;
    private TextureRegion[][] tmp;
    private TextureRegion[] flyFrames;
    private Animation<TextureRegion> flyAnimation;

    public Dragon() {
        this.height = 128;
        this.width = 200;
        this.x = 10;
        this.y = 250;
        this.hp = 100;
    }

    public void createFlyAnimation(Texture flySheet) {
        this.tmp = TextureRegion.split(flySheet, flySheet.getWidth() / 3, flySheet.getHeight() / 1);
        this.flyFrames = new TextureRegion[3];

        for(int index = 0; index < 3; index++) {
            this.flyFrames[index] = tmp[0][index];
        }

        flyAnimation = new Animation<TextureRegion>(1f/6f, flyFrames); 
    }

    public TextureRegion getAnimationFrame(float state_time) {
        return flyAnimation.getKeyFrame(state_time, true);
    }
}

class Knight extends Rectangle {
    private TextureRegion[][] tmpUp;
    private TextureRegion[][] tmpDown;
    private TextureRegion[][] tmpLeft;
    private TextureRegion[][] tmpRight;
    private TextureRegion[][] tmpIdle;
    private TextureRegion[][] tmpAttack;

    private TextureRegion[] walkUpFrames;
    private TextureRegion[] walkDownFrames;
    private TextureRegion[] walkLeftFrames;
    private TextureRegion[] walkRightFrames;
    private TextureRegion[] idleFrames;
    private TextureRegion[] attackFrames;

    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> attackAnimation;

    private Animation<TextureRegion> currentAnimation;

    public Knight() {
        this.x = 900;
        this.y = 300;
        this.height = 64;
        this.width = 64;
    }

    public void createWalkUpAnimation(Texture walkUpSheet) {
        this.tmpUp = TextureRegion.split(walkUpSheet, walkUpSheet.getWidth() / 9, walkUpSheet.getHeight() / 1);
        this.walkUpFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkUpFrames[index] = tmpUp[0][index];
        }

        this.walkUpAnimation = new Animation<TextureRegion>(1f/10f, walkUpFrames); 
    }

    public void createWalkDownAnimation(Texture walkDownSheet) {
        this.tmpDown = TextureRegion.split(walkDownSheet, walkDownSheet.getWidth() / 9, walkDownSheet.getHeight() / 1);
        this.walkDownFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkDownFrames[index] = tmpDown[0][index];
        }

        this.walkDownAnimation = new Animation<TextureRegion>(1f/10f, walkDownFrames); 
    }

    public void createWalkLeftAnimation(Texture walkLeftSheet) {
        this.tmpLeft = TextureRegion.split(walkLeftSheet, walkLeftSheet.getWidth() / 9, walkLeftSheet.getHeight() / 1);
        this.walkLeftFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkLeftFrames[index] = tmpLeft[0][index];
        }

        this.walkLeftAnimation = new Animation<TextureRegion>(1f/10f, walkLeftFrames); 
    }

    public void createWalkRightAnimation(Texture walkRightSheet) {
        this.tmpRight = TextureRegion.split(walkRightSheet, walkRightSheet.getWidth() / 9, walkRightSheet.getHeight() / 1);
        this.walkRightFrames = new TextureRegion[9];

        for(int index = 0; index < 9; index++) {
            this.walkRightFrames[index] = tmpRight[0][index];
        }

        this.walkRightAnimation = new Animation<TextureRegion>(1f/10f, walkRightFrames); 
    }

    public void createIdleAnimation(Texture idleSheet) {
        this.tmpIdle = TextureRegion.split(idleSheet, idleSheet.getWidth() / 1, idleSheet.getHeight() / 1);
        this.idleFrames = new TextureRegion[1];
        
        for(int index = 0; index < 1; index++) {
            this.idleFrames[index] = tmpIdle[0][index];
        }

        this.idleAnimation = new Animation<TextureRegion>(1f/10f, idleFrames); 
    }

    public void createAttackAnimation(Texture attackSheet) {
        this.tmpAttack = TextureRegion.split(attackSheet, attackSheet.getWidth() / 6, attackSheet.getHeight() / 1);
        this.attackFrames = new TextureRegion[6];

        for(int index = 0; index < 6; index++) {
            this.attackFrames[index] = tmpAttack[0][index];
        }

        this.attackAnimation = new Animation<TextureRegion>(1f/10f, attackFrames); 
    }

    public TextureRegion getAnimationFrame(float state_time) {
        return currentAnimation.getKeyFrame(state_time, true);
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
        }
    }
}

class Fireball extends Rectangle {
    private int speed;

    public Fireball() {
        this.x = 50;
        this.y = MathUtils.random(100, 400-64);
        this.height = 17;
        this.width = 59;
        this.speed = 200;
    }

    public void Move(){
        this.x += speed * Gdx.graphics.getDeltaTime();
    }
}
