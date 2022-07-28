package com.badlogic.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class ProtectManjaro extends Game {
	SpriteBatch batch;
	BitmapFont font;
	Music background_music;
	Music game_music;
	OrthographicCamera camera;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		background_music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack_menu.mp3"));
		game_music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack_game.mp3"));
		camera = new OrthographicCamera();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		background_music.dispose();
		game_music.dispose();
	}
}
