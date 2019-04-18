package fi.tamk.sprintgarden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import fi.tamk.sprintgarden.actor.FastPlant;
import fi.tamk.sprintgarden.util.Fonts;
import fi.tamk.sprintgarden.game.MainGame;
import fi.tamk.sprintgarden.actor.MediumPlant;
import fi.tamk.sprintgarden.actor.PlantingSpace;
import fi.tamk.sprintgarden.actor.SlowPlant;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Texture background, arrowRight, arrowLeft;
    private Viewport bgViewPort;

    private ChoosePlantScreen choosePlantScreen;
    private MarketScreen marketScreen;
    private OptionsScreen optionsScreen;

    private Stage stage;
    private Fonts fonts;


    private ArrayList<PlantingSpace> plantingSpaceList = new ArrayList<PlantingSpace>();
    public PlantingSpace chosenPlantingSpace;

    public GameScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        choosePlantScreen = new ChoosePlantScreen(game);
        marketScreen = new MarketScreen(game);
        optionsScreen = new OptionsScreen(game);

        background = game.getAssetManager().get("gamecanvas.png");
        bgViewPort = new FillViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        Gdx.input.setInputProcessor(stage);

        //luodaan kasvatuspaikat
        if (game.getMaxPlantingSpaceAmount() >= plantingSpaceList.size()) {
            for (int i = 0; i < game.getMaxPlantingSpaceAmount(); i++) {
                if (plantingSpaceList.size() != game.getMaxPlantingSpaceAmount()) {
                    plantingSpaceList.add(new PlantingSpace(game));
                }
            }
        }
        loadPrefs();

        for (int i = 0; plantingSpaceList.size() > i; i++) {
            if (game.getCurrentPlantingSpaceAmount() > i) {
                plantingSpaceList.get(i).setUsable(true);
            }
        }

        int plantingSpaceX = 21;
        int plantingSpaceY = 7;

        for (PlantingSpace plantingSpace : plantingSpaceList) {
            if (plantingSpace.isUsable()) {
                plantingSpace.setBounds(plantingSpaceX, plantingSpaceY, 58, 58);
                stage.addActor(plantingSpace);
            }

            if (plantingSpaceX >= 145) {
                plantingSpaceX = 21;
                plantingSpaceY = 67;
            } else {
                plantingSpaceX += 48;
            }
            if (plantingSpace.getPlantedFlower() != null) {
                plantingSpace.getPlantedFlower().setBounds(plantingSpace.getX(), plantingSpace.getY(), 58, 58);
                plantingSpace.getPlantedFlower().setupGrowthBar();
                plantingSpace.getPlantedFlower().setMainGame(game);
                stage.addActor(plantingSpace.getPlantedFlower());
                stage.addActor(plantingSpace.getPlantedFlower().getGrowthBar());
            }
        }
        createButtons();
        arrowRight = game.getAssetManager().get("arrow_right.png");
        arrowLeft = game.getAssetManager().get("arrow_left.png");


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getStepCount() >= game.GOALSTEPS && !game.isGoalReached()){
            game.setScreen(new PrizeScreen(game));
        }

        for (PlantingSpace plantingSpace : plantingSpaceList) {
            //onko kasvatuspaikkaa klikattu
            if (plantingSpace.isChoosePlantWindow()) {
                plantingSpace.setChoosePlantWindow(false);
                chosenPlantingSpace = plantingSpace;
                if (chosenPlantingSpace.getPlantedFlower() == null) {
                    game.setLastScreen(choosePlantScreen);
                    game.setScreen(choosePlantScreen);
                }
            }

            //kukan sijoitus ja kasvaminen
            if (plantingSpace.getPlantedFlower() != null) {
                if (!plantingSpace.getPlantedFlower().isPlantHarvested()) {
                    plantingSpace.getPlantedFlower().updateGrowthBar(plantingSpace);
                    plantingSpace.getPlantedFlower().updateTexture();
                }
                if (game.getStepCount() > game.getOldStepCount()) {
                    plantingSpace.getPlantedFlower().setCurrentGrowthTime(plantingSpace.getPlantedFlower().getCurrentGrowthTime() + game.getStepCount() - game.getOldStepCount());
                }
                if (plantingSpace.getPlantedFlower().isPlantFinished() && plantingSpace.getPlantedFlower().isPlantHarvested()) {
                    game.setCoins(game.getCoins() + plantingSpace.getPlantedFlower().getCoinValue());
                    Sound coinSound = game.getAssetManager().get("Sounds/coins.mp3");
                    coinSound.play(game.getEffVolume());
                    plantingSpace.getPlantedFlower().getGrowthBar().remove();
                    plantingSpace.getPlantedFlower().remove();
                    plantingSpace.getPlantedFlower().setGrowthBar(null);
                    plantingSpace.setPlantedFlower(null);
                }
            }else{
                plantingSpace.setPlantingSpaceTexture();
            }
        }
        game.setOldStepCount(game.getStepCount());
        stage.act(Gdx.graphics.getDeltaTime());
        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0);
        if(!game.isTutorialDone()){
            if(game.getCurrentPlantingSpaceAmount() == 0){
                batch.draw(arrowRight, 193, 112);
            }
            if(game.getCurrentPlantingSpaceAmount() == 1 && plantingSpaceList.get(0).getPlantedFlower() == null){
                batch.draw(arrowLeft, 85 ,25);
            }
            if(game.getCurrentPlantingSpaceAmount() == 1 && plantingSpaceList.get(0).getPlantedFlower() != null){
                game.setTutorialDone(true);
            }
        }
        batch.end();
        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getMediumFont().draw(batch, ""+game.getLocalization().get("steps")+": " + game.getStepCount(), 500, 1060);
        fonts.getMediumFont().draw(batch, ""+game.getLocalization().get("coins")+": " + game.getCoins(), 50, 1060);
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
        batch.dispose();
    }

    private void createButtons() {
        Texture marketButtonIdle = game.getAssetManager().get("BUTTONS/button_market.png");
        Texture marketButtonPressed = game.getAssetManager().get("BUTTONS/button_market_PRESSED.png");

        ImageButton marketButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(marketButtonIdle)), new TextureRegionDrawable(new TextureRegion(marketButtonPressed)));

        marketButton.setPosition(game.SCREEN_WIDTH - 25, game.SCREEN_HEIGHT - 25);
        stage.addActor(marketButton);

        marketButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setLastScreen(marketScreen);
                game.setScreen(marketScreen);
            }
        });

        Texture settingsButtonIdle = game.getAssetManager().get("BUTTONS/button_settings.png");
        Texture settingsButtonPressed = game.getAssetManager().get("BUTTONS/button_settings_PRESSED.png");

        ImageButton settingButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsButtonIdle)), new TextureRegionDrawable(new TextureRegion(settingsButtonPressed)));

        settingButton.setPosition(game.SCREEN_WIDTH - 25, game.SCREEN_HEIGHT - 50);
        stage.addActor(settingButton);

        settingButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setLastScreen(game.getGameScreen());
                game.setScreen(optionsScreen);
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
        Json json = new Json();
        ArrayList<PlantingSpaceData> plantingSpaceDataList = new ArrayList<PlantingSpaceData>();

        FileHandle file = Gdx.files.local("gameState.json");
        for (int i = 0; plantingSpaceList.size() > i; i++) {
            plantingSpaceDataList.add(new PlantingSpaceData());
            plantingSpaceDataList.get(i).isUsable = plantingSpaceList.get(i).isUsable();
            if(plantingSpaceList.get(i).getPlantedFlower() != null) {
                plantingSpaceDataList.get(i).growthTime = plantingSpaceList.get(i).getPlantedFlower().getGrowthTime();
                plantingSpaceDataList.get(i).currentGrowthTime = plantingSpaceList.get(i).getPlantedFlower().getCurrentGrowthTime();
                plantingSpaceDataList.get(i).currentTier = plantingSpaceList.get(i).getPlantedFlower().getCurrentTier();
            }
        }
        json.setOutputType(JsonWriter.OutputType.json);
        file.writeString(json.prettyPrint(plantingSpaceDataList), false);
    }

    public void loadPrefs(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("gameState.json");

        if(file.exists()) {
            ArrayList<PlantingSpaceData> datalist = json.fromJson(ArrayList.class, PlantingSpaceData.class, file);

            for(int i = 0; datalist.size() > i; i++){
                plantingSpaceList.get(i).setUsable(datalist.get(i).isUsable);

                if(plantingSpaceList.get(i).isUsable()){
                    if(datalist.get(i).growthTime == 500){
                        if(datalist.get(i).currentTier == 1){
                            plantingSpaceList.get(i).setPlantedFlower(new FastPlant(1, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }else if(datalist.get(i).currentTier == 2){
                            plantingSpaceList.get(i).setPlantedFlower(new FastPlant(2, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }else if(datalist.get(i).currentTier == 3){
                            plantingSpaceList.get(i).setPlantedFlower(new FastPlant(3, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }
                    }
                    if(datalist.get(i).growthTime == 1500){
                        if(datalist.get(i).currentTier == 1){
                            plantingSpaceList.get(i).setPlantedFlower(new MediumPlant(1, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }else if(datalist.get(i).currentTier == 2){
                            plantingSpaceList.get(i).setPlantedFlower(new MediumPlant(2, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }else if(datalist.get(i).currentTier == 3){
                            plantingSpaceList.get(i).setPlantedFlower(new MediumPlant(3, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }
                    }
                    if(datalist.get(i).growthTime == 2500){
                        if(datalist.get(i).currentTier == 1){
                            plantingSpaceList.get(i).setPlantedFlower(new SlowPlant(1, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }else if(datalist.get(i).currentTier == 2){
                            plantingSpaceList.get(i).setPlantedFlower(new SlowPlant(2, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }else if(datalist.get(i).currentTier == 3){
                            plantingSpaceList.get(i).setPlantedFlower(new SlowPlant(3, game));
                            plantingSpaceList.get(i).getPlantedFlower().setCurrentGrowthTime(datalist.get(i).currentGrowthTime);
                        }
                    }
                }
            }
        }
    }
}
