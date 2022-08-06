package com.badlogic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class DifficultyScreen implements Screen {
    final ProtectManjaro game;
    static private int WIDTH = 1200;
    static private int HEIGHT = 675;

    int BUTTON_WIDTH = 213;
    int BUTTON_HEIGHT = 110;

    Texture difficulty;

    Button easy;
    Button medium;
    Button hard;
    Button impossible;

    ButtonStyle easy_style;
    ButtonStyle medium_style;
    ButtonStyle hard_style;
    ButtonStyle impossible_style;

    Texture button_easy_up;
    Texture button_easy_down;
    Texture button_medium_up;
    Texture button_medium_down;
    Texture button_hard_up;
    Texture button_hard_down;
    Texture button_impossible_up;
    Texture button_impossible_down;

    Stage stage;
    Table table;

    public DifficultyScreen(final ProtectManjaro passed_game) {
        game = passed_game;
        game.camera.setToOrtho(false, WIDTH, HEIGHT);
        difficulty = new Texture(Gdx.files.internal("difficulty.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        stage.act();
        stage.draw();
        game.batch.draw(difficulty, 120, 450);
        game.batch.end();
        
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setBounds(0, 0, 1200, 675);

        easy_style = new ButtonStyle();
        medium_style = new ButtonStyle();
        hard_style = new ButtonStyle();
        impossible_style = new ButtonStyle();

        button_easy_up = new Texture(Gdx.files.internal("easy_button.png"));
        button_easy_down = new Texture(Gdx.files.internal("easy_button_down.png"));
        button_medium_up = new Texture(Gdx.files.internal("medium_button.png"));
        button_medium_down = new Texture(Gdx.files.internal("medium_button_down.png"));
        button_hard_up = new Texture(Gdx.files.internal("hard_button.png"));
        button_hard_down = new Texture(Gdx.files.internal("hard_button_down.png"));
        button_impossible_up = new Texture(Gdx.files.internal("impossible_button.png"));
        button_impossible_down = new Texture(Gdx.files.internal("impossible_button_down.png"));
        
        easy_style.up = new TextureRegionDrawable(button_easy_up);
        easy_style.down = new TextureRegionDrawable(button_easy_down);
        medium_style.up = new TextureRegionDrawable(button_medium_up);
        medium_style.down = new TextureRegionDrawable(button_medium_down);
        hard_style.up = new TextureRegionDrawable(button_hard_up);
        hard_style.down = new TextureRegionDrawable(button_hard_down);
        impossible_style.up = new TextureRegionDrawable(button_impossible_up);
        impossible_style.down = new TextureRegionDrawable(button_impossible_down);

        easy = new Button(easy_style);
        medium = new Button(medium_style);
        hard = new Button(hard_style);
        impossible = new Button(impossible_style);

        easy.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        medium.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        hard.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        impossible.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        easy.setPosition(400, 500);
        medium.setPosition(400, 380);
        hard.setPosition(400, 260);
        impossible.setPosition(400, 40);
        
        easy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.difficulty = 1;
                game.background_music.stop();
                game.setScreen(new GameScreen(game));
                dispose();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                easy_style.up = new TextureRegionDrawable(button_easy_down);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                easy_style.up = new TextureRegionDrawable(button_easy_up);
            }
        });
        medium.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.difficulty = 2;
                game.background_music.stop();
                game.setScreen(new GameScreen(game));
                dispose();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                medium_style.up = new TextureRegionDrawable(button_medium_down);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                medium_style.up = new TextureRegionDrawable(button_medium_up);
            }
        });
        hard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.difficulty = 3;
                game.background_music.stop();
                game.setScreen(new GameScreen(game));
                dispose();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                hard_style.up = new TextureRegionDrawable(button_hard_down);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                hard_style.up = new TextureRegionDrawable(button_hard_up);
            }
        });
        impossible.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.difficulty = 4;
                game.background_music.stop();
                game.setScreen(new GameScreen(game));
                dispose();
            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                impossible_style.up = new TextureRegionDrawable(button_impossible_down);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                impossible_style.up = new TextureRegionDrawable(button_impossible_up);
            }
        });

        table.add(easy);
        table.add(medium);
        table.add(hard);
        table.add(impossible);
        stage.addActor(table);

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
        stage.dispose();
        
        button_easy_up.dispose();
        button_easy_down.dispose();
        button_medium_up.dispose();
        button_medium_down.dispose();
        button_hard_up.dispose();
        button_hard_down.dispose();
        button_impossible_up.dispose();
        button_impossible_down.dispose();
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

}
