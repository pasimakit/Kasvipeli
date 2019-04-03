package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.Writer;
import java.util.ArrayList;

public class GameScreen implements Screen {

    Json json = new Json();

    SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    ChoosePlantScreen choosePlantScreen;
    MarketScreen marketScreen;

    Skin skin;
    Stage stage;
    Fonts fonts;


    ArrayList<PlantingSpace> plantingSpaceList = new ArrayList<PlantingSpace>();
    public PlantingSpace chosenPlantingSpace;

    public GameScreen(MainGame game) {
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
        bgViewPort = new FillViewport(SCREEN_WIDTH, SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        Gdx.input.setInputProcessor(stage);

        //luodaan kasvatuspaikat
        if (game.maxPlantingSpaceAmount >= plantingSpaceList.size()) {
            for (int i = 0; i < game.maxPlantingSpaceAmount; i++) {
                if (plantingSpaceList.size() != game.maxPlantingSpaceAmount) {
                    plantingSpaceList.add(new PlantingSpace());
                }
            }
        }
        loadPrefs();

        for (int i = 0; plantingSpaceList.size() > i; i++) {
            if (game.currentPlantingSpaceAmount > i) {
                plantingSpaceList.get(i).isUsable = true;
            }
        }

        int plantingSpaceX = 21;
        int plantingSpaceY = 5;

        for (PlantingSpace plantingSpace : plantingSpaceList) {
            if (plantingSpace.isUsable) {
                plantingSpace.setBounds(plantingSpaceX, plantingSpaceY, 58, 58);
                stage.addActor(plantingSpace);
            }

            if (plantingSpaceX >= 145) {
                plantingSpaceX = 21;
                plantingSpaceY = 65;
            } else {
                plantingSpaceX += 48;
            }
            if (plantingSpace.plantedFlower != null) {
                plantingSpace.plantedFlower.setBounds(plantingSpace.getX(), plantingSpace.getY(), 58, 58);
                plantingSpace.plantedFlower.setupGrowthBar();
                stage.addActor(plantingSpace.plantedFlower);
                stage.addActor(plantingSpace.plantedFlower.growthBar);
            }
        }
        createButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        for (PlantingSpace plantingSpace : plantingSpaceList) {
            //onko kasvatuspaikkaa klikattu
            if (plantingSpace.choosePlantWindow) {
                plantingSpace.choosePlantWindow = false;
                chosenPlantingSpace = plantingSpace;
                if (chosenPlantingSpace.plantedFlower == null) {
                    game.setScreen(choosePlantScreen);
                }
            }


            //kukan sijoitus ja kasvaminen
            if (plantingSpace.plantedFlower != null) {
                if (!plantingSpace.plantedFlower.plantHarvested) {
                    plantingSpace.plantedFlower.updateGrowthBar(plantingSpace);
                    plantingSpace.plantedFlower.updateTexture();
                }
                if (game.stepCount > game.oldStepCount) {
                    plantingSpace.plantedFlower.currentGrowthTime += game.stepCount - game.oldStepCount;
                }
                if (plantingSpace.plantedFlower.plantFinished && plantingSpace.plantedFlower.plantHarvested) {
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
        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        fonts.fontViewport.apply();
        batch.setProjectionMatrix(fonts.fontViewport.getCamera().combined);
        batch.begin();
        fonts.largeFont.draw(batch, "STEPS: " + game.stepCount, 50, 1060);
        fonts.largeFont.draw(batch, "COINS: " + game.coins, 500, 1060);
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
        makePrefs();
        game.toJson();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        makePrefs();
        game.toJson();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void createButtons() {
        Texture marketButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_market.png"));
        Texture marketButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_market_PRESSED.png"));

        ImageButton marketButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(marketButtonIdle)), new TextureRegionDrawable(new TextureRegion(marketButtonPressed)));

        marketButton.setPosition(SCREEN_WIDTH - 25, SCREEN_HEIGHT - 25);
        stage.addActor(marketButton);

        marketButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(marketScreen);
            }
        });

        Texture settingsButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_settings.png"));
        Texture settingsButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_settings_PRESSED.png"));

        ImageButton settingButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsButtonIdle)), new TextureRegionDrawable(new TextureRegion(settingsButtonPressed)));

        settingButton.setPosition(SCREEN_WIDTH - 25, SCREEN_HEIGHT - 50);
        stage.addActor(settingButton);

        settingButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //game.setScreen(gameScreen);
            }
        });
    }
    static class PlantingSpaceData{
        private boolean isUsable;
        private int growthTime;
        private int currentGrowthTime;
        private int currentTier;
    }

    public void makePrefs() {
        ArrayList<PlantingSpaceData> plantingSpaceDataList = new ArrayList<PlantingSpaceData>();

        FileHandle file = Gdx.files.local("gameState.json");
        for (int i = 0; plantingSpaceList.size() > i; i++) {
            plantingSpaceDataList.add(new PlantingSpaceData());
            plantingSpaceDataList.get(i).isUsable = plantingSpaceList.get(i).isUsable;
            if(plantingSpaceList.get(i).plantedFlower != null) {
                plantingSpaceDataList.get(i).growthTime = plantingSpaceList.get(i).plantedFlower.growthTime;
                plantingSpaceDataList.get(i).currentGrowthTime = plantingSpaceList.get(i).plantedFlower.currentGrowthTime;
                plantingSpaceDataList.get(i).currentTier = plantingSpaceList.get(i).plantedFlower.currentTier;
            }
        }
        json.setOutputType(JsonWriter.OutputType.json);
        file.writeString(json.prettyPrint(plantingSpaceDataList), false);
    }

    public void loadPrefs(){
        FileHandle file = Gdx.files.local("gameState.json");

        if(file.exists()) {
            ArrayList<PlantingSpaceData> datalist = json.fromJson(ArrayList.class, PlantingSpaceData.class, file);

            for(int i = 0; datalist.size() > i; i++){
                plantingSpaceList.get(i).isUsable = datalist.get(i).isUsable;

                if(plantingSpaceList.get(i).isUsable){
                    if(datalist.get(i).growthTime == 1000){
                        if(datalist.get(i).currentTier == 1){
                            plantingSpaceList.get(i).setPlantedFlower(new fastPlant(1));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }else if(datalist.get(i).currentTier == 2){
                            plantingSpaceList.get(i).setPlantedFlower(new fastPlant(2));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }else if(datalist.get(i).currentTier == 3){
                            plantingSpaceList.get(i).setPlantedFlower(new fastPlant(3));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }
                    }
                    if(datalist.get(i).growthTime == 3000){
                        if(datalist.get(i).currentTier == 1){
                            plantingSpaceList.get(i).setPlantedFlower(new mediumPlant(1));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }else if(datalist.get(i).currentTier == 2){
                            plantingSpaceList.get(i).setPlantedFlower(new mediumPlant(2));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }else if(datalist.get(i).currentTier == 3){
                            plantingSpaceList.get(i).setPlantedFlower(new mediumPlant(3));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }
                    }
                    if(datalist.get(i).growthTime == 5000){
                        if(datalist.get(i).currentTier == 1){
                            plantingSpaceList.get(i).setPlantedFlower(new slowPlant(1));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }else if(datalist.get(i).currentTier == 2){
                            plantingSpaceList.get(i).setPlantedFlower(new slowPlant(2));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }else if(datalist.get(i).currentTier == 3){
                            plantingSpaceList.get(i).setPlantedFlower(new slowPlant(3));
                            plantingSpaceList.get(i).plantedFlower.currentGrowthTime = datalist.get(i).currentGrowthTime;
                        }
                    }
                }
            }
        }
    }
}
