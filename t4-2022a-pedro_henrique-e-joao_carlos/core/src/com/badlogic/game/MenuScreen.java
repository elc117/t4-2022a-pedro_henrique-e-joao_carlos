package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;



public class MenuScreen implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;
    Texture menu_background;

    public MenuScreen(final ProtectManjaro passed_game) {
        game = passed_game;

        menu_background = new Texture(Gdx.files.internal("menu_background.jpg"));
        game.camera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(menu_background, 0, 0);
        game.background_music.setLooping(true);
        game.background_music.play();
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            game.setScreen(new PlotScreen(game));
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
        menu_background.dispose();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

}
