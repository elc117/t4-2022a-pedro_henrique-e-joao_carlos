package com.badlogic.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class ProtectManjaro extends Game {
	SpriteBatch batch;
	BitmapFont font;
	Music background_music;
	OrthographicCamera camera;
	int difficulty;

	@Override	
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		background_music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack_menu.mp3"));
		camera = new OrthographicCamera();
		Gdx.input.setCatchKey(Input.Keys.UP, true);
		Gdx.input.setCatchKey(Input.Keys.DOWN, true);
		Gdx.input.setCatchKey(Input.Keys.LEFT, true);
		Gdx.input.setCatchKey(Input.Keys.RIGHT, true);

		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();
		background_music.dispose();
	}
}
