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

import fi.tamk.sprintgarden.actor.FastPlant;
import fi.tamk.sprintgarden.util.Fonts;
import fi.tamk.sprintgarden.game.MainGame;
import fi.tamk.sprintgarden.actor.MediumPlant;
import fi.tamk.sprintgarden.actor.SlowPlant;

public class ChoosePlantScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;

    private Stage stage;

    private GameScreen gameScreen;

    private FastPlant fastPlant;
    private MediumPlant mediumPlant;
    private SlowPlant slowPlant;

    private Fonts fonts;

    public ChoosePlantScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = game.getAssetManager().get("background_plantmenu.png");
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        createButtons();

        //luodaan valittavat kukat
        fastPlant = new FastPlant(game.getFastPlantTier(), game);
        fastPlant.setMainGame(game);
        fastPlant.setBounds(20, game.SCREEN_HEIGHT-75, 58,58);
        fastPlant.displayTexture();
        stage.addActor(fastPlant);

        mediumPlant = new MediumPlant(game.getMediumPlantTier(), game);
        mediumPlant.setMainGame(game);
        mediumPlant.setBounds(92, game.SCREEN_HEIGHT-50, 58,58);
        mediumPlant.displayTexture();
        stage.addActor(mediumPlant);

        slowPlant = new SlowPlant(game.getSlowPlantTier(), game);
        slowPlant.setMainGame(game);
        slowPlant.setBounds(164, game.SCREEN_HEIGHT-55, 58,58);
        slowPlant.displayTexture();
        stage.addActor(slowPlant);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getMediumFont().draw(batch, ""+ fastPlant.getPlantName(), 180, 960);
        fonts.getMediumFont().draw(batch, ""+ fastPlant.getGrowthTime(), 300, 600);
        fonts.getMediumFont().draw(batch, ""+ fastPlant.getCoinValue(), 300, 400);
        fonts.getMediumFont().draw(batch, ""+ mediumPlant.getPlantName(), 680, 960);
        fonts.getMediumFont().draw(batch, ""+ mediumPlant.getGrowthTime(), 850, 600);
        fonts.getMediumFont().draw(batch, ""+ mediumPlant.getCoinValue(), 850, 400);
        fonts.getMediumFont().draw(batch, ""+ slowPlant.getPlantName(), 1240, 960);
        fonts.getMediumFont().draw(batch, ""+ slowPlant.getGrowthTime(), 1400, 600);
        fonts.getMediumFont().draw(batch, ""+ slowPlant.getCoinValue(), 1400, 400);
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
        gameScreen.makePrefs();
        game.toJson();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        gameScreen.makePrefs();
        game.toJson();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    public void createButtons(){
        Texture closeButtonIdle = game.getAssetManager().get("BUTTONS/button_close.png");
        Texture closeButtonPressed = game.getAssetManager().get("BUTTONS/button_close_PRESSED.png");

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)),new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH-25, game.SCREEN_HEIGHT-25);
        stage.addActor(closeButton);

        closeButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });

        Texture choseButtonIdle = game.getAssetManager().get("BUTTONS/button_selectplant_ENG.png");
        Texture choseButtonPressed = game.getAssetManager().get("BUTTONS/button_selectplant_PRESSED_ENG.png");

        ImageButton choseButton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton1.setPosition(18, 18);
        stage.addActor(choseButton1);

        choseButton1.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fastPlant.setPlantChosen(true);
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(fastPlant);
                game.setScreen(gameScreen);
            }
        });

        ImageButton choseButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton2.setPosition(90, 18);
        stage.addActor(choseButton2);

        choseButton2.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mediumPlant.setPlantChosen(true);
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(mediumPlant);
                game.setScreen(gameScreen);
            }
        });

        ImageButton choseButton3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton3.setPosition(162, 18);
        stage.addActor(choseButton3);

        choseButton3.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                slowPlant.setPlantChosen(true);
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(slowPlant);
                game.setScreen(gameScreen);
            }
        });
    }
}
