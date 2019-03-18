package fi.tamk.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class MainGame extends Game {
	SpriteBatch batch;

	final int SCREEN_WIDTH = 384;
	final int SCREEN_HEIGHT = 216;

	OrthographicCamera camera;
    GameScreen gameScreen;

    BitmapFont font14;
    BitmapFont font8;

    int stepCount; // renderissä
    int oldStepCount;

    int coins;

    public SpriteBatch getBatch() {
        return batch;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }


    public void receiveSteps(int stepCount) {
        //System.out.println("Steps: " + stepCount);
        this.stepCount = stepCount;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);

        //fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        font14 = generator.generateFont(parameter);
        parameter.size = 8;
        font8 = generator.generateFont(parameter);
        generator.dispose();

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
