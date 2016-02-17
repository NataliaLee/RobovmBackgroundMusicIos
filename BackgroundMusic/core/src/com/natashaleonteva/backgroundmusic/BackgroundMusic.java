package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundMusic extends Game {
	SpriteBatch batch;
	private static BackgroundMusic instance=new BackgroundMusic();
	public MusicController musicController;
	public PlayerScreen playerScreen;


	public static BackgroundMusic getInstance(){
		return instance;
	}

	private BackgroundMusic(){
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playerScreen=new PlayerScreen();
		setScreen(playerScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	public void setMusicController(MusicController musicController) {
		this.musicController = musicController;
	}
}
