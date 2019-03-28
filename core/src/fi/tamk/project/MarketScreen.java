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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MarketScreen implements Screen {

    SpriteBatch batch;
    final MainGame game;

    private Texture background;
    private Viewport bgViewPort;


    Fonts fonts;
    Stage stage;

    final int SCREEN_WIDTH = 256;
    final int SCREEN_HEIGHT = 144;

    GameScreen gameScreen;

    int[] plantingSpacePricing = {0, 0, 40, 80, 160, 320, 640, 1280, 0000};
    int[] plantTierPricing = {0, 200, 1000, 0000};

    public MarketScreen(MainGame game){
        this.game = game;
        batch = game.getBatch();
        gameScreen = game.getGameScreen();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("marketplace.png");
        bgViewPort = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT);

        fonts = new Fonts();
        fonts.createSmallestFont();
        fonts.createSmallFont();
        fonts.createMediumFont();
        fonts.createLargeFont();

        createButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        bgViewPort.apply();
        batch.setProjectionMatrix(bgViewPort.getCamera().combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        fonts.fontViewport.apply();
        batch.setProjectionMatrix(fonts.fontViewport.getCamera().combined);
        batch.begin();
        fonts.largeFont.draw(batch, "COINS: "+game.coins, 500, 1050);
        // tier numbers
        fonts.smallestFont.draw(batch, ""+game.fastPlantTier , 920, 645);
        fonts.smallestFont.draw(batch, ""+game.mediumPlantTier , 920, 405);
        fonts.smallestFont.draw(batch, ""+game.slowPlantTier , 920, 165);
        // upgrade prices
        if(game.fastPlantTier==3){
            fonts.smallFont.draw(batch, "MAX", 1000, 780);
        }else{
            fonts.smallFont.draw(batch, "$ "+ plantTierPricing[game.fastPlantTier], 1000, 780);
        }
        if(game.mediumPlantTier==3){
            fonts.smallFont.draw(batch, "MAX", 1000, 530);
        }else{
            fonts.smallFont.draw(batch, "$ "+ plantTierPricing[game.mediumPlantTier], 1000, 530);
        }
        if(game.slowPlantTier==3){
            fonts.smallFont.draw(batch, "MAX", 1000, 300);
        }else{
            fonts.smallFont.draw(batch, "$ "+ plantTierPricing[game.slowPlantTier], 1000, 300);
        }
        // additional plantingspaces
        fonts.smallFont.draw(batch, "+1      (" + game.currentPlantingSpaceAmount + " / " + game.maxPlantingSpaceAmount + ")", 220, 780);
        if(game.currentPlantingSpaceAmount==game.maxPlantingSpaceAmount){
            fonts.smallFont.draw(batch, "SOLD!", 300, 300);
        }else{
            fonts.smallFont.draw(batch, "$ "+plantingSpacePricing[game.currentPlantingSpaceAmount], 280, 300);
        }
        batch.end();
        stage.getViewport().apply();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        bgViewPort.update(width, height, true);
        stage.getViewport().update(width, height, true);
        fonts.fontViewport.update(width, height, true);
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
        Texture closeButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_close.png"));
        Texture closeButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_close_PRESSED.png"));

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

        Texture buyButtonIdle = new Texture(Gdx.files.internal("BUTTONS/button_buy_ENG.png"));
        Texture buyButtonPressed = new Texture(Gdx.files.internal("BUTTONS/button_buy_PRESSED_ENG.png"));

        ImageButton buyFastPlantButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buyButtonIdle)),new TextureRegionDrawable(new TextureRegion(buyButtonPressed)));

        buyFastPlantButton.setPosition(132, 79);
        stage.addActor(buyFastPlantButton);

        buyFastPlantButton.addListener (new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.fastPlantTier<=2 && game.coins>= plantTierPricing[game.fastPlantTier]){
                    game.coins-=plantTierPricing[game.fastPlantTier];
                    game.fastPlantTier++;
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
                if(game.mediumPlantTier<=2 && game.coins>= plantTierPricing[game.mediumPlantTier]){
                    game.coins-=plantTierPricing[game.mediumPlantTier];
                    game.mediumPlantTier++;
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
                if(game.slowPlantTier<=2 && game.coins>= plantTierPricing[game.slowPlantTier]){
                    game.coins-=plantTierPricing[game.slowPlantTier];
                    game.slowPlantTier++;
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
                if(game.currentPlantingSpaceAmount<game.maxPlantingSpaceAmount && game.coins >= plantingSpacePricing[game.currentPlantingSpaceAmount]){
                    game.coins-= plantingSpacePricing[game.currentPlantingSpaceAmount];
                    game.currentPlantingSpaceAmount++;
                }
            }
        });


    }
}
