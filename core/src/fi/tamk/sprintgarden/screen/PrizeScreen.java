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
import fi.tamk.sprintgarden.util.Fonts;

/**
 * Screen that is displayed when player reaches GOALSTEPS.
 */

public class PrizeScreen implements Screen {
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
     * Trophy image for background.
     */
    private Texture trophy;
    /**
     * Viewport for background image.
     */
    private Viewport bgViewPort;
    /**
     * Fonts are stored in this object
     */
    private Fonts fonts;
    /**
     * GameObjects are drawn on stage
     */
    private Stage stage;

    /**
     * Constructor for PrizeScreen.
     * @param game used to make reference to MainGame
     */
    public PrizeScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
    }
    /**
     * Method which is called when screen is shown. In this method create references to variables.
     * When screen is shown goal is reached.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = game.getAssetManager().get("background_store.png");
        trophy = game.getAssetManager().get("tiertrophy.png");

        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createCongratsFont();
        fonts.createLargeFont();
        fonts.createTitleFont();

        game.setGoalReached(true);

        createButtons();
    }
    /**
     * Method which is called everytime frame is rendered. Display congratulations message.
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
        batch.draw(trophy, -10, 80);
        batch.draw(trophy, game.SCREEN_WIDTH-50, 80);
        batch.end();

        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        if(game.getLocale().getCountry().equals("FI")){
            fonts.getCongratsFont().draw(batch,""+game.getLocalization().get("congrats"), 480, 900 );
        }else{
            fonts.getCongratsFont().draw(batch,""+game.getLocalization().get("congrats"), 250, 900 );
        }

        fonts.getTitleFont().draw(batch, ""+game.getLocalization().format("goalSentence", game.GOALSTEPS), 80, 650);
        fonts.getTitleFont().draw(batch, ""+game.getLocalization().get("thankSentence"), 80, 570);
        fonts.getTitleFont().draw(batch, ""+game.getLocalization().get("gardenSentence"), 80, 400);
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
        fonts.getFontViewport().update(width, height, true);
    }
    /**
     * Method that is called when game is paused. Game is saved.
     */
    @Override
    public void pause() {
        game.toJson();
    }
    /**
     * Method that is called when game is resumed.
     */
    @Override
    public void resume() {

    }
    /**
     * Method that is called when game is hidden. Game is saved.
     */
    @Override
    public void hide() {
        game.toJson();
    }
    /**
     * Disposes things.
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }

    /**
     * Creates ok button to go back to previous screen.
     */
    private void createButtons() {
        Texture closeButtonIdle = game.getAssetManager().get("BUTTONS/button_OK.png");
        Texture closeButtonPressed = game.getAssetManager().get("BUTTONS/button_OK_PRESSED.png");

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)), new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH/2 -16, 15);
        stage.addActor(closeButton);

        closeButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getLastScreen());
            }
        });
    }
}
