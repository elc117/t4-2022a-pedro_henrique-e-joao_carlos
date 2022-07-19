package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;

public class Menu implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;
    OrthographicCamera camera;
    Texture image;
    SpriteBatch batch;
    Music background_music;

    public Menu(final ProtectManjaro passed_game) {
        game = passed_game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        image = new Texture(Gdx.files.internal("menu_background.jpg"));
        background_music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack_menu.mp3"));
        background_music.setLooping(true);
        background_music.play();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(2);
        game.batch.begin();
        game.batch.draw(image, 0, 0);
        game.font.draw(game.batch, "Welcome to Protect Manjaro", 400, 500);
        game.font.draw(game.batch, "Press Space to begin", 450, 450);
        game.batch.end();

        // If player activates the game, dispose of this menu.
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            // game.setScreen(new GameScreen(game));
            System.out.print("apertou/n");
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
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

}
