package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class GameScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    int maxPlantingSpaceAmount = 4;

    ChoosePlantScreen choosePlantScreen;
    Skin skin;
    Stage stage;

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
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));

        Gdx.input.setInputProcessor(stage);

        //luodaan kasvatuspaikat
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
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.camera.combined);




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
                plantingSpace.plantedFlower.setBounds(plantingSpace.getX() + 8, plantingSpace.getY() + 16, 16, 16);
                plantingSpace.plantedFlower.setupGrowthBar();
                stage.addActor(plantingSpace.plantedFlower);
                stage.addActor(plantingSpace.plantedFlower.growthBar);
                if(!plantingSpace.plantedFlower.plantHarvested){
                    plantingSpace.plantedFlower.updateGrowthBar(plantingSpace);
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
        batch.begin();
        game.font14.draw(batch, "Steps: "+ game.stepCount, 20, SCREEN_HEIGHT-10);
        game.font14.draw(batch, "Coins: " + game.coins, 10, 120);
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
    }


}
