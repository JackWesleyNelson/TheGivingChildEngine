package org.TheGivingChild.Screens;

import org.TheGivingChild.Engine.MyChangeListener;
import org.TheGivingChild.Engine.TGC_Engine;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

/**
 * This is the options screen. It has check boxes to control both sound and music or each individually.
 * It also creates a volume slider to give the user more control.
 * @author ctokunag
 *
 */
class ScreenOptions extends ScreenAdapter {
	private Skin skin;
	private Skin buttonSkin;
	private Skin sliderSkin;
	private Table optionsTable;
	private Array<CheckBox> options;
	private Table choicesTable;
	private Table sliderTable;
	private Table overallTable;
	private BitmapFont font;
	private TextButtonStyle style;
	private CheckBoxStyle cbStyle;
	private CheckBoxStyle muteStyle;
	private CheckBox mute;
	private TGC_Engine game;
	private AssetManager manager;
	private SpriteBatch batch;
	private Texture title;
	private boolean isRendered = false;
	private String[] optionsArray = {"   Music   ", 
			  						 "   Sound   "};
	private Slider slider;
	private Label sliderName;
	private float volume;

	/**
	 * Instantiates the sprite batch and loads the assets it needs and initially creates the tables.
	 * Sets the check boxes to checked.
	 */
	public ScreenOptions() {
		game = ScreenAdapterManager.getInstance().game;
		batch = new SpriteBatch();
		manager = game.getAssetManager();
		manager.load("titleOptionScreen.png", Texture.class);
		createOptionsTable();
		createOverallTable();
		ScreenAdapterManager.getInstance().cb.setChecked(false);
		//set the inital state of sound to be on
		for(CheckBox c: options){
			c.setChecked(true);
		}
	}
	
	/**
	 * Renders the screen transition until it is complete.
	 * Then it renders the two tables that hold all of the buttons. 
	 */
	@Override
	public void render(float delta) {
		ScreenAdapterManager.getInstance().screenTransitionInComplete = ScreenAdapterManager.getInstance().screenTransitionIn();
		if(manager.update()) {
			if(ScreenAdapterManager.getInstance().SCREEN_TRANSITION_TIME_LEFT <= 0 && ScreenAdapterManager.getInstance().screenTransitionInComplete) {
				if(manager.isLoaded("titleOptionScreen.png"))
					title = manager.get("titleOptionScreen.png");
				Gdx.gl.glClearColor(1,1,0,1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				ScreenAdapterManager.getInstance().backgroundImage();
				batch.begin();
				batch.draw(title, (Gdx.graphics.getWidth()-title.getWidth())/2, Gdx.graphics.getHeight()-title.getHeight());
				batch.end();
				isRendered = true;
				show();
				for(CheckBox c : options) {
					if(c.isChecked()) {
						if(c.equals(options.get(0))){
							game.musicEnabled = true;
						}
						else if(c.equals(options.get(1))){
							game.soundEnabled = true;
						}
					}
					else if(c.equals(options.get(0))){
						game.musicEnabled = false;
					}
					else if(c.equals(options.get(1))){
						game.soundEnabled = false;
					}
				}
			}
		}
		if(ScreenAdapterManager.getInstance().SCREEN_TRANSITION_TIME_LEFT >= 0)
			ScreenAdapterManager.getInstance().SCREEN_TRANSITION_TIME_LEFT -= Gdx.graphics.getDeltaTime();
	}
	
	
	/**
	 * Adds the two tables and resets a boolean so the screen transtion is shown
	 */
	@Override
	public void show() {
		if(isRendered) {
			game.getStage().addActor(optionsTable);
			game.getStage().addActor(overallTable);
			isRendered = false;
		}
	};
	
	/**
	 * Removes all of the tables so they do not show on other screens.
	 * Resets the check boxes also.
	 */
	@Override
	public void hide() {
		optionsTable.remove();
		overallTable.remove();
		ScreenAdapterManager.getInstance().cb.setChecked(false);
	}
	
	/**
	 * Creates the table that holds the back button.
	 */
	private void createOptionsTable() {
		//Sets up the needed variables and parameters
		optionsTable = new Table();
		skin = new Skin();
		skin.addRegions((TextureAtlas) manager.get("Packs/ButtonsEditor.pack"));
		//Creates the buttons and sets table to origin
		createButton();
		optionsTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		optionsTable.align(Align.bottom);
	}
	
	/**
	 * Creates the table that the sound and music check boxes are added to so they are formatted correctly.
	 */
	private void createChoices() {
		choicesTable = new Table();
		buttonSkin = new Skin();
		buttonSkin.addRegions((TextureAtlas) manager.get("Packs/CheckBoxes.pack"));
		createCheckBoxes();
	}
	
	/**
	 * Creates the back button to return to the main screen and it's listener.
	 */
	private void createButton() {
		font = new BitmapFont();
		skin = new Skin();
		skin.addRegions((TextureAtlas) manager.get("Packs/Buttons.pack"));
		style = new TextButtonStyle();
		style.font = font; 
		style.up = skin.getDrawable("Button_MainScreen");
		style.down = skin.getDrawable("ButtonPressed_MainScreen");
		TextButton backButton = new TextButton("", style);

		/**
		 * Creates the listener for the back button.
		 * Shows the main screen when pushed.
		 */
		backButton.addListener(new MyChangeListener() { 			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				super.changed(event, actor);
				//Calls the screen manager and has main be the shown screen if Back is hit
				ScreenAdapterManager.getInstance().show(ScreenAdapterEnums.MAIN);
			}
		});
		backButton.setSize(150,300);
		optionsTable.add(backButton);
	}
	
