package fi.tamk.sprintgarden;

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

public class OptionsScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;

    private Fonts fonts;
    private Stage stage;

    private GameScreen gameScreen;

    public OptionsScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("settings.png");
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

        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getTitleFont().draw(batch, "SETTINGS", 150, 950);
        fonts.getTitleFont().draw(batch, "EFFECT VOLUME", 270, 693);
        fonts.getTitleFont().draw(batch, "MUSIC VOLUME", 270,493);
        fonts.getTitleFont().draw(batch, "LANGUAGE", 270,273);
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

    }

    public void createButtons() {
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)), new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH - 25, game.SCREEN_HEIGHT - 25);
        stage.addActor(closeButton);

        closeButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });

        Texture engButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_ENG.png"));
        Texture engButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_ENG_PRESSED.png"));

        ImageButton engButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(engButtonIdle)), new TextureRegionDrawable(new TextureRegion(engButtonPressed)));

        engButton.setPosition(184, 23);
        stage.addActor(engButton);

        engButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //lokalisaatio enkuksi
            }
        });

        Texture finButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_FIN.png"));
        Texture finButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_FIN_PRESSED.png"));

        ImageButton finButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(finButtonIdle)), new TextureRegionDrawable(new TextureRegion(finButtonPressed)));

        finButton.setPosition(136, 23);
        stage.addActor(finButton);

        engButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //lokalisaatio suomeksi
            }
        });

        Texture minusButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_minus.png"));
        Texture minusButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_minus_PRESSED.png"));

        ImageButton minusEffButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(minusButtonIdle)), new TextureRegionDrawable(new TextureRegion(minusButtonPressed)));

        minusEffButton.setPosition(143, 79);
        stage.addActor(minusEffButton);

        minusEffButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //efekti äänet alas
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
            }
        });

        Texture plusButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_plus.png"));
        Texture plusButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_plus_PRESSED.png"));

        ImageButton plusEffButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(plusButtonIdle)), new TextureRegionDrawable(new TextureRegion(plusButtonPressed)));

        plusEffButton.setPosition(192, 79);
        stage.addActor(plusEffButton);

        plusEffButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //efekti äänet ylös
            }
        });

        ImageButton plusMusButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(plusButtonIdle)), new TextureRegionDrawable(new TextureRegion(plusButtonPressed)));

        plusMusButton.setPosition(192, 51);
        stage.addActor(plusMusButton);

        plusEffButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //musiikki äänet ylös
            }
        });
    }
}
