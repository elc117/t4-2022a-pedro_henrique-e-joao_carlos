package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;



public class GameScreen implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;
    Texture game_background;
    Texture castelo1;
    Texture knight_img;
    Texture dragon_img;
    Rectangle knight;
    Rectangle dragon;

    public GameScreen(final ProtectManjaro passed_game) {
        game = passed_game;

        game_background = new Texture(Gdx.files.internal("game_background.png"));
        castelo1 = new Texture(Gdx.files.internal("Castle1.png"));
        knight_img = new Texture(Gdx.files.internal("knight.png"));
        dragon_img = new Texture(Gdx.files.internal("red_dragon1.png"));

        knight = new Rectangle();
        knight.x = 900;
        knight.y = 300;
        knight.height = 64;
        knight.width = 64;

        dragon = new Rectangle();
        dragon.height = 128;
        dragon.width = 144;
        dragon.x = 10;
        dragon.y = 250;

        game.camera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(game_background, 0, 0);
        game.batch.draw(castelo1, 1000, 280);
        game.batch.draw(castelo1, 1000, 100);
        game.batch.draw(knight_img, knight.x, knight.y);
        game.batch.draw(dragon_img, dragon.x, dragon.y);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            knight.y += 250 * Gdx.graphics.getDeltaTime();;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            knight.y -= 250 * Gdx.graphics.getDeltaTime();;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            knight.x -= 250 * Gdx.graphics.getDeltaTime();;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            knight.x += 250 * Gdx.graphics.getDeltaTime();;
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
