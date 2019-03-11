package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class GameScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    int plantingSpaceAmount = 4;
    boolean windowOpen = false;

    Skin skin;
    Stage stage;

    ArrayList<PlantingSpace> plantingSpaceList = new ArrayList<PlantingSpace>();

    Flowers sunFlower;

    public GameScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));

        for(int i=0; i<plantingSpaceAmount; i++){
            plantingSpaceList.add(new PlantingSpace());
        }

        int plantingSpaceX = 0;
        for(PlantingSpace plantingSpace : plantingSpaceList){
            plantingSpace.setBounds(plantingSpaceX, 0, 48, 48);
            stage.addActor(plantingSpace);
            plantingSpaceX+=60;
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(PlantingSpace plantingSpace : plantingSpaceList){
            if(plantingSpace.openWindow && !windowOpen){
                pickPlantWindow();
                plantingSpace.openWindow = false;
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
    }

    public void pickPlantWindow(){
        final Window plantWindow = new Window("Choose a flower to plant", skin);
        TextButton closeButton = new TextButton("x", skin);
        plantWindow.getTitleLabel().setFontScale(0.5f);

        plantWindow.setSize(stage.getWidth()/1.1f, stage.getHeight()/1.1f);
        plantWindow.setPosition(stage.getWidth() / 20, stage.getHeight() / 20);

        sunFlower = new Flowers();
        sunFlower.setBounds(0,0, 16,16);

        plantWindow.add(sunFlower).left().expand().top().padTop(20).padLeft(20);

        plantWindow.getTitleTable().add(closeButton).size(15, 15).padRight(0).padTop(0);

        stage.addActor(plantWindow);
        windowOpen = true;

        closeButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // This is where we remove the window.
                plantWindow.remove();
                windowOpen = false;
            }
        });
    }

    public void createFlowers(){

    }
}
