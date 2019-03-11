package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class ChoosePlantScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

    TextButton backButton;
    Skin skin;

    Stage stage;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    ArrayList<Plant> plantList = new ArrayList<Plant>();

    Flowers flower;
    PlantingSpace chosenSpace; // TODO get planting space after clicking it

    public ChoosePlantScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
        backButton = new TextButton("Back", skin);
        backButton.setBounds(SCREEN_WIDTH-50, 2, 45, 20);
        stage.addActor(backButton);

        flower = new Flowers();
        flower.setBounds(5, SCREEN_HEIGHT-35,16,16);
        stage.addActor(flower);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.3f, 0.1f, 1);
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
