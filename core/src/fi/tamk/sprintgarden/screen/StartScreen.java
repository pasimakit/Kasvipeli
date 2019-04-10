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

public class StartScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;

    private Fonts fonts;
    private Stage stage;

    private GameScreen gameScreen;

    public StartScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

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

    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
        fonts.getFontViewport().update(width, height, true);
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
        stage.dispose();
        batch.dispose();
    }

    public void createButtons() {
        Texture playButtonIdle, playButtonPressed,settingsButtonIdle, settingsButtonPressed;

        if(game.getLocale().getCountry() == "FI"){
            playButtonIdle = game.getAssetManager().get("BUTTONS/button_startgame_FIN.png");
            playButtonPressed = game.getAssetManager().get("BUTTONS/button_startgame_FIN_PRESSED.png");

            settingsButtonIdle = game.getAssetManager().get("BUTTONS/button_startsettings_FIN.png");
            settingsButtonPressed = game.getAssetManager().get("BUTTONS/button_startsettings_FIN_PRESSED.png");
        }else{
            playButtonIdle = game.getAssetManager().get("BUTTONS/button_startgame_ENG.png");
            playButtonPressed = game.getAssetManager().get("BUTTONS/button_startgame_ENG_PRESSED.png");

            settingsButtonIdle = game.getAssetManager().get("BUTTONS/button_startsettings_ENG.png");
            settingsButtonPressed = game.getAssetManager().get("BUTTONS/button_startsettings_ENG_PRESSED.png");
        }

        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playButtonIdle)), new TextureRegionDrawable(new TextureRegion(playButtonPressed)));

        playButton.setPosition(47, 12);
        stage.addActor(playButton);

        playButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });

        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsButtonIdle)), new TextureRegionDrawable(new TextureRegion(settingsButtonPressed)));

        settingsButton.setPosition(143, 12);
        stage.addActor(settingsButton);

        settingsButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new OptionsScreen(game));
            }
        });
    }
}
