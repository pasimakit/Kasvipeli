package fi.tamk.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MainGame extends Game {
	SpriteBatch batch;

	final int SCREEN_WIDTH = 384;
	final int SCREEN_HEIGHT = 216;

	OrthographicCamera camera;
    GameScreen gameScreen;
    ChoosePlantScreen choosePlantScreen;

    public int stepCount;

    public SpriteBatch getBatch() {
        return batch;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public ChoosePlantScreen getChoosePlantScreen() {
        return choosePlantScreen;
    }

    public void receiveSteps(int stepCount) {
        System.out.println("Steps: " + stepCount);
        this.stepCount = stepCount;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        gameScreen = new GameScreen(this);
        choosePlantScreen = new ChoosePlantScreen(this);

        setScreen(gameScreen);
        //setScreen(choosePlantScreen);
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
