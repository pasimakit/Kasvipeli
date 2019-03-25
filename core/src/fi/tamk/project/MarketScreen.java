package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MarketScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;
    Texture background;

    Fonts fonts;
    Stage stage;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    GameScreen gameScreen;

    public MarketScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);
        background = new Texture("background_store.png");

        fonts = new Fonts();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        createButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.camera.combined);


        batch.begin();
        batch.draw(background,0,0);
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

    }

    public void createButtons(){
        Texture closeButtonIdle = new Texture(Gdx.files.internal("button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("button_close_PRESSED.png"));

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)),new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(SCREEN_WIDTH-25, SCREEN_HEIGHT-25);
        stage.addActor(closeButton);

        closeButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
            }
        });
    }
}
