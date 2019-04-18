package fi.tamk.sprintgarden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fi.tamk.sprintgarden.game.MainGame;

public class SplashScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Viewport bgViewPort;

    private Stage stage;

    final int FRAME_COLS = 40, FRAME_ROWS = 1;
    Animation<TextureRegion> splashScreenAnimation;
    Texture splashSheet;
    float stateTime;

    public SplashScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
    }


    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        splashSheet = game.getAssetManager().get("splashSpriteSheet.png");
        createSplashAnimation();
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = splashScreenAnimation.getKeyFrame(stateTime, false);
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(currentFrame, 0, 0);
        batch.end();

        if(currentFrame == splashScreenAnimation.getKeyFrame(40)){
            game.setLastScreen(game.getStartScreen());
            game.setScreen(game.getStartScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void createSplashAnimation(){
        TextureRegion[][] tmp = TextureRegion.split(splashSheet,
                splashSheet.getWidth() / FRAME_COLS,
                splashSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] splashFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                splashFrames[index++] = tmp[i][j];
            }
        }

        splashScreenAnimation = new Animation<TextureRegion>(0.1f, splashFrames);
    }
}
