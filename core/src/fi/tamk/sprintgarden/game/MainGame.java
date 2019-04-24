package fi.tamk.sprintgarden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.Locale;

import fi.tamk.sprintgarden.screen.GameScreen;
import fi.tamk.sprintgarden.screen.SplashScreen;
import fi.tamk.sprintgarden.screen.StartScreen;
import fi.tamk.sprintgarden.util.GetSteps;

/**
 * This game is based on pedometer that tracks your steps as your plants grow in your garden, you gain
 * gold as you harvest your plants and use your coins to improve your garden.
 *
 * This class handles variables useful for the game and saving and loading them when game is closed and
 * opened. Assets are handled in this class as is Localization for the finnish and english.
 *
 * @author Pasi Mäkitalo
 * @version 1.0
 * @since 22-04-2019
 */
public class MainGame extends Game{
    /**
     * Used to draw every pixel in game.
     */
	private SpriteBatch batch;
    /**
     * Resolution width for stage and background.
     */

	public final int SCREEN_WIDTH = 256;
    /**
     * Resolution height for stage and background.
     */
	public final int SCREEN_HEIGHT = 144;
    /**
     * Goal to reach to get golden plantingspace borders.
     */

	public final int GOALSTEPS = 100000;
    /**
     * Screen where garden is and most of the game happen.
     */

    private GameScreen gameScreen;
    /**
     * Screen where you may go to settings, start the game or check out CreditsScreen.
     */
    private StartScreen startScreen;
    /**
     * Screen which is animation of the team's logo.
     */
    private SplashScreen splashScreen;

    /**
     * Tracks screen switches and holds the last screen is player wants to back out from current one.
     */
    private Screen lastScreen;

    /**
     * Current amount of steps made in game.
     */
    private int stepCount;
    /**
     * Variable to check last renders stepCount to add to difference of stepCount and oldStepCount to
     * growing plants.
     */
    private int oldStepCount;
    /**
     * Gold amount in game.
     */
    private int coins = 10;

    /**
     * Current tier of fastPlant
     */
    private int fastPlantTier = 1;
    /**
     * Current tier of mediumPlant
     */
    private int mediumPlantTier = 1;
    /**
     * Current tier of slowPlant
     */
    private int slowPlantTier = 1;

    /**
     * Amount of plantingSpaces player has.
     */
    private int currentPlantingSpaceAmount = 0;
    /**
     * Maximum amount of plantingSpaces.
     */
    private int maxPlantingSpaceAmount = 8;
    /**
     * Tracks if player has reached 100000 steps.
     */
    private boolean goalReached;
    /**
     * Tracks if player has done tutorial.
     */
    private boolean tutorialDone;

    /**
     * Holds all the game assets.
     */
    private AssetManager assetManager;
    /**
     * Background music.
     */
    private Music musicBg;
    /**
     * Sound volume for effects.
     */
    private float effVolume = 0.3f;
    /**
     * Sound volume for music.
     */
    private float musicVolume = 0.1f;
    /**
     * Current localization.
     */
    private Locale locale;
    /**
     * Handles which localization is used.
     */
    private I18NBundle myBundle;

    /**
     * Create method in MainGame handles assetManager, localization and saving/loading. Also instantiates
     * useful variables for other classes. Also checks if its players first time playing, if so it goes
     * into SplashScreen.
     */
    @Override
	public void create () {
		batch = new SpriteBatch();
        gameScreen = new GameScreen(this);
        startScreen = new StartScreen(this);
        splashScreen = new SplashScreen(this);

        FileHandle file = Gdx.files.local("save.json");
        FileHandle file1 = Gdx.files.local("gameState.json");
        fromJson();
        setupAssetManager();
        setupLocalization();

        musicBg = getAssetManager().get("Sounds/music.mp3");
        musicBg.play();

        if(!file.exists() & !file1.exists()) {
            setScreen(splashScreen);
        }else{
            lastScreen = getGameScreen();
            setScreen(getGameScreen());
        }
    }

    /**
     * Plays music in background of the game in loop.
     */
	@Override
	public void render () {
        musicBg.setVolume(musicVolume);
        stepCount++;
        super.render();
	}

    /**
     * Disposes assets.
     */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    /**
     * Saves game data in JSON file. Saves stepCount, oldStepCount, coins, fastPlantTier,
     * mediumPlantTier, slowPlantTier, currentPlantingSpaceAmount, maxPlantingSpaceAmount,
     * goalReached and tutorialDone.
     *
     * Saves also options in preferences of language to use and volume levels for effects and music.
     */
    public void toJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("save.json");
        json.setOutputType(JsonWriter.OutputType.json);
        int goalReachedInt;
        int tutorialDoneInt;
        if(isGoalReached()){
            goalReachedInt = 1;
        }else{
            goalReachedInt = 0;
        }

        if(isTutorialDone()){
            tutorialDoneInt = 1;
        }else{
            tutorialDoneInt = 0;
        }
        int[] saveData = {getStepCount(), getOldStepCount(), getCoins(), getFastPlantTier(),
                getMediumPlantTier(), getSlowPlantTier(), getCurrentPlantingSpaceAmount(),
                getMaxPlantingSpaceAmount(), goalReachedInt, tutorialDoneInt};
        file.writeString(json.prettyPrint(saveData),false);

        Preferences prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.putFloat("effVolume", effVolume);
        prefs.putFloat("musicVolume", musicVolume);
        prefs.putString("language", myBundle.getLocale().toString());
        prefs.flush();
    }

    /**
     * Loads from JSON file. Loads stepCount, oldStepCount, coins, fastPlantTier,
     * mediumPlantTier, slowPlantTier, currentPlantingSpaceAmount, maxPlantingSpaceAmount,
     * goalReached and tutorialDone.
     *
     * Loads also saved options from preferences.
     */
    private void fromJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("save.json");
        Preferences prefs = Gdx.app.getPreferences("MyPreferences");

        if(file.exists()){
            setEffVolume(prefs.getFloat("effVolume"));
            setMusicVolume(prefs.getFloat("musicVolume"));
            if(prefs.getString("language").equals("fi_FI")){
                setLocale(new Locale("fi", "FI"));
            }else{
                setLocale(new Locale("en", "UK"));
            }

            int[] saveData;
            saveData = json.fromJson(int[].class, file);
            setStepCount(saveData[0]);
            setOldStepCount(saveData[1]);
            setCoins(saveData[2]);
            setFastPlantTier(saveData[3]);
            setMediumPlantTier(saveData[4]);
            setSlowPlantTier(saveData[5]);
            setCurrentPlantingSpaceAmount(saveData[6]);
            maxPlantingSpaceAmount = saveData[7];
            if(saveData[8] == 1){
                setGoalReached(true);
            }else{
                setGoalReached(false);
            }
            if(saveData[9] == 1){
                setTutorialDone(true);
            }else{
                setTutorialDone(false);
            }

        }else{
            setLocale(Locale.getDefault());
        }
    }

    /**
     * Getter for spriteBatch
     * @return returns spriteBatch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Getter for gameScreen
     * @return returns gameScreen
     */
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Getter for startScreen
     * @return returns startScreen
     */
    public StartScreen getStartScreen() {
        return startScreen;
    }

    /**
     * Getter for lastScreen
     * @return returns lastScreen
     */
    public Screen getLastScreen() {
        return lastScreen;
    }

    /**
     * Setter for lastScreen
     * @param lastScreen Previous screen
     */
    public void setLastScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
    }

    /**
     * GetSteps interface method that gets steps from MyService.
     * @return returns stepCount
     */
    public int getStepCount() {
        if(stepGetter!= null){
            stepCount += stepGetter.getNumSteps();
        }
        return stepCount;
    }

    /**
     * Setter for stepCount
     * @param stepCount amount of steps to set
     */
    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    /**
     * Getter for oldStepCount
     * @return returns oldStepCount
     */
    public int getOldStepCount() {
        return oldStepCount;
    }

    /**
     * Setter for oldStepCount
     * @param oldStepCount sets oldStepCount
     */
    public void setOldStepCount(int oldStepCount) {
        this.oldStepCount = oldStepCount;
    }

    /**
     * Getter for coins
     * @return returns coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Setter for coins
     * @param coins amount of coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * Getter for fastPlantTier
     * @return returns fastPlantTier
     */
    public int getFastPlantTier() {
        return fastPlantTier;
    }

    /**
     * Setter for fastPlantTier
     * @param fastPlantTier sets tier for fastPlant
     */
    public void setFastPlantTier(int fastPlantTier) {
        this.fastPlantTier = fastPlantTier;
    }

    /**
     * Getter for mediumPlantTier
     * @return return mediumPlantTier
     */
    public int getMediumPlantTier() {
        return mediumPlantTier;
    }

    /**
     * Setter for mediumPlantTier
     * @param mediumPlantTier sets tier for mediumPlant
     */
    public void setMediumPlantTier(int mediumPlantTier) {
        this.mediumPlantTier = mediumPlantTier;
    }

    /**
     * Getter for slowPlantTier
     * @return returns slowPlantTier
     */
    public int getSlowPlantTier() {
        return slowPlantTier;
    }

    /**
     * Setter for slowPlantTier
     * @param slowPlantTier sets tier for slowPlant
     */
    public void setSlowPlantTier(int slowPlantTier) {
        this.slowPlantTier = slowPlantTier;
    }

    /**
     * Getter for currentPlantingSpaceAmount
     * @return returns currentPlantingSpaceAmount
     */
    public int getCurrentPlantingSpaceAmount() {
        return currentPlantingSpaceAmount;
    }

    /**
     * Setter for currentPlantingSpaceAmount
     * @param currentPlantingSpaceAmount sets currentPlantingSpaceAmount
     */
    public void setCurrentPlantingSpaceAmount(int currentPlantingSpaceAmount) {
        this.currentPlantingSpaceAmount = currentPlantingSpaceAmount;
    }

    /**
     * Getter for maxPlantingSpaceAmount
     * @return returns maxPlantingSpaceAmount
     */
    public int getMaxPlantingSpaceAmount() {
        return maxPlantingSpaceAmount;
    }

    /**
     * Getter for locale
     * @return returns locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Setter for locale
     * @param locale sets locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Getter for effVolume
     * @return returns effVolume
     */
    public float getEffVolume() {
        return effVolume;
    }

    /**
     * Setter for effVolume
     * @param effVolume sets effVolume
     */
    public void setEffVolume(float effVolume) {
        this.effVolume = effVolume;
    }

    /**
     * Getter for musicVolume
     * @return returns musicVolume
     */
    public float getMusicVolume() {
        return musicVolume;
    }

    /**
     * Setter for musicVolume
     * @param musicVolume sets musicVolume
     */
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    /**
     * getter for goalReached
     * @return returns goalReached
     */
    public boolean isGoalReached() {
        return goalReached;
    }

    /**
     * Setter for goalReached
     * @param goalReached sets goalReached
     */
    public void setGoalReached(boolean goalReached) {
        this.goalReached = goalReached;
    }

    /**
     * Getter for tutorialDone
     * @return returns tutorialDone
     */
    public boolean isTutorialDone() {
        return tutorialDone;
    }

    /**
     * Setter for tutorialDone
     * @param tutorialDone sets tutorialDone
     */
    public void setTutorialDone(boolean tutorialDone) {
        this.tutorialDone = tutorialDone;
    }

    /**
     * Variable to get steps from MyServices
     */
    private GetSteps stepGetter;

    /**
     * Setter for stepGetter
     * @param sg sets stepGetter
     */
    public void setGetSteps(GetSteps sg){
        stepGetter = sg;
    }

    /**
     * Sets all assets for assetManager.
     */
    private void setupAssetManager(){
        assetManager = new AssetManager();
        assetManager.load("startscreen.png", Texture.class);
        assetManager.load("gamecanvas.png", Texture.class);
        assetManager.load("background_plantmenu.png", Texture.class);
        assetManager.load("background_store.png", Texture.class);
        assetManager.load("flowerbed_shadow_bot-right.png", Texture.class);
        assetManager.load("flowerbed_shadow_bot-right_PLANTED.png", Texture.class);
        assetManager.load("marketplace.png", Texture.class);
        assetManager.load("settings.png", Texture.class);
        assetManager.load("arrow_down.png", Texture.class);
        assetManager.load("arrow_left.png", Texture.class);
        assetManager.load("arrow_right.png", Texture.class);
        assetManager.load("arrow_up.png", Texture.class);
        assetManager.load("splashSpriteSheet.png", Texture.class);
        assetManager.load("Sprite-0002.png", Texture.class);
        assetManager.load("credits_ENG.png", Texture.class);
        assetManager.load("credits_FIN.png", Texture.class);

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
        assetManager.load("BUTTONS/button_OK.png", Texture.class);
        assetManager.load("BUTTONS/button_OK_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_ENG.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_ENG_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_FIN.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_FIN_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_FIN_DARK_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_FIN_DARK.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_ENG_DARK_PRESSED.png", Texture.class);
        assetManager.load("BUTTONS/button_credits_ENG_DARK.png", Texture.class);

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
        assetManager.load("plants/fastPlant/plant1_stage0.png",Texture.class);
        assetManager.load("plants/mediumPlant/plant2_stage0.png",Texture.class);
        assetManager.load("plants/slowPlant/plant3_stage0.png",Texture.class);
        assetManager.load("flowerbed_planted_GOLD.png", Texture.class);
        assetManager.load("flowerbed_GOLD.png", Texture.class);
        assetManager.load("tiertrophy.png", Texture.class);

        assetManager.load("Sounds/coins.mp3",Sound.class);
        assetManager.load("Sounds/dig.mp3",Sound.class);
        assetManager.load("Sounds/music.mp3", Music.class);
        assetManager.load("Sounds/bought.mp3",Sound.class);

        assetManager.finishLoading();
    }

    /**
     * Getter for assetManager
     * @return returns assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Setter for myBundle
     */
    public void setupLocalization(){
        myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
    }

    /**
     * Getter for myBundle
     * @return returns myBundle
     */
    public I18NBundle getLocalization(){
        return myBundle;
    }
}