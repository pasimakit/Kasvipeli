package fi.tamk.sprintgarden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
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
 * Screen where you upgrade you garden.
 */
public class MarketScreen implements Screen {
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
     * Tutorial arrow to Left
     */
    private Texture arrowLeft;
    /**
     * Viewport for background image.
     */
    private Viewport bgViewPort;
    /**
     * Fonts are stored in this object
     */
    private Fonts fonts;
    /**
     * GameObjects are drawn on stage
     */
    private Stage stage;
    /**
     * Screen where game is played.
     */
    private GameScreen gameScreen;
    /**
     * slowPlant is for display when upgrading plants.
     */
    private SlowPlant slowPlant;
    /**
     * mediumPlant is for display when upgrading plants.
     */
    private MediumPlant mediumPlant;
    /**
     * fastPlant is for display when upgrading plants.
     */
    private FastPlant fastPlant;
    /**
     * Prices for buying more PlantingSpaces.
     */
    private int[] plantingSpacePricing = {10, 20, 40, 80, 500, 1500, 4000, 8000};
    /**
     * Prices for upgrading your flowers.
     */
    private int[] plantTierPricing = {0, 500, 2000};
    /**
     * Display your progress on GOALSTEPS.
     */
    private ProgressBar mileStoneBar;

    /**
     * Constructor for MarketScreen. Creates reference to MainGame, SpriteBatch and GameScreen.
     * @param game used to make reference to MainGame
     */
    public MarketScreen(MainGame game){
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

        background = game.getAssetManager().get("marketplace.png");
        arrowLeft = game.getAssetManager().get("arrow_left.png");
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();
        fonts.createTitleFont();
        fonts.createLargestFont();

        createButtons();

        fastPlant = new FastPlant(game.getFastPlantTier()+1, game);
        fastPlant.setBounds(96, 74, 32,32);
        fastPlant.displayTexture();
        stage.addActor(fastPlant);

        mediumPlant = new MediumPlant(game.getMediumPlantTier()+1, game);
        mediumPlant.setBounds(99, 46, 25,25);
        mediumPlant.displayTexture();
        stage.addActor(mediumPlant);

        slowPlant = new SlowPlant(game.getSlowPlantTier()+1, game);
        slowPlant.setBounds(94, 9, 34,34);
        slowPlant.displayTexture();
        stage.addActor(slowPlant);

        checkFlowerTexture();
        createMileStoneBar();
        mileStoneBar.setBounds(game.SCREEN_WIDTH- 70, 15, 20, 90);
        stage.addActor(mileStoneBar);
    }
    /**
     * Method which is called everytime frame is rendered. Check if goal is reached and if tutorial
     * is not done. Draw text for prices and info about what you are buying. Also updating the
     * progress on your milestoneBar.
     * @param delta deltaTime
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getStepCount() >= game.GOALSTEPS && !game.isGoalReached()){
            game.setScreen(new PrizeScreen(game));
        }

        mileStoneBar.setValue((float)game.getStepCount()/game.GOALSTEPS);

        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background,0,0);
        if(!game.isTutorialDone() && game.getCurrentPlantingSpaceAmount() == 0){
            batch.draw(arrowLeft, 65, 14, 24,24);
        }
        batch.end();

        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getLargestFont().draw(batch, ""+game.getLocalization().get("market"),150, 1000);
        fonts.getMediumFont().draw(batch, ""+game.getLocalization().get("coins") + ": " + game.getCoins(), 760, 980);
        fonts.getSmallFont().draw(batch, ""+game.getLocalization().get("upgradeTitle"), 720, 850);

        // tier numbers
        fonts.getSmallestFont().draw(batch, ""+ game.getFastPlantTier(), 920, 645);
        fonts.getSmallestFont().draw(batch, ""+ game.getMediumPlantTier(), 920, 405);
        fonts.getSmallestFont().draw(batch, ""+ game.getSlowPlantTier(), 920, 165);

        // upgrade prices
        if(game.getFastPlantTier() ==3){
            fonts.getSmallFont().draw(batch, "MAX", 1000, 780);
        }else{
            fonts.getSmallFont().draw(batch, "$ "+ plantTierPricing[game.getFastPlantTier()], 1000, 780);
        }
        if(game.getMediumPlantTier() ==3){
            fonts.getSmallFont().draw(batch, "MAX", 1000, 535);
        }else{
            fonts.getSmallFont().draw(batch, "$ "+ plantTierPricing[game.getMediumPlantTier()], 1000, 535);
        }
        if(game.getSlowPlantTier() ==3){
            fonts.getSmallFont().draw(batch, "MAX", 1000, 295);
        }else{
            fonts.getSmallFont().draw(batch, "$ "+ plantTierPricing[game.getSlowPlantTier()], 1000, 295);
        }
        // additional plantingspaces
        fonts.getSmallFont().draw(batch, ""+game.getLocalization().get("plantingspaceTitle"), 150, 850);
        fonts.getSmallFont().draw(batch, "(" + game.getCurrentPlantingSpaceAmount() + " / " + game.getMaxPlantingSpaceAmount() + ")", 290, 740);
        if(game.getCurrentPlantingSpaceAmount() == game.getMaxPlantingSpaceAmount()){
            fonts.getSmallFont().draw(batch, "SOLD!", 400, 210);
        }else{
            fonts.getSmallFont().draw(batch, "$ "+plantingSpacePricing[game.getCurrentPlantingSpaceAmount()], 400, 210);
        }
        //milestonebar description
        if(game.getLocale().getCountry().equals("FI")){
            fonts.getSmallFont().draw(batch, ""+game.getLocalization().get("stepmeter"), 1320, 850);
        }else{
            fonts.getSmallFont().draw(batch, ""+game.getLocalization().get("stepmeter"), 1355, 850);
        }
        fonts.getSmallFont().draw(batch, "- "+game.GOALSTEPS, 1560, 800);
        fonts.getSmallFont().draw(batch, ""+game.getStepCount(),1400, 90);
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
     * Method that is called when game is hidden. Game is saved.
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
        fonts.disposeFonts();
    }

    /**
     * Create the mileStoneBar style and size.
     */
    private void createMileStoneBar(){
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();

        Pixmap pixmap = new Pixmap(20, 88, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.rgb888(254,174, 52));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(20, 90, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#FEAE34"));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        mileStoneBar = new ProgressBar(0.0f, 1.0f, 0.0001f, true, progressBarStyle);
    }
    /**
     * Create close button, buy buttons based on language.
     */
    private void createButtons(){
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));
        Texture buyButtonIdle, buyButtonPressed;
        final Sound upgradeSound = game.getAssetManager().get("Sounds/bought.mp3");
        final Texture trophy = game.getAssetManager().get("tiertrophy.png");

        if(game.getLocale().getCountry().equals("FI")){
            buyButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_buy_FIN.png"));
            buyButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_buy_PRESSED_FIN.png"));
        } else{
            buyButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_buy_ENG.png"));
            buyButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_buy_PRESSED_ENG.png"));
        }

        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(closeButtonIdle)),new TextureRegionDrawable(new TextureRegion(closeButtonPressed)));

        closeButton.setPosition(game.SCREEN_WIDTH-25, game.SCREEN_HEIGHT-25);
        stage.addActor(closeButton);

        closeButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setLastScreen(new MarketScreen(game));
                game.setScreen(gameScreen);
            }
        });

        ImageButton buyFastPlantButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyButtonIdle)),new TextureRegionDrawable(new TextureRegion(buyButtonPressed)));

        buyFastPlantButton.setPosition(132, 79);
        stage.addActor(buyFastPlantButton);

        buyFastPlantButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getFastPlantTier() <=2 && game.getCoins() >= plantTierPricing[game.getFastPlantTier()]){
                    game.setCoins(game.getCoins() - plantTierPricing[game.getFastPlantTier()]);
                    game.setFastPlantTier(game.getFastPlantTier() + 1);
                    upgradeSound.play(game.getEffVolume());
                    if(fastPlant.getCurrentTier() <3){
                        fastPlant.setCurrentTier(fastPlant.getCurrentTier() + 1);
                        fastPlant.setupTextures();
                        fastPlant.displayTexture();
                    }else{
                        fastPlant.setFlowerTexture(trophy);
                    }
                }
            }
        });

        ImageButton buyMediumPlantButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyButtonIdle)),new TextureRegionDrawable(new TextureRegion(buyButtonPressed)));

        buyMediumPlantButton.setPosition(132, 46);
        stage.addActor(buyMediumPlantButton);

        buyMediumPlantButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getMediumPlantTier() <=2 && game.getCoins() >= plantTierPricing[game.getMediumPlantTier()]){
                    game.setCoins(game.getCoins() - plantTierPricing[game.getMediumPlantTier()]);
                    game.setMediumPlantTier(game.getMediumPlantTier() + 1);
                    upgradeSound.play(game.getEffVolume());
                    if(mediumPlant.getCurrentTier() <3){
                        mediumPlant.setCurrentTier(mediumPlant.getCurrentTier() + 1);
                        mediumPlant.setupTextures();
                        mediumPlant.displayTexture();
                    }else{
                        mediumPlant.setFlowerTexture(trophy);
                    }
                }
            }
        });

        ImageButton buySlowPlantButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyButtonIdle)),new TextureRegionDrawable(new TextureRegion(buyButtonPressed)));

        buySlowPlantButton.setPosition(132, 15);
        stage.addActor(buySlowPlantButton);

        buySlowPlantButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getSlowPlantTier() <=2 && game.getCoins() >= plantTierPricing[game.getSlowPlantTier()]){
                    game.setCoins(game.getCoins() - plantTierPricing[game.getSlowPlantTier()]);
                    game.setSlowPlantTier(game.getSlowPlantTier() + 1);
                    upgradeSound.play(game.getEffVolume());
                    if(slowPlant.getCurrentTier() <3){
                        slowPlant.setCurrentTier(slowPlant.getCurrentTier() + 1);
                        slowPlant.setupTextures();
                        slowPlant.displayTexture();
                    }else{
                        slowPlant.setFlowerTexture(trophy);
                    }
                }
            }
        });

        ImageButton buyPlantingSpaceButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyButtonIdle)),new TextureRegionDrawable(new TextureRegion(buyButtonPressed)));

        buyPlantingSpaceButton.setPosition(26, 18);
        stage.addActor(buyPlantingSpaceButton);

        buyPlantingSpaceButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getCurrentPlantingSpaceAmount() < game.getMaxPlantingSpaceAmount() && game.getCoins() >= plantingSpacePricing[game.getCurrentPlantingSpaceAmount()]){
                    game.setCoins(game.getCoins() - plantingSpacePricing[game.getCurrentPlantingSpaceAmount()]);
                    game.setCurrentPlantingSpaceAmount(game.getCurrentPlantingSpaceAmount() + 1);
                    upgradeSound.play(game.getEffVolume());
                }
            }
        });
    }

    /**
     * Check which tier of flower is displayed on upgrade windows. If it is tier upgraded to highest
     * tier it shows trophy as texture.
     */
    private void checkFlowerTexture(){
        final Texture trophy = game.getAssetManager().get("tiertrophy.png");

        if(fastPlant.getCurrentTier()==4){
            fastPlant.setFlowerTexture(trophy);
            fastPlant.setBounds(94, 73, 34,34);
        }
        if(mediumPlant.getCurrentTier()==4){
            mediumPlant.setFlowerTexture(trophy);
            mediumPlant.setBounds(94, 42, 34,34);
        }
        if(slowPlant.getCurrentTier()==4){
            slowPlant.setFlowerTexture(trophy);
            slowPlant.setBounds(94, 9, 34,34);
        }
    }


}
