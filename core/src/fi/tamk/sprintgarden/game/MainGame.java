package fi.tamk.sprintgarden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import fi.tamk.sprintgarden.screen.GameScreen;
import fi.tamk.sprintgarden.screen.OptionsScreen;
import fi.tamk.sprintgarden.screen.StartScreen;
import fi.tamk.sprintgarden.util.GetSteps;


public class MainGame extends Game{

	private SpriteBatch batch;

	public final int SCREEN_WIDTH = 256;
	public final int SCREEN_HEIGHT = 144;

    private GameScreen gameScreen;
    private OptionsScreen optionsScreen;
    private StartScreen startScreen;

    private Screen lastScreen;

    private int stepCount; // renderissä
    private int oldStepCount;
    private int coins;

    private int fastPlantTier = 1;
    private int mediumPlantTier = 1;
    private int slowPlantTier = 1;

    private int currentPlantingSpaceAmount = 2;
    private int maxPlantingSpaceAmount = 8;

    private AssetManager assetManager;

    public void toJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("save.json");
        json.setOutputType(JsonWriter.OutputType.json);
        int[] saveData = {getStepCount(), getOldStepCount(), getCoins(), getFastPlantTier(), getMediumPlantTier(), getSlowPlantTier(), getCurrentPlantingSpaceAmount(), getMaxPlantingSpaceAmount()};
        file.writeString(json.prettyPrint(saveData),false);
    }

    public void fromJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("save.json");

        if(file.exists()){
            int[] saveData = new int[8];
            saveData = json.fromJson(int[].class, file);
            setStepCount(saveData[0]);
            setOldStepCount(saveData[1]);
            setCoins(saveData[2]);
            setFastPlantTier(saveData[3]);
            setMediumPlantTier(saveData[4]);
            setSlowPlantTier(saveData[5]);
            setCurrentPlantingSpaceAmount(saveData[6]);
            maxPlantingSpaceAmount = saveData[7];
        }
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public OptionsScreen getOptionsScreen() {
        return optionsScreen;
    }

    public StartScreen getStartScreen() {
        return startScreen;
    }

    public Screen getLastScreen() {
        return lastScreen;
    }

    public void setLastScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
    }

    public void receiveSteps(int stepCount) {
        this.setStepCount(this.getStepCount() + stepCount);
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        gameScreen = new GameScreen(this);
        optionsScreen = new OptionsScreen(this);
        startScreen = new StartScreen(this);
        FileHandle file = Gdx.files.local("save.json");
        FileHandle file1 = Gdx.files.local("gameState.json");

        setupAssetManager();

        fromJson();

        if(!file.exists() & !file1.exists()) {
            lastScreen = getStartScreen();
            setScreen(getStartScreen());
        }else{
            lastScreen = getGameScreen();
            setScreen(getGameScreen());
        }
    }

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    public int getStepCount() {
        if(stepGetter!= null){
            stepCount += stepGetter.getNumSteps();
        }
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getOldStepCount() {
        return oldStepCount;
    }

    public void setOldStepCount(int oldStepCount) {
        this.oldStepCount = oldStepCount;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getFastPlantTier() {
        return fastPlantTier;
    }

    public void setFastPlantTier(int fastPlantTier) {
        this.fastPlantTier = fastPlantTier;
    }

    public int getMediumPlantTier() {
        return mediumPlantTier;
    }

    public void setMediumPlantTier(int mediumPlantTier) {
        this.mediumPlantTier = mediumPlantTier;
    }

    public int getSlowPlantTier() {
        return slowPlantTier;
    }

    public void setSlowPlantTier(int slowPlantTier) {
        this.slowPlantTier = slowPlantTier;
    }

    public int getCurrentPlantingSpaceAmount() {
        return currentPlantingSpaceAmount;
    }

    public void setCurrentPlantingSpaceAmount(int currentPlantingSpaceAmount) {
        this.currentPlantingSpaceAmount = currentPlantingSpaceAmount;
    }

    public int getMaxPlantingSpaceAmount() {
        return maxPlantingSpaceAmount;
    }

    private GetSteps stepGetter;

    public void setGetSteps(GetSteps sg){
        stepGetter = sg;
    }

    public void setupAssetManager(){
        assetManager = new AssetManager();
        assetManager.load("startscreen.png", Texture.class);
        assetManager.load("gamecanvas.png", Texture.class);
        assetManager.load("background_plantmenu.png", Texture.class);
        assetManager.load("background_store.png", Texture.class);
        assetManager.load("flowerbed_shadow_bot-right.png", Texture.class);
        assetManager.load("marketplace.png", Texture.class);
        assetManager.load("settings.png", Texture.class);

        assetManager.load("BUTTONS/button_buy_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_buy_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_buy_PRESSED_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_buy_PRESSED_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_close.png", Texture.class);
        assetManager.load("BUTTONS/button_close_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_ENG_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_FIN_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_market.png", Texture.class);
        assetManager.load("BUTTONS/button_market_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_minus.png", Texture.class);
        assetManager.load("BUTTONS/button_minus_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_plus.png", Texture.class);
        assetManager.load("BUTTONS/button_plus_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_selectplant_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_selectplant_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_selectplant_PRESSED_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_selectplant_PRESSED_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_settings.png", Texture.class);
        assetManager.load("BUTTONS/button_settings_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_startgame_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_startgame_ENG_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_startgame_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_startgame_FIN_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_startsettings_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_startsettings_ENG_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_startsettings_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_startsettings_FIN_PRESSED.png", Texture.class);

        assetManager.load("plants/slowPlant/plant3_stage3_tier2.png", Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage3_tier3.png", Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage2_tier1.png", Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage2_tier3.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage1.png", Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage3_tier1.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage2_tier3.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage3_tier3.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage3_tier1.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage2_tier3.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage2_tier2.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage3_tier2.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage2_tier1.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage1.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage3_tier1.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage3_tier2.png", Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage2_tier2.png", Texture.class);
        assetManager.load("plants/fastPlant/plant1_stage2_tier2.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage3_tier3.png", Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage2_tier1.png", Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage1.png", Texture.class);
        assetManager.finishLoading();
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }
}