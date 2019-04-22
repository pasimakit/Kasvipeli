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

import fi.tamk.sprintgarden.actor.FastPlant;
import fi.tamk.sprintgarden.util.Fonts;
import fi.tamk.sprintgarden.game.MainGame;
import fi.tamk.sprintgarden.actor.MediumPlant;
import fi.tamk.sprintgarden.actor.SlowPlant;

/**
 * Screen where you choose which plant you are planting.
 */

public class ChoosePlantScreen implements Screen {
    /**
     * Used to draw every pixel in game.
     */
    private SpriteBatch batch;
    /**
     * Reference to MainGame.
     */
    final MainGame game;
    /**
     * Background image.
     */
    private Texture background;
    /**
     * Viewport for background image.
     */
    private Viewport bgViewPort;
    /**
     * GameObjects are drawn on stage
     */
    private Stage stage;
    /**
     * Screen where game is played.
     */
    private GameScreen gameScreen;
    /**
     * fastPlant is for display to be chosen.
     */
    private FastPlant fastPlant;
    /**
     * mediumPlant is for display to be chosen.
     */
    private MediumPlant mediumPlant;
    /**
     * slowPlant is for display to be chosen.
     */
    private SlowPlant slowPlant;
    /**
     * Fonts are stored in this object
     */
    private Fonts fonts;

    /**
     * Constructor for ChoosePlantScreen. Reference to MainGame, SpriteBatch and GameScreen.
     * @param game used to make reference to MainGame
     */
    public ChoosePlantScreen(MainGame game) {
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }
    /**
     * Method which is called when screen is shown. In this method create references to variables,
     * and create objects and place them in correct places.
     */
    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = game.getAssetManager().get("background_plantmenu.png");
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        createButtons();

        //luodaan valittavat kukat
        fastPlant = new FastPlant(game.getFastPlantTier(), game);
        fastPlant.setMainGame(game);
        fastPlant.setBounds(18, game.SCREEN_HEIGHT-74, 54,54);
        fastPlant.displayTexture();
        stage.addActor(fastPlant);

        mediumPlant = new MediumPlant(game.getMediumPlantTier(), game);
        mediumPlant.setMainGame(game);
        mediumPlant.setBounds(96, game.SCREEN_HEIGHT-70, 45,45);
        mediumPlant.displayTexture();
        stage.addActor(mediumPlant);

        slowPlant = new SlowPlant(game.getSlowPlantTier(), game);
        slowPlant.setMainGame(game);
        slowPlant.setBounds(163, game.SCREEN_HEIGHT-72, 52,52);
        slowPlant.displayTexture();
        stage.addActor(slowPlant);
    }
    /**
     * Method which is called everytime frame is rendered. Check if goal is reached and draw info
     * about Flowers that are about to be chosen.
     * @param delta deltaTime
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getStepCount() >= game.GOALSTEPS && !game.isGoalReached()){
            game.setScreen(new PrizeScreen(game));
        }

        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getMediumFont().draw(batch, ""+ fastPlant.getPlantName(), 250, 960);
        fonts.getMediumFont().draw(batch, ""+ fastPlant.getGrowthTime(), 300, 600);
        fonts.getMediumFont().draw(batch, ""+ fastPlant.getCoinValue(), 300, 400);
        fonts.getMediumFont().draw(batch, ""+ mediumPlant.getPlantName(), 790, 960);
        fonts.getMediumFont().draw(batch, ""+ mediumPlant.getGrowthTime(), 850, 600);
        fonts.getMediumFont().draw(batch, ""+ mediumPlant.getCoinValue(), 850, 400);
        fonts.getMediumFont().draw(batch, ""+ slowPlant.getPlantName(), 1255, 960);
        fonts.getMediumFont().draw(batch, ""+ slowPlant.getGrowthTime(), 1400, 600);
        fonts.getMediumFont().draw(batch, ""+ slowPlant.getCoinValue(), 1400, 400);
        batch.end();
        stage.getViewport().apply();
        stage.draw();
    }
    /**
     * Method that is called when screen is resized. Updates different viewports.
     * @param width width after resizing
     * @param height height after resizing
     */
    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
        fonts.getFontViewport().update(width, height, true);
    }
    /**
     * Method that is called when game is paused. Game is saved.
     */
    @Override
    public void pause() {
        gameScreen.makePrefs();
        game.toJson();
    }
    /**
     * Method that is called when game is resumed.
     */
    @Override
    public void resume() {

    }
    /**
     * Method that is called when game is hidden. Game is saved.
     */
    @Override
    public void hide() {
        gameScreen.makePrefs();
        game.toJson();
    }
    /**
     * Disposes things.
     */
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    /**
     * Creates close button and select button based on language.
     */
    private void createButtons(){
        Texture closeButtonIdle = game.getAssetManager().get("BUTTONS/button_close.png");
        Texture closeButtonPressed = game.getAssetManager().get("BUTTONS/button_close_PRESSED.png");
        Texture choseButtonIdle, choseButtonPressed;

        if(game.getLocale().getCountry().equals("FI")){
            choseButtonIdle = game.getAssetManager().get("BUTTONS/button_selectplant_FIN.png");
            choseButtonPressed = game.getAssetManager().get("BUTTONS/button_selectplant_PRESSED_FIN.png");
        } else{
            choseButtonIdle = game.getAssetManager().get("BUTTONS/button_selectplant_ENG.png");
            choseButtonPressed = game.getAssetManager().get("BUTTONS/button_selectplant_PRESSED_ENG.png");
        }

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)),new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH-25, game.SCREEN_HEIGHT-25);
        stage.addActor(closeButton);

        closeButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setLastScreen(new ChoosePlantScreen(game));
                game.setScreen(gameScreen);
            }
        });



        ImageButton choseButton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton1.setPosition(18, 18);
        stage.addActor(choseButton1);

        choseButton1.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fastPlant.setPlantChosen(true);
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(fastPlant);
                game.setLastScreen(new ChoosePlantScreen(game));
                game.setScreen(gameScreen);
            }
        });

        ImageButton choseButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton2.setPosition(90, 18);
        stage.addActor(choseButton2);

        choseButton2.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mediumPlant.setPlantChosen(true);
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(mediumPlant);
                game.setLastScreen(new ChoosePlantScreen(game));
                game.setScreen(gameScreen);
            }
        });

        ImageButton choseButton3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(choseButtonIdle)),new TextureRegionDrawable(new TextureRegion(choseButtonPressed)));

        choseButton3.setPosition(162, 18);
        stage.addActor(choseButton3);

        choseButton3.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                slowPlant.setPlantChosen(true);
                game.getGameScreen().chosenPlantingSpace.setPlantedFlower(slowPlant);
                game.setLastScreen(new ChoosePlantScreen(game));
                game.setScreen(gameScreen);
            }
        });
    }
}
