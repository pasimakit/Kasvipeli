package fi.tamk.sprintgarden.screen;

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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fi.tamk.sprintgarden.game.MainGame;

public class CreditsScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;

    private Stage stage;

    public CreditsScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);
        if(game.getLocale().getCountry().equals("FI")){
            background = game.getAssetManager().get("credits_FIN.png");
        }else{
            background = game.getAssetManager().get("credits_ENG.png");
        }
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
        createButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
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

    public void createButtons() {
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)), new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH - 25, game.SCREEN_HEIGHT - 25);
        stage.addActor(closeButton);

        closeButton.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StartScreen(game));
            }
        });
    }
}
