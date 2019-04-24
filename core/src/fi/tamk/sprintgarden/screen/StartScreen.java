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
 * Screen that has buttons between screens. One for starting the game one for options and one for
 * credits.
 */

public class StartScreen implements Screen {
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
     * Fonts are stored in this object
     */
    private Fonts fonts;
    /**
     * GameObjects are drawn on stage
     */
    private Stage stage;
    /**
     * Screen where game is played.
     */
    private GameScreen gameScreen;

    /**
     * Constructor for StartScreen
     * @param game used to make reference to MainGame
     */
    public StartScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }
    /**
     * Method which is called when screen is shown. In this method create references to variables.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = game.getAssetManager().get("startscreen.png");
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();
        fonts.createTitleFont();

        createButtons();
    }
    /**
     * Method which is called everytime frame is rendered.
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

        stage.getViewport().apply();
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
        fonts.disposeFonts();
    }

    /**
     * Creates the 3 buttons for the different screens depending on language.
     */
    private void createButtons() {
        Texture playButtonIdle, playButtonPressed,settingsButtonIdle, settingsButtonPressed, creditsButtonIdle, creditsButtonPressed;

        if(game.getLocale().getCountry().equals("FI")){
            playButtonIdle = game.getAssetManager().get("BUTTONS/button_startgame_FIN.png");
            playButtonPressed = game.getAssetManager().get("BUTTONS/button_startgame_FIN_PRESSED.png");

            settingsButtonIdle = game.getAssetManager().get("BUTTONS/button_startsettings_FIN.png");
            settingsButtonPressed = game.getAssetManager().get("BUTTONS/button_startsettings_FIN_PRESSED.png");

            creditsButtonIdle = game.getAssetManager().get("BUTTONS/button_credits_FIN.png");
            creditsButtonPressed = game.getAssetManager().get("BUTTONS/button_credits_FIN_PRESSED.png");
        }else{
            playButtonIdle = game.getAssetManager().get("BUTTONS/button_startgame_ENG.png");
            playButtonPressed = game.getAssetManager().get("BUTTONS/button_startgame_ENG_PRESSED.png");

            settingsButtonIdle = game.getAssetManager().get("BUTTONS/button_startsettings_ENG.png");
            settingsButtonPressed = game.getAssetManager().get("BUTTONS/button_startsettings_ENG_PRESSED.png");

            creditsButtonIdle = game.getAssetManager().get("BUTTONS/button_credits_ENG.png");
            creditsButtonPressed = game.getAssetManager().get("BUTTONS/button_credits_ENG_PRESSED.png");
        }

        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playButtonIdle)), new TextureRegionDrawable(new TextureRegion(playButtonPressed)));

        playButton.setPosition(95, 12);
        stage.addActor(playButton);

        playButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });

        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsButtonIdle)), new TextureRegionDrawable(new TextureRegion(settingsButtonPressed)));

        settingsButton.setPosition(175, 12);
        stage.addActor(settingsButton);

        settingsButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        ImageButton creditsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(creditsButtonIdle)), new TextureRegionDrawable(new TextureRegion(creditsButtonPressed)));

        creditsButton.setPosition(15, 12);
        stage.addActor(creditsButton);

        creditsButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditsScreen(game));
            }
        });
    }
}
