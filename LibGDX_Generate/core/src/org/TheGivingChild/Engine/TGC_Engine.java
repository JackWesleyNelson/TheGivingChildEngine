package org.TheGivingChild.Engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TGC_Engine extends ApplicationAdapter {
	SpriteBatch batch;
	Texture mainScreen_Editor;
	Texture mainScreen_Options;
	Texture mainScreen_Play;
	Texture mainScreen_Splash;
	//testing git pulls comment
	@Override
	public void create () {
		batch = new SpriteBatch();
		//create main menu images
		mainScreen_Editor = new Texture("MainScreen_Editor.png");
		mainScreen_Options = new Texture("MainScreen_Options.png");
		mainScreen_Play = new Texture("MainScreen_Play.png");
		mainScreen_Splash = new Texture("MainScreen_Splash.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//draw main menu
		batch.draw(mainScreen_Splash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(mainScreen_Editor, Gdx.graphics.getWidth()*2/3, 0, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		batch.draw(mainScreen_Options, Gdx.graphics.getWidth()/3, 0, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		batch.draw(mainScreen_Play, 0, 0, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
		batch.end();
	}
}
