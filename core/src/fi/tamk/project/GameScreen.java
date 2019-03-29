package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

    ChoosePlantScreen choosePlantScreen;
    MarketScreen marketScreen;

    Skin skin;
    Stage stage;
    Fonts fonts;
    Sound coins;

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
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();
        coins = Gdx.audio.newSound(Gdx.files.internal("coins.mp3"));

        Gdx.input.setInputProcessor(stage);

        //luodaan kasvatuspaik
        if(game.currentPlantingSpaceAmount>=plantingSpaceList.size()){
            for (int i = plantingSpaceList.size(); i < game.currentPlantingSpaceAmount; i++) {
                plantingSpaceList.add(new PlantingSpace());
                System.out.println("plantingspace added");
            }
        }
        //sijoitetaan kasvatuspaikat
        int plantingSpaceX = 21;
        int plantingSpaceY = 5;
        for(PlantingSpace plantingSpace : plantingSpaceList){
            plantingSpace.setBounds(plantingSpaceX, plantingSpaceY, 58, 58);
            stage.addActor(plantingSpace);
            if(plantingSpaceX>=150){
                plantingSpaceX = 21;
                plantingSpaceY = 65;
            }else{
                plantingSpaceX+=48;
            }
            if(plantingSpace.plantedFlower != null){
                if(plantingSpace.plantedFlower.plantChosen) {
                    plantingSpace.plantedFlower.setBounds(plantingSpaceX-30, plantingSpace.getY()+ 20, 16, 16);
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
                    plantingSpace.plantedFlower.updateTexture();
                }
                if(game.stepCount>game.oldStepCount){
                    plantingSpace.plantedFlower.currentGrowthTime+=game.stepCount - game.oldStepCount;
                }
                if(plantingSpace.plantedFlower.plantFinished && plantingSpace.plantedFlower.plantHarvested) {
                    game.coins += plantingSpace.plantedFlower.coinValue;
                    coins.play();
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
        Texture marketButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_market.png"));
        Texture marketButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_market_PRESSED.png"));

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

        Texture settingsButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_settings.png"));
        Texture settingsButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_settings_PRESSED.png"));

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
