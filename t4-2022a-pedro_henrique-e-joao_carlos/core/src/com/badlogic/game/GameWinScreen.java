package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;

public class GameWinScreen implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;

    Texture game_win_background;

    public GameWinScreen(final ProtectManjaro passed_game) {
        game = passed_game;
        game_win_background = new Texture(Gdx.files.internal("game_win_background.png"));
        game.camera.setToOrtho(false, WIDTH, HEIGHT);
        game.game_music.stop();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.font.setColor(0, 0, 0, 1);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(game_win_background, 350, 20);
        game.font.draw(game.batch, "Press Enter to play again", 1000, 50);
        game.batch.end();
        
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
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
        game_win_background.dispose();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

}
