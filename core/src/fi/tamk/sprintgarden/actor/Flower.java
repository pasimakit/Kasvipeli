package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import fi.tamk.sprintgarden.game.MainGame;

/**
 * Parent class for all game flowers.
 */
public class Flower extends Actor {
    /**
     * Flowers current texture.
     */
    private Texture flowerTexture;
    /**
     * Spritesheet for coin animation when flower is harvested.
     */
    private Texture coinSpriteSheet;
    /**
     * Current animation frame when flower is harvested.
     */
    private TextureRegion coinCurrentFrame;
    /**
     * Texture array where flower is holding all the growth cycle textures.
     */
    private Texture[] textureList = new Texture[4];
    /**
     * Animation from coinSpriteSheet.
     */
    private Animation<TextureRegion> coinAnimation;
    /**
     * Check for if plant was chosen in choosePlantScreen.
     */
    private boolean plantChosen;
    /**
     * Checks if plant is fully grown.
     */
    private boolean plantFinished;
    /**
     * Check if plant was harvested and animation is ready to play.
     */
    private boolean plantHarvested;
    /**
     * Reference to game to get hold off assetManager.
     */
    MainGame game;
    /**
     * Name for plant.
     */
    private String plantName;
    /**
     * Amount of steps for plant to be fully grown.
     */
    private int growthTime;
    /**
     * Amount of steps currently flower has.
     */
    private int currentGrowthTime;
    /**
     * How much coins you get when flower is harvested.
     */
    private int coinValue;
    /**
     * Tier of flower.
     */
    private int currentTier;
    /**
     * ProgressBar to visualize currentGrowthTime and growthTime.
     */
    private ProgressBar growthBar;
    /**
     * Variable to determine current frame for coinAnimation.
     */
    private float stateTime;

    /**
     * Constructor for flower, setups the growthBar.
     * @param game game reference for assets
     */
    public Flower(MainGame game){
         setupGrowthBar();
         this.game = game;
     }

    /**
     * Setter for game
     * @param game sets game
     */
     public void setMainGame(MainGame game){
         this.game = game;
     }

    /**
     * Getter for game
     * @return returns game
     */
    public MainGame getGame() {
        return game;
    }

    /**
     * Method that is inherited from Actor
     * @param delta deltaTime
     */
    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Method that is inherited from Actor
     * @param batch SpriteBatch to draw flower
     * @param alpha transparency of Flower when drawn
     */
    @Override
    public void draw(Batch batch, float alpha){
        if(!plantHarvested){
            batch.draw(getFlowerTexture(),this.getX(), this.getY(),
                    this.getOriginX(), this.getOriginY(),
                    this.getWidth(), this.getHeight(),
                    this.getScaleX(), this.getScaleY(),
                    this.getRotation(),
                    0,0,
                    getFlowerTexture().getWidth(), getFlowerTexture().getHeight(),
                    false, false);
        }else{
            batch.draw(getCoinCurrentFrame(), this.getX(), this.getY(),
                    this.getWidth(), this.getHeight());
        }

    }

    /**
     * Updates the values in growthBar
     * @param space PlantingSpace where flower is planted and is used to place growthBar in place
     */
    public void updateGrowthBar(PlantingSpace space){
         if(!isPlantHarvested()) {
             getGrowthBar().setBounds(space.getX() + 10, space.getY() - 5, 39, 5);
             getGrowthBar().setValue((float) getCurrentGrowthTime() / getGrowthTime());
         }
         if(getCurrentGrowthTime() >= getGrowthTime()){
             setPlantFinished(true);
         }
    }

    /**
     * Updates texture based on the currentGrowthTime and growthTime difference.
     */
    public void updateTexture(){
        if(getCurrentGrowthTime() >= getGrowthTime()){
            flowerTexture = getTextureList()[3];
        }else if((float) getCurrentGrowthTime() / getGrowthTime() > 0.5f){
            flowerTexture = getTextureList()[2];
        }else if((float) getCurrentGrowthTime() / getGrowthTime() > 0.1f){
            flowerTexture = getTextureList()[1];
        }else{
            flowerTexture = getTextureList()[0];
        }
    }

    /**
     * Sets texture of the flower to be the one which is finished. Used for display.
     */

    public void displayTexture(){
         flowerTexture = getTextureList()[3];
    }

    /**
     * Setups the growthBar with correct size and colors.
     */

