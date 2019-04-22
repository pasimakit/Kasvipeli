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

/**
 * Screen that displays animation of our team logo.
 */
public class SplashScreen implements Screen {
    /**
     * Used to draw every pixel in game.
     */
    private SpriteBatch batch;
    /**
     * Reference to MainGame.
     */
    final MainGame game;
    /**
     * Viewport for background image.
     */
    private Viewport bgViewPort;
    /**
     * GameObjects are drawn on stage.
     */
    private Stage stage;
    /**
     * How many frames are on the spritesheet column .
     */
    final int FRAME_COLS = 40;
    /**
     * How many frames are on the spritesheet row.
     */
    final int FRAME_ROWS = 1;
    /**
     * Animation that is constructed from the spritesheet.
     */
    Animation<TextureRegion> splashScreenAnimation;
    /**
     * Spritesheet that is going to be animated.
     */
    Texture splashSheet;
    /**
     * Variable for animation to show correct frame
     */
    float stateTime;

    /**
     * Construct for SplashScreen.
     * @param game used to make reference to MainGame
     */
    public SplashScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
    }
    /**
     * Method which is called when screen is shown. In this method create references to variables.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        splashSheet = game.getAssetManager().get("splashSpriteSheet.png");
        createSplashAnimation();
    }
    /**
     * Method which is called everytime frame is rendered. Display splash animation.
     * @param delta deltaTime
     */
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
    /**
     * Method that is called when screen is resized. Updates different viewports.
     * @param width width after resizing
     * @param height height after resizing
     */
    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }
    /**
     * Method that is called when game is paused.
     */
    @Override
    public void pause() {

    }
    /**
     * Method that is called when game is hidden.
     */
    @Override
    public void resume() {

    }
    /**
     * Method that is called when game is hidden.
     */
    @Override
    public void hide() {

    }
    /**
     * Disposes things.
     */
    @Override
    public void dispose() {

    }

    /**
     * Creates the animation from the spritesheet.
     */
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
