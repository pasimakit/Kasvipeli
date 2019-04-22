package fi.tamk.sprintgarden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fi.tamk.sprintgarden.game.MainGame;

/**
 * Screen that shows the creators of the game.
 */
public class CreditsScreen implements Screen {
    /**
     * Used to draw every pixel in game.
     */
    private SpriteBatch batch;
    /**
     * Reference to MainGame.
     */
    final MainGame game;
    /**
     * Background image.
     */
    private Texture background;
    /**
     * Viewport for background image.
     */
    private Viewport bgViewPort;
    /**
     * GameObjects are drawn on stage
     */
    private Stage stage;

    /**
     * Constructor for CreditsScreen. Reference to MainGame and SpriteBatch
     * @param game used to make reference to MainGame
     */
    public CreditsScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
    }
    /**
     * Method which is called when screen is shown. In this method create references to variables,
     * and create objects and place them in correct places.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);
        if(game.getLocale().getCountry().equals("FI")){
            background = game.getAssetManager().get("credits_FIN.png");
        }else{
            background = game.getAssetManager().get("credits_ENG.png");
        }
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
        createButtons();
    }
    /**
     * Method which is called everytime frame is rendered. Draw background.
     * @param delta deltaTime
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        stage.draw();
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
     * Method that is called when game is resumed.
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
        stage.dispose();
        batch.dispose();
    }
    /**
     * Creates close button.
     */
    private void createButtons() {
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)), new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH - 25, game.SCREEN_HEIGHT - 25);
        stage.addActor(closeButton);

        closeButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StartScreen(game));
            }
        });
    }
}
