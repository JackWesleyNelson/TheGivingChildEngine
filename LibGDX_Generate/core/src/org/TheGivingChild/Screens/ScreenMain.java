package org.TheGivingChild.Screens;

import org.TheGivingChild.Engine.MyChangeListener;
import org.TheGivingChild.Engine.TGC_Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * This class creates the main screen that is first seen when the game is started. It allows
 * for navigation between all screens that we have created. It has a table of buttons on
 * the bottom of the screen that allow you to navigate to the play screen, how to play
 * screen, editor screen, and options screen.
 * @author ctokunag
 */
class ScreenMain extends ScreenAdapter {
	private BitmapFont bitmapFontButton;
	private float buttonHeight;
	private Table mainScreenTable;
	private Skin skin = new Skin();
	private TGC_Engine game;
	private AssetManager manager;
	private boolean isRendered = false;
	private Label gameName;
	private LabelStyle ls;
	private Table labelTable;
	
	public ScreenMain() {
		game = ScreenAdapterManager.getInstance().game;
		manager = game.getAssetManager();
		mainScreenTable = createMainScreenTable();
		ScreenAdapterManager.getInstance().cb.setChecked(false);
		createLabel();
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
			table.add(b).size(Gdx.graphics.getWidth()/widthDivider/2, Gdx.graphics.getHeight()/3/2).pad((Gdx.graphics.getWidth()/200)*(game.getButtonAtlasNamesArray().length/2));
			buttonHeight = b.getHeight();
			final int j = i;
			//button to transition to different screens.
			b.addListener(new MyChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					super.changed(event, actor);
					ScreenAdapterManager.getInstance().show(ScreenAdapterEnums.values()[j/2]);
				}
			});
		}
		table.setPosition(Gdx.graphics.getWidth()/2, buttonHeight/2);
		return table;
	}
	
	public void createLabel() {
		labelTable = new Table();
		skin.add("labelBack", manager.get("SemiTransparentBG.png"));
		ls = new LabelStyle();
		ls.font = game.getBitmapFontButton();
		ls.background = skin.getDrawable("labelBack");
		gameName = new Label("Denver Defenders", ls);
		switch(Gdx.app.getType()){
		case Android:
			gameName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()));
			break;
			//if using the desktop set the width and height to a 16:9 resolution.
		case Desktop:
			gameName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*3));
			break;
		case iOS:
			gameName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()));
			break;
		default:
			gameName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*5));
			break;
		}
		gameName.setColor(Color.WHITE);
		labelTable.add(gameName).top();
		labelTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		ScreenAdapterManager.getInstance().screenTransitionInComplete = ScreenAdapterManager.getInstance().screenTransitionIn();
		if(manager.update()) {
			if(ScreenAdapterManager.getInstance().SCREEN_TRANSITION_TIME_LEFT <= 0 && ScreenAdapterManager.getInstance().screenTransitionInComplete) {
				Gdx.gl.glClearColor(1,1,1,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				ScreenAdapterManager.getInstance().backgroundImage();
				isRendered = true;
				show();
			}
		}
		
		if(ScreenAdapterManager.getInstance().SCREEN_TRANSITION_TIME_LEFT >= 0)
			ScreenAdapterManager.getInstance().SCREEN_TRANSITION_TIME_LEFT -= Gdx.graphics.getDeltaTime();
	}

	@Override
	public void show() {
		if(isRendered){
			game.getStage().addActor(labelTable);
			game.getStage().addActor(mainScreenTable);
		}
	}

	@Override
	public void hide() {
		isRendered = false;
		mainScreenTable.remove();
		labelTable.remove();
		ScreenAdapterManager.getInstance().cb.setChecked(false);
	}
}
