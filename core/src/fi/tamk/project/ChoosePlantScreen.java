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
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import sun.security.provider.Sun;

public class ChoosePlantScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;
    Texture background;

    TextButton backButton;
    Skin skin;

    Stage stage;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    GameScreen gameScreen;

    ArrayList<Flower> plantList = new ArrayList<Flower>();

    Sunflower sunflower1;
    Sunflower sunflower2;
    Sunflower sunflower3;

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

        createButtons();

        //luodaan valittavat kukat
        sunflower1 = new Sunflower();
        sunflower1.setBounds(18, SCREEN_HEIGHT-55, 16,16);
        stage.addActor(sunflower1);
        plantList.add(sunflower1);

        sunflower2 = new Sunflower();
        sunflower2.setBounds(90, SCREEN_HEIGHT-55, 16,16);
        stage.addActor(sunflower2);
        plantList.add(sunflower2);

        sunflower3 = new Sunflower();
        sunflower3.setBounds(162, SCREEN_HEIGHT-55, 16,16);
        stage.addActor(sunflower3);
        plantList.add(sunflower3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.3f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        skin.dispose();
    }

    public void createButtons(){
        Texture closeButtonIdle = new Texture(Gdx.files.internal("button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("button_close_PRESSED.png"));

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

        Texture choseButtonIdle = new Texture(Gdx.files.internal("valitse_button.png"));
        Texture choseButtonPressed = new Texture(Gdx.files.internal("seed2.png"));

        ImageButton choseButton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton1.setPosition(18, 18);
        stage.addActor(choseButton1);

        choseButton1.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sunflower1.plantChosen = true;
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(sunflower1);
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
                sunflower2.plantChosen = true;
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(sunflower2);
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
                sunflower3.plantChosen = true;
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(sunflower3);
                game.setScreen(gameScreen);
            }
        });
    }
}