    public void setupGrowthBar() {
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();

        Pixmap pixmap = new Pixmap(39, 5, Pixmap.Format.RGBA8888);
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

        pixmap = new Pixmap(37, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#FEAE34"));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        setGrowthBar(new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle));
    }

    /**
     * Setups coinAnimation.
     */

    public void setupCoinAnimation(){
        coinSpriteSheet = game.getAssetManager().get("Sprite-0002.png");

        TextureRegion[][] tmp = TextureRegion.split(coinSpriteSheet,
                coinSpriteSheet.getWidth() / 11,
                coinSpriteSheet.getHeight());

        TextureRegion[] coinFrames = new TextureRegion[11];

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 11; j++) {
                coinFrames[index++] = tmp[i][j];
            }
        }

        coinAnimation = new Animation<TextureRegion>(0.08f, coinFrames);
    }

    /**
     * Starts coinAnimation. Starts when plant is harvested.
     * @param delta graphics deltaTime
     */
    public void startCoinAnimation(float delta){
        stateTime += delta;
        coinCurrentFrame = coinAnimation.getKeyFrame(stateTime, false);
    }

    /**
     * Getter for flowerTexture
     * @return returns flowerTexture
     */
    public Texture getFlowerTexture() {
        return flowerTexture;
    }

    /**
     * Getter for plantChosen
     * @return return plantChosen
     */

    public boolean isPlantChosen() {
        return plantChosen;
    }

    /**
     * Setter for plantChosen
     * @param plantChosen sets plantChosen
     */
    public void setPlantChosen(boolean plantChosen) {
        this.plantChosen = plantChosen;
    }

    /**
     * Getter for plantFinished
     * @return return plantFinished
     */
    public boolean isPlantFinished() {
        return plantFinished;
    }

    /**
     * Setter for plantFinished
     * @param plantFinished sets plantFinished
     */
    public void setPlantFinished(boolean plantFinished) {
        this.plantFinished = plantFinished;
    }

    /**
     * Getter for plantHarvested
     * @return returns plantHarvested
     */
    public boolean isPlantHarvested() {
        return plantHarvested;
    }

    /**
     * Setter for plantHarvested
     * @param plantHarvested sets plantHarvested
     */
    public void setPlantHarvested(boolean plantHarvested) {
        this.plantHarvested = plantHarvested;
    }

    /**
     * Getter for currentGrowthTime
     * @return returns currentGrowthTime
     */
    public int getCurrentGrowthTime() {
        return currentGrowthTime;
    }

    /**
     * Setter for currentGrowthTime
     * @param currentGrowthTime sets currentGrowthTime
     */

    public void setCurrentGrowthTime(int currentGrowthTime) {
        this.currentGrowthTime = currentGrowthTime;
    }

    /**
     * Getter for textureList
     * @return return textureList
     */
    public Texture[] getTextureList() {
        return textureList;
    }

    /**
     * Setter for flowerTexture
     * @param flowerTexture sets flowerTexture
     */
    public void setFlowerTexture(Texture flowerTexture) {
        this.flowerTexture = flowerTexture;
    }

    /**
     * Getter for plantName
     * @return returns plantName
     */
    public String getPlantName() {
        return plantName;
    }

    /**
     * Setter for plantName
     * @param plantName sets plantName
     */
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    /**
     * Getter for growthTime
     * @return returns growthTime
     */
    public int getGrowthTime() {
        return growthTime;
    }

    /**
     * Setter for growthTime
     * @param growthTime sets growthTime
     */
    public void setGrowthTime(int growthTime) {
        this.growthTime = growthTime;
    }

    /**
     * Getter for coinValue
     * @return returns coinValue
     */
    public int getCoinValue() {
        return coinValue;
    }

    /**
     * Setter for coinValue
     * @param coinValue sets coinValue
     */
    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    /**
     * Getter for currentTier
     * @return returns currentTier
     */
    public int getCurrentTier() {
        return currentTier;
    }

    /**
     * Setter for currentTier
     * @param currentTier sets currentTier
     */
    public void setCurrentTier(int currentTier) {
        this.currentTier = currentTier;
    }

    /**
     * Getter for growthBar
     * @return returns growthBar
     */
    public ProgressBar getGrowthBar() {
        return growthBar;
    }

    /**
     * Setter for growthBar
     * @param growthBar sets growthBar
     */
    public void setGrowthBar(ProgressBar growthBar) {
        this.growthBar = growthBar;
    }

    /**
     * Getter for coinCurrentFrame
     * @return returns coinCurrentFrame
     */
    public TextureRegion getCoinCurrentFrame() {
        return coinCurrentFrame;
    }

    /**
     * Getter for coinAnimation
     * @return returns coinAnimation
     */
    public Animation<TextureRegion> getCoinAnimation() {
        return coinAnimation;
    }

    /**
     * Getter for stateTime
     * @return returns stateTime
     */
    public float getStateTime() {
        return stateTime;
    }
}
