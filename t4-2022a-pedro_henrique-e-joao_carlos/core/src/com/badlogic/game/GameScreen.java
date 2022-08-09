package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.audio.Music;

import java.util.ListIterator;
import java.util.ArrayList;



public class GameScreen implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;

    int FIREBALL_SPEED;
    float FIREBALL_COOLDOWN;

    float state_time;
    float dt;

    Texture game_background;

    Knight knight;
    Dragon dragon;

    Castle castelo1;
    Castle castelo2;

    ArrayList<Fireball> fireballs;
    float fireball_cooldown;

    Texture hp_bar_sheet;
    TextureRegion[][] hp_bar;
    
    Music game_music;
    Music sound_effect_explosion;

    public GameScreen(final ProtectManjaro passed_game) {
        game = passed_game;
        state_time = 0f;
        dt = Gdx.graphics.getDeltaTime();

        knight = new Knight();
        knight.setAnimation(5);

        dragon = new Dragon();

        castelo1 = new Castle(1000, 280);
        castelo2 = new Castle(1000, 100);

        game_background = new Texture(Gdx.files.internal("game_background.png"));
        
        fireballs = new ArrayList<Fireball>();
        fireball_cooldown = 0f;

        hp_bar_sheet = new Texture(Gdx.files.internal("hp_bars.png"));
        hp_bar = new TextureRegion[1][6];
        hp_bar = TextureRegion.split(hp_bar_sheet, hp_bar_sheet.getWidth() / 1, hp_bar_sheet.getHeight() / 6);

        game_music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack_game.mp3"));
        sound_effect_explosion = Gdx.audio.newMusic(Gdx.files.internal("explosion_effect.wav"));

        game_music.setLooping(true);
        game_music.play();

        switch(game.difficulty){
            case 1:
                FIREBALL_SPEED = 120;
                FIREBALL_COOLDOWN = -1.3f;
                break;
            case 2:
                FIREBALL_SPEED = 200;
                FIREBALL_COOLDOWN = -0.9f;
                break;
            case 3:
                FIREBALL_SPEED = 250;
                FIREBALL_COOLDOWN = -0.7f;
                break;
            case 4:
                FIREBALL_SPEED = 5000;
                FIREBALL_COOLDOWN = -0.01f;
                break;
        }
        
        game.camera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateDt();

        TextureRegion currentDragonFrame = dragon.getAnimationFrame(state_time);
        TextureRegion currentKnightFrame = knight.getAnimationFrame();

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        game.batch.draw(game_background, 0, 0);

        game.batch.draw(castelo1.getTexture(), castelo1.x, castelo1.y);
        game.batch.draw(castelo2.getTexture(), castelo2.x, castelo2.y);
        
        if(knight.attacking){
            game.batch.draw(currentKnightFrame, knight.x - 70, knight.y - 8);
        }
        else{
            game.batch.draw(currentKnightFrame, knight.x, knight.y);
        }

        for(Fireball fireball : fireballs){
            game.batch.draw(fireball.texture, fireball.x, fireball.y);
        }

        game.batch.draw(currentDragonFrame, -550, 50);
        game.batch.draw(hp_bar[dragon.hp][0], 450, 0);
        game.font.draw(game.batch, "DRAGON HP:", 470, 50);
        game.batch.end();

        
        knight.manageAttack(dragon);

        knight.inputs();

        knight.manageKnockback();

        knight.manageDefense();

        manageFireball();

        checkGameOver();
    }

    public void updateDt(){
        state_time += dt;
        knight.animation_time += dt;
        knight.attacking_cooldown += dt;
        knight.defending_time += dt;
        knight.knockback_time += dt;
        fireball_cooldown += dt;
    }

    public void manageFireball(){

        if(fireball_cooldown >= 0){
            fireballs.add(new Fireball(FIREBALL_SPEED));
            fireball_cooldown = FIREBALL_COOLDOWN;
        }

        ListIterator<Fireball> iterator = fireballs.listIterator();
        while(iterator.hasNext()){
            Fireball fireball = iterator.next();
            fireball.Move();
            if(fireball.x > 1000){
                iterator.remove();
                if(sound_effect_explosion.isPlaying()){
                    sound_effect_explosion.stop();
                }
                sound_effect_explosion.play();
                castelo1.hits += 1;
                castelo2.hits += 1;
            }
            if(fireball.overlaps(knight) && !knight.attacking && !knight.knockback){
                knight.defending = true;
                knight.defending_time = 0f;
                iterator.remove();
            }
        }
    }

    public void checkGameOver(){
        if(castelo1.hits >=6){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        if(dragon.hp < 0){
            game.setScreen(new GameWinScreen(game));
            dispose();
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
        knight.dispose();
        dragon.dispose();
        castelo1.dispose();
        castelo2.dispose();

        for(Fireball fireball : fireballs){
            fireball.dispose();
        }

        game_background.dispose();
    
        hp_bar_sheet.dispose();
    
        game_music.dispose();
        sound_effect_explosion.dispose();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

}
