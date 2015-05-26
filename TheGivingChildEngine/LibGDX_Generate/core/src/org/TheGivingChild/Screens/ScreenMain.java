package org.TheGivingChild.Screens;

import org.TheGivingChild.Engine.TGC_Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

class ScreenMain extends ScreenAdapter {
	private BitmapFont bitmapFontButton;
	private float buttonHeight;
    private Table mainScreenTable;
    private Skin skin = new Skin();
	
    private TGC_Engine game;
    
	public ScreenMain() {
		game = ScreenAdapterManager.getInstance().game;
		mainScreenTable = createMainScreenTable();
	}

	private Table createMainScreenTable() {
		//font for the buttons
		bitmapFontButton = game.getBitmapFontButton();
		//create a table for the buttons
        Table table = new Table();
        //adds the proper textures to skin from the asset manager
        skin.addRegions((TextureAtlas) game.getAssetManager().get("Packs/Buttons.pack"));
        //variable to keep track of button height for table positioning
        int widthDivider = (game.getButtonAtlasNamesArray().length/2);
        //iterate over button pack names in order to check
        for(int i = 0; i < game.getButtonAtlasNamesArray().length-1; i+=game.getButtonStates()){
        	TextButtonStyle bs = new TextButtonStyle();
        	bs.font = bitmapFontButton;
        	bs.down = skin.getDrawable(game.getButtonAtlasNamesArray()[i]);
        	bs.up = skin.getDrawable(game.getButtonAtlasNamesArray()[i+1]);
        	TextButton b = new TextButton("", bs);
        	b.setSize(Gdx.graphics.getWidth()/widthDivider, Gdx.graphics.getHeight()/3);
        	table.add(b).size(Gdx.graphics.getWidth()/widthDivider, Gdx.graphics.getHeight()/3);
        	buttonHeight = b.getHeight();
        	final int j = i;
        	//button to transition to different screens.
        	b.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					ScreenAdapterManager.getInstance().show(ScreenAdapterEnums.values()[j/2]);
				}
        	});
        }
        table.setPosition(Gdx.graphics.getWidth()/2, buttonHeight/2);
        return table;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void show() {
		game.getStage().addActor(mainScreenTable);
	}
	
	@Override
	public void hide() {
		mainScreenTable.remove();
	}
}