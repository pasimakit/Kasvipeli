package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import sun.security.provider.Sun;

public class ChoosePlantScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

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


    public ChoosePlantScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);
        // nappulan skini
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        // takaisin nappula
        backButton = new TextButton("Back", skin);
        backButton.setBounds(SCREEN_WIDTH-50, 2, 45, 20);
        stage.addActor(backButton);
        // nappulan kuuntelija
        backButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });


        //luodaan valittavat kukat
        sunflower1 = new Sunflower();
        sunflower1.setBounds(85, SCREEN_HEIGHT-35, 16,16);
        stage.addActor(sunflower1);
        plantList.add(sunflower1);

        sunflower2 = new Sunflower();
        sunflower2.setBounds(125, SCREEN_HEIGHT-35, 16,16);
        stage.addActor(sunflower2);
        plantList.add(sunflower2);

        sunflower3 = new Sunflower();
        sunflower3.setBounds(165, SCREEN_HEIGHT-35, 16,16);
        stage.addActor(sunflower3);
        plantList.add(sunflower3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.3f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.camera.combined);

        //valitaan kukkanen arraylististä
        for(Flower flower : plantList){
            if(flower.isPlantChosen()){
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(flower);
                game.setScreen(game.getGameScreen());
            }
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        batch.end();
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
}
