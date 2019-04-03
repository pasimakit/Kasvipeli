package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import sun.security.provider.Sun;

public class ChoosePlantScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

    Texture background;
    private Viewport bgViewPort;

    Skin skin;

    Stage stage;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    GameScreen gameScreen;

    fastPlant lily;
    mediumPlant carnivorousPlant;
    slowPlant cactus;

    Fonts fonts;

    //ASKELMÄÄRÄ JA RAHAMÄÄRÄ TULOSTUU OIKEESEN KOHTAAN


    public ChoosePlantScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background_plantmenu.png");
        bgViewPort = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        createButtons();

        //luodaan valittavat kukat
        lily = new fastPlant(game.fastPlantTier);
        lily.setBounds(20, SCREEN_HEIGHT-75, 58,58);
        stage.addActor(lily);

        carnivorousPlant = new mediumPlant(game.mediumPlantTier);
        carnivorousPlant.setBounds(92, SCREEN_HEIGHT-50, 58,58);
        stage.addActor(carnivorousPlant);

        cactus = new slowPlant(game.slowPlantTier);
        cactus.setBounds(164, SCREEN_HEIGHT-55, 58,58);
        stage.addActor(cactus);
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
        fonts.fontViewport.apply();
        batch.setProjectionMatrix(fonts.fontViewport.getCamera().combined);
        batch.begin();
        fonts.mediumFont.draw(batch, ""+lily.growthTime, 300, 600);
        fonts.mediumFont.draw(batch, ""+lily.coinValue, 300, 400);
        fonts.mediumFont.draw(batch, ""+carnivorousPlant.growthTime, 850, 600);
        fonts.mediumFont.draw(batch, ""+carnivorousPlant.coinValue, 850, 400);
        fonts.mediumFont.draw(batch, ""+cactus.growthTime, 1400, 600);
        fonts.mediumFont.draw(batch, ""+cactus.coinValue, 1400, 400);
        batch.end();
        stage.getViewport().apply();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
        fonts.fontViewport.update(width, height, true);
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
        skin.dispose();
    }

    public void createButtons(){
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)),new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(SCREEN_WIDTH-25, SCREEN_HEIGHT-25);
        stage.addActor(closeButton);

        closeButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });

        Texture choseButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_selectplant_ENG.png"));
        Texture choseButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_selectplant_PRESSED_ENG.png"));

        ImageButton choseButton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton1.setPosition(18, 18);
        stage.addActor(choseButton1);

        choseButton1.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lily.plantChosen = true;
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(lily);
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
                carnivorousPlant.plantChosen = true;
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(carnivorousPlant);
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
                cactus.plantChosen = true;
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(cactus);
                game.setScreen(gameScreen);
            }
        });
    }
}