	/**
	 * Creates the check boxes used for the sound and the music toggle.
	 */
	private void createCheckBoxes() {
		options = new Array<CheckBox>();
		font = game.getBitmapFontButton();
		cbStyle = new CheckBoxStyle();
		Skin bSkin = new Skin();
		bSkin.add("background", manager.get("SemiTransparentBG.png"));
		cbStyle.font = font;
		cbStyle.checkboxOff = buttonSkin.getDrawable("CheckBox");
		cbStyle.checkboxOn = buttonSkin.getDrawable("CheckBox_Checked");
		for(int i = 0; i < optionsArray.length; i++) {
			CheckBox checkbox = new CheckBox("", cbStyle);
			LabelStyle ls = new LabelStyle();
			ls.font = font;
			ls.background = bSkin.getDrawable("background");
			Label label = new Label(optionsArray[i], ls);switch(Gdx.app.getType()){
			case Android:
				label.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()));
				break;
				//if using the desktop set the width and height to a 16:9 resolution.
			case Desktop:
				label.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*5));
				break;
			case iOS:
				label.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()));
				break;
			default:
				label.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*5));
				break;
			}
			checkbox.setSize(0.2f*Gdx.graphics.getWidth(), 0.2f*Gdx.graphics.getHeight());
			checkbox.setScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*5));
			checkbox.addListener(new MyChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					super.changed(event, actor);
				}
			});
			choicesTable.add(checkbox).width(Gdx.graphics.getWidth()/4).height(Gdx.graphics.getHeight()/4);
			choicesTable.add(label);
			options.add(checkbox);
		}
	}
	
	/**
	 * Creates the slider for the volume so the level can be controlled.
	 * <p>
	 * Also creates a check box for the mute button
	 */
	 private void createSlider() {
		 sliderTable = new Table();
		 sliderSkin = new Skin();
		 muteStyle = new CheckBoxStyle();
		 font = game.getBitmapFontButton();
		 sliderSkin.addRegions((TextureAtlas) manager.get("Packs/Slider.pack"));
		 SliderStyle ss = new SliderStyle();
		 ss.background = sliderSkin.getDrawable("Slider_After");
		 ss.knobBefore = sliderSkin.getDrawable("Slider_Before");
		 ss.knobAfter = sliderSkin.getDrawable("Slider_After");
		 ss.knob = sliderSkin.getDrawable("Knob");
		 LabelStyle ls = new LabelStyle();
		 ls.font = font;
		 muteStyle.font = font;
		 muteStyle.checkboxOff = sliderSkin.getDrawable("Volume_On");
		 muteStyle.checkboxOn = sliderSkin.getDrawable("Mute");
		 mute = new CheckBox(" ", muteStyle);
		 mute.addListener(new MyChangeListener() {
			 @Override
			 public void changed(ChangeEvent event, Actor actor) {
				 super.changed(event, actor);
				 if(slider.getValue() != 0)
					 volume = slider.getValue();
				 if(mute.isChecked()) {
					 slider.setValue(0);
					 ScreenAdapterManager.getInstance().game.volume = slider.getValue()/100f;
					 slider.setDisabled(true);
					 game.muteAll = true;
				 }
				 else {
					 slider.setValue(volume);
					 slider.setDisabled(false);
					 game.muteAll = false;
				 }
			 }
		 });
		 slider = new Slider(0, 100, 1, false, ss);
		 slider.setValue(75);
		 slider.setDisabled(false);
		 ScreenAdapterManager.getInstance().game.volume = slider.getValue()/100f;
		 slider.addListener(new MyChangeListener() {
			 @Override
			 public void changed(ChangeEvent event, Actor actor) {
				 float value = ((Slider) actor).getValue();
				 ScreenAdapterManager.getInstance().game.volume = value/100f;
			 }
		 });
		 sliderName = new Label("Volume  ", ls);switch(Gdx.app.getType()){
			case Android:
				sliderName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()));
				break;
				//if using the desktop set the width and height to a 16:9 resolution.
			case Desktop:
				sliderName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*5));
				break;
			case iOS:
				sliderName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()));
				break;
			default:
				sliderName.setFontScale(Gdx.graphics.getWidth()/(Gdx.graphics.getPpiX()*5));
				break;
			}
		 sliderTable.add(sliderName).height(Gdx.graphics.getHeight()/3);
		 sliderTable.add(slider).width(600).height(Gdx.graphics.getHeight()/3);
		 sliderTable.add(mute).height(Gdx.graphics.getHeight()/3);
	 }
	 
	 /**
	  * Creates the table that will hold the sound and music table and the volume
	  * slider and adds and aligns them properly. 
	  */
	 private void createOverallTable() {
		 overallTable = new Table();
		 createChoices();
		 createSlider();
		 overallTable.add(choicesTable);
		 overallTable.row();
		 overallTable.add(sliderTable);
		 overallTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		 overallTable.align(Align.center);
	 }
	 
	 /**
	  * Getter for the music check box
	  * @return Returns whether or not the music box is checked
	  */
	 public boolean getMusic() {
		 return options.get(0).isChecked();
	 }
	 
	 /**
	  * Getter for the sound check box
	  * @return Returns whether or not the sound box is checked
	  */
	 public boolean getSound() {
		 return options.get(1).isChecked();
	 }
	 
	 /**
	  * Getter for the mute button
	  * @return Returns whether or not the mute button is selected
	  */
	 public boolean getMute() {
		 return mute.isChecked();
	 }
	 
	 /**
	  * Getter for the volume slider level
	  * @return Returns the value of the volume slider.
	  */
	 public float getVolume() {
		 return slider.getValue();
	 }
}
