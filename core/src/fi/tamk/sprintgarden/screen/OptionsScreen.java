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

import java.util.Locale;

import fi.tamk.sprintgarden.util.Fonts;
import fi.tamk.sprintgarden.game.MainGame;

/**
 * Screen where you can choose volume and language.
 */
public class OptionsScreen implements Screen {
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
     * Constuctor for OptionsScreen
     * @param game used to make reference to MainGame
     */
    public OptionsScreen(MainGame game){
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

        background = game.getAssetManager().get("settings.png");
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
     * Method which is called everytime frame is rendered. Guidance for players what buttons do.
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

        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getTitleFont().draw(batch, ""+game.getLocalization().get("settings"), 150, 950);
        fonts.getTitleFont().draw(batch, ""+game.getLocalization().get("effVolume"), 270, 693);
        fonts.getTitleFont().draw(batch, ""+(int)(game.getEffVolume()*100), 1260, 693);
        fonts.getTitleFont().draw(batch, ""+game.getLocalization().get("musicVolume"), 270,493);
        fonts.getTitleFont().draw(batch, ""+(int)(game.getMusicVolume()*100), 1260, 493);
        fonts.getTitleFont().draw(batch, ""+game.getLocalization().get("settings"), 270,273);
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
     * Method that is called when game is paused. Game is saved.
     */
    @Override
    public void pause() {
        game.toJson();
    }
    /**
     * Method that is called when game is hidden. Game is saved.
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
     * Create close button, language buttons for finnish and english and minus and plus buttons for
     * both effects and music volume.
     */
    private void createButtons() {
        Texture closeButtonIdle = game.getAssetManager().get("BUTTONS/button_close.png");
        Texture closeButtonPressed = game.getAssetManager().get("BUTTONS/button_close_PRESSED.png");

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)), new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH - 25, game.SCREEN_HEIGHT - 25);
        stage.addActor(closeButton);

        closeButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getLastScreen());
            }
        });

        Texture engButtonIdle = game.getAssetManager().get("BUTTONS/button_ENG.png");
        Texture engButtonPressed = game.getAssetManager().get("BUTTONS/button_ENG_PRESSED.png");

        ImageButton engButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(engButtonIdle)), new TextureRegionDrawable(new TextureRegion(engButtonPressed)));

        engButton.setPosition(184, 23);
        stage.addActor(engButton);

        engButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //lokalisaatio enkuksi
                game.setLocale(new Locale("en", "UK"));
                game.setupLocalization();
                game.setScreen(new OptionsScreen(game));
            }
        });

        Texture finButtonIdle = game.getAssetManager().get("BUTTONS/button_FIN.png");
        Texture finButtonPressed = game.getAssetManager().get("BUTTONS/button_FIN_PRESSED.png");

        ImageButton finButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(finButtonIdle)), new TextureRegionDrawable(new TextureRegion(finButtonPressed)));

        finButton.setPosition(136, 23);
        stage.addActor(finButton);

        finButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //lokalisaatio suomeksi
                game.setLocale(new Locale("fi", "FI"));
                game.setupLocalization();
                game.setScreen(new OptionsScreen(game));
            }
        });

        Texture minusButtonIdle = game.getAssetManager().get("BUTTONS/button_minus.png");
        Texture minusButtonPressed = game.getAssetManager().get("BUTTONS/button_minus_PRESSED.png");

        ImageButton minusEffButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(minusButtonIdle)), new TextureRegionDrawable(new TextureRegion(minusButtonPressed)));

        minusEffButton.setPosition(143, 79);
        stage.addActor(minusEffButton);

        minusEffButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //efekti äänet alas
                if(game.getEffVolume() > 0.09f){
                    game.setEffVolume(game.getEffVolume() - 0.1f);
                }
            }
        });

        ImageButton minusMusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(minusButtonIdle)), new TextureRegionDrawable(new TextureRegion(minusButtonPressed)));

        minusMusButton.setPosition(143, 51);
        stage.addActor(minusMusButton);

        minusMusButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //musiikki äänet alas
                if(game.getMusicVolume() > 0.09f){
                    game.setMusicVolume(game.getMusicVolume() - 0.1f);
                }
            }
        });

        Texture plusButtonIdle = game.getAssetManager().get("BUTTONS/button_plus.png");
        Texture plusButtonPressed = game.getAssetManager().get("BUTTONS/button_plus_PRESSED.png");

        ImageButton plusEffButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(plusButtonIdle)), new TextureRegionDrawable(new TextureRegion(plusButtonPressed)));

        plusEffButton.setPosition(192, 79);
        stage.addActor(plusEffButton);

        plusEffButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //efekti äänet ylös
                if(game.getEffVolume() < 1.0f){
                    game.setEffVolume(game.getEffVolume() + 0.1f);
                }
            }
        });

        ImageButton plusMusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(plusButtonIdle)), new TextureRegionDrawable(new TextureRegion(plusButtonPressed)));

        plusMusButton.setPosition(192, 51);
        stage.addActor(plusMusButton);

        plusMusButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //musiikki äänet ylös
                if(game.getMusicVolume() < 1.0f){
                    game.setMusicVolume(game.getMusicVolume() + 0.1f);
                }
            }
        });
    }
}
