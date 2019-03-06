package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    Stage stage;

    PlantingSpace plantingSpace1;
    PlantingSpace plantingSpace2;
    PlantingSpace plantingSpace3;
    PlantingSpace plantingSpace4;


    public GameScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        plantingSpace1 = new PlantingSpace();
        plantingSpace2 = new PlantingSpace();
        plantingSpace3 = new PlantingSpace();
        plantingSpace4 = new PlantingSpace();

        plantingSpace1.setBounds(0,0, 48,48);
        plantingSpace2.setBounds(60,0, 48,48);
        plantingSpace3.setBounds(120,0, 48,48);
        plantingSpace4.setBounds(180,0, 48,48);

        stage.addActor(plantingSpace1);
        stage.addActor(plantingSpace2);
        stage.addActor(plantingSpace3);
        stage.addActor(plantingSpace4);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
