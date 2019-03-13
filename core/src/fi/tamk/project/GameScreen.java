package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

            if(plantingSpace.plantedFlower != null) {
                plantingSpace.plantedFlower.setBounds(plantingSpace.getX() + 8, plantingSpace.getY() + 16, 16, 16);
                stage.addActor(plantingSpace.plantedFlower);
            }
           //onko kukka kasvanut
            if(plantingSpace.plantedFlower != null) {
                if(plantingSpace.plantedFlower.growthTime <= 0 && plantingSpace.plantIsReady) {
                    game.coins += plantingSpace.plantedFlower.coinValue;
                    plantingSpace.plantedFlower = null;
                }
            }

        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        game.font14.draw(batch, "Steps: "+ game.stepCount, 50, SCREEN_HEIGHT-5);
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
}
