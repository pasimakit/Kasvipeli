package fi.tamk.sprintgarden.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class MarketScreen implements Screen {

    private SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;

    private Fonts fonts;
    private Stage stage;

    private GameScreen gameScreen;

    private SlowPlant slowPlant;
    private MediumPlant mediumPlant;
    private FastPlant fastPlant;

    private int[] plantingSpacePricing = {0, 0 ,40 ,80 ,350 ,700 ,1400 ,2800};
    private int[] plantTierPricing = {0, 200, 1000};

    private ProgressBar mileStoneBar;
    private int goalSteps = 100000;

    public MarketScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("marketplace.png");
        bgViewPort = new StretchViewport(game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();
        fonts.createTitleFont();

        createButtons();

        fastPlant = new FastPlant(game.getFastPlantTier(), game);
        fastPlant.setBounds(100, 82, 25,25);
        fastPlant.displayTexture();
        stage.addActor(fastPlant);

        mediumPlant = new MediumPlant(game.getMediumPlantTier(), game);
        mediumPlant.setBounds(100, 48, 25,25);
        mediumPlant.displayTexture();
        stage.addActor(mediumPlant);

        slowPlant = new SlowPlant(game.getSlowPlantTier(), game);
        slowPlant.setBounds(100, 15, 25,25);
        slowPlant.displayTexture();
        stage.addActor(slowPlant);

        createMileStoneBar();
        mileStoneBar.setBounds(game.SCREEN_WIDTH- 60, 15, 20, 100);
        stage.addActor(mileStoneBar);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mileStoneBar.setValue((float)game.getStepCount()/goalSteps);

        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();

        fonts.getFontViewport().apply();
        batch.setProjectionMatrix(fonts.getFontViewport().getCamera().combined);
        batch.begin();
        fonts.getLargeFont().draw(batch, "MARKET",150, 950);
        fonts.getLargeFont().draw(batch, "COINS: "+ game.getCoins(), 150, 880);
        fonts.getSmallFont().draw(batch, "UPGRADE PLANT TIERS", 720, 850);

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
        fonts.getSmallFont().draw(batch, "+1      (" + game.getCurrentPlantingSpaceAmount() + " / " + game.getMaxPlantingSpaceAmount() + ")", 220, 720);
        if(game.getCurrentPlantingSpaceAmount() == game.getMaxPlantingSpaceAmount()){
            fonts.getSmallFont().draw(batch, "SOLD!", 300, 300);
        }else{
            fonts.getSmallFont().draw(batch, "$ "+plantingSpacePricing[game.getCurrentPlantingSpaceAmount()], 280, 300);
        }
        //milestonebar description
        fonts.getSmallFont().draw(batch, "STEPMETER", 1440, 920);
        fonts.getSmallFont().draw(batch, ""+game.getStepCount(),1470, 90);
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
        gameScreen.makePrefs();
        game.toJson();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        gameScreen.makePrefs();
        game.toJson();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    public void createMileStoneBar(){
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();

        Pixmap pixmap = new Pixmap(20, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.rgb888(254,174, 52));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(20, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#FEAE34"));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        mileStoneBar = new ProgressBar(0.0f, 1.0f, 0.01f, true, progressBarStyle);
    }

    public void createButtons(){
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));
        Texture buyButtonIdle, buyButtonPressed;

        if(game.getLocale().getCountry() == "FI"){
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
                    if(fastPlant.getCurrentTier() <=3){
                        fastPlant.setCurrentTier(fastPlant.getCurrentTier() + 1);
                        fastPlant.setupTextures();
                        fastPlant.displayTexture();
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
                    if(mediumPlant.getCurrentTier() <=3){
                        mediumPlant.setCurrentTier(mediumPlant.getCurrentTier() + 1);
                        mediumPlant.setupTextures();
                        mediumPlant.displayTexture();
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
                    if(slowPlant.getCurrentTier() <=3){
                        slowPlant.setCurrentTier(slowPlant.getCurrentTier() + 1);
                        slowPlant.setupTextures();
                        slowPlant.displayTexture();
                    }
                }
            }
        });

        ImageButton buyPlantingSpaceButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyButtonIdle)),new TextureRegionDrawable(new TextureRegion(buyButtonPressed)));

        buyPlantingSpaceButton.setPosition(35, 15);
        stage.addActor(buyPlantingSpaceButton);

        buyPlantingSpaceButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getCurrentPlantingSpaceAmount() < game.getMaxPlantingSpaceAmount() && game.getCoins() >= plantingSpacePricing[game.getCurrentPlantingSpaceAmount()]){
                    game.setCoins(game.getCoins() - plantingSpacePricing[game.getCurrentPlantingSpaceAmount()]);
                    game.setCurrentPlantingSpaceAmount(game.getCurrentPlantingSpaceAmount() + 1);
                }
            }
        });


    }
}
