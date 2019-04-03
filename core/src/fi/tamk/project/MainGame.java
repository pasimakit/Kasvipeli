package fi.tamk.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;


public class MainGame extends Game {
	SpriteBatch batch;

	final int SCREEN_WIDTH = 256;
	final int SCREEN_HEIGHT = 144;

	OrthographicCamera camera;
    GameScreen gameScreen;

    int stepCount; // renderissä
    int oldStepCount;
    int coins;

    int fastPlantTier = 1;
    int mediumPlantTier = 1;
    int slowPlantTier = 1;

    int currentPlantingSpaceAmount = 2;
    int maxPlantingSpaceAmount = 8;

    public void toJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("test.json");
        json.setOutputType(JsonWriter.OutputType.json);
        int[] saveData = {stepCount, oldStepCount, coins, fastPlantTier, mediumPlantTier, slowPlantTier, currentPlantingSpaceAmount, maxPlantingSpaceAmount};
        file.writeString(json.prettyPrint(saveData),false);
        System.out.println(saveData.getClass());
    }

    public void fromJson(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("test.json");

        if(file.exists()){
            int[] saveData = new int[8];
            saveData = json.fromJson(int[].class, file);
            stepCount = saveData[0];
            oldStepCount = saveData[1];
            coins = saveData[2];
            fastPlantTier = saveData[3];
            mediumPlantTier = saveData[4];
            slowPlantTier = saveData[5];
            currentPlantingSpaceAmount = saveData[6];
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
        this.stepCount = stepCount;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        gameScreen = new GameScreen(this);
        fromJson();
        setScreen(gameScreen);
    }

	@Override
	public void render () {
        stepCount++;
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
