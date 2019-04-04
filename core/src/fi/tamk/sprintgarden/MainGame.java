package fi.tamk.sprintgarden;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;


public class MainGame extends Game {

	private SpriteBatch batch;

	public final int SCREEN_WIDTH = 256;
	public final int SCREEN_HEIGHT = 144;

    private GameScreen gameScreen;

    private int stepCount; // renderissä
    private int oldStepCount;
    private int coins;

    private int fastPlantTier = 1;
    private int mediumPlantTier = 1;
    private int slowPlantTier = 1;

    private int currentPlantingSpaceAmount = 2;
    private int maxPlantingSpaceAmount = 8;

    public void toJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("test.json");
        json.setOutputType(JsonWriter.OutputType.json);
        int[] saveData = {getStepCount(), getOldStepCount(), getCoins(), getFastPlantTier(), getMediumPlantTier(), getSlowPlantTier(), getCurrentPlantingSpaceAmount(), getMaxPlantingSpaceAmount()};
        file.writeString(json.prettyPrint(saveData),false);
    }

    public void fromJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("test.json");

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

    public void receiveSteps(int stepCount) {
        this.setStepCount(this.getStepCount() + stepCount);
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        gameScreen = new GameScreen(this);
        fromJson();
        setScreen(getGameScreen());
    }

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    public int getStepCount() {
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
}