package com.badlogic.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;


public class ProtectManjaro extends Game {
	SpriteBatch batch;
	BitmapFont font;
	Music background_music;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		background_music = Gdx.audio.newMusic(Gdx.files.internal("soundtrack_menu.mp3"));
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
	}
}