package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class GameScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;
    Texture background;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    int maxPlantingSpaceAmount = 4;

    ChoosePlantScreen choosePlantScreen;
    MarketScreen marketScreen;

    Skin skin;
    Stage stage;
    Fonts fonts;

    ArrayList<PlantingSpace> plantingSpaceList = new ArrayList<PlantingSpace>();
    public PlantingSpace chosenPlantingSpace;

    public GameScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        choosePlantScreen = new ChoosePlantScreen(game);
        marketScreen = new MarketScreen(game);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        background = new Texture("gamecanvas.png");

        fonts = new Fonts();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        Gdx.input.setInputProcessor(stage);

        //luodaan kasvatuspaik
        if(maxPlantingSpaceAmount>plantingSpaceList.size()){
            for (int i = 0; i < maxPlantingSpaceAmount; i++) {
                plantingSpaceList.add(new PlantingSpace());
            }
        }
        //sijoitetaan kasvatuspaikat
        int plantingSpaceX = 30;
        for(PlantingSpace plantingSpace : plantingSpaceList){
            plantingSpace.setBounds(plantingSpaceX, 30, 32, 32);
            stage.addActor(plantingSpace);
            plantingSpaceX+=50;
            if(plantingSpace.plantedFlower != null){
                if(plantingSpace.plantedFlower.plantChosen) {
                    plantingSpace.plantedFlower.setBounds(plantingSpace.getX() + 8, plantingSpace.getY() + 16, 16, 16);
                    plantingSpace.plantedFlower.setupGrowthBar();
                    stage.addActor(plantingSpace.plantedFlower);
                    stage.addActor(plantingSpace.plantedFlower.growthBar);
                }
            }
        }
        createButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        for(PlantingSpace plantingSpace : plantingSpaceList){
            //onko kasvatuspaikkaa klikattu
            if(plantingSpace.choosePlantWindow){
                plantingSpace.choosePlantWindow = false;
                chosenPlantingSpace = plantingSpace;
                if(chosenPlantingSpace.plantedFlower == null){
                    game.setScreen(choosePlantScreen);
                }
            }


           //kukan sijoitus ja kasvaminen
            if(plantingSpace.plantedFlower != null) {

                if(!plantingSpace.plantedFlower.plantHarvested){
                    plantingSpace.plantedFlower.updateGrowthBar(plantingSpace);
                    System.out.println("progress päivittyy" + plantingSpace.plantedFlower.growthBar);
                }
                if(game.stepCount>game.oldStepCount){
                    plantingSpace.plantedFlower.currentGrowthTime+=game.stepCount - game.oldStepCount;
                }
                if(plantingSpace.plantedFlower.plantFinished && plantingSpace.plantedFlower.plantHarvested) {
                    game.coins += plantingSpace.plantedFlower.coinValue;
                    plantingSpace.plantedFlower.growthBar.remove();
                    plantingSpace.plantedFlower.remove();
                    plantingSpace.plantedFlower.growthBar = null;
                    plantingSpace.plantedFlower = null;
                }
            }
        }
        game.oldStepCount = game.stepCount;
        stage.act(Gdx.graphics.getDeltaTime());
        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.setProjectionMatrix(fonts.camera.combined);
        fonts.largeFont.draw(batch, "STEPS: "+ game.stepCount, 50, 1050);
        fonts.largeFont.draw(batch, "COINS: " + game.coins, 500, 1050);
        batch.end();
        batch.setProjectionMatrix(game.camera.combined);
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
    }

    public void createButtons(){
        Texture marketButtonIdle = new Texture(Gdx.files.internal("button_market.png"));
        Texture marketButtonPressed = new Texture(Gdx.files.internal("seed3.png"));

        ImageButton marketButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(marketButtonIdle)),new TextureRegionDrawable(new TextureRegion(marketButtonPressed)));

        marketButton.setPosition(SCREEN_WIDTH-25, SCREEN_HEIGHT-25);
        stage.addActor(marketButton);

        marketButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(marketScreen);
            }
        });

        Texture settingsButtonIdle = new Texture(Gdx.files.internal("button_settings.png"));
        Texture settingsButtonPressed = new Texture(Gdx.files.internal("seed2.png"));

        ImageButton settingButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsButtonIdle)),new TextureRegionDrawable(new TextureRegion(settingsButtonPressed)));

        settingButton.setPosition(SCREEN_WIDTH-25, SCREEN_HEIGHT-50);
        stage.addActor(settingButton);

        settingButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(gameScreen);
            }
        });
    }
}
