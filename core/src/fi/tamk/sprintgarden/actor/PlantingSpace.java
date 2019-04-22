package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;

/**
 * Class which makes plantingSpaces for game
 */

public class PlantingSpace extends Actor {
    /**
     * plantingSpaces current texture.
     */
    private Texture plantingSpaceTexture;
    /**
     * If this plantingspace is chosen for flower to be planted in.
     */
    private boolean choosePlantWindow;
    /**
     * If the flower is ready.
     */
    private boolean plantIsReady;
    /**
     * If the plantingSpace is usable.
     */
    private boolean isUsable;

    /**
     * Flower that is planted in plantingSpace.
     */
    private Flower plantedFlower;

    /**
     * Reference to game for assetManager
     */
    private MainGame game;

    /**
     * Constructor for PlantingSpace. Places texture and adds listener.
     * @param game to make reference to MainGame
     */
    public PlantingSpace(MainGame game) {
        this.game = game;
        setPlantingSpaceTexture();
        addListener(new PlayerListener());
    }

    /**
     * Sets PlantingSpace texture based on if goal is reached and if flower is planted.
     */
    public void setPlantingSpaceTexture() {
        if(plantedFlower == null) {
            plantingSpaceTexture = game.getAssetManager().get("flowerbed_shadow_bot-right.png");
            if(game.isGoalReached()){
                plantingSpaceTexture = game.getAssetManager().get("flowerbed_GOLD.png");
            }
        }else{
            plantingSpaceTexture = game.getAssetManager().get("flowerbed_shadow_bot-right_PLANTED.png");
            if(game.isGoalReached()){
                plantingSpaceTexture = game.getAssetManager().get("flowerbed_planted_GOLD.png");
            }
        }
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
     * @param batch SpriteBatch to draw PlantingSpace
     * @param alpha transparency of PlantingSpace when drawn
     */
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(getPlantingSpaceTexture(),this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                0,0,
                getPlantingSpaceTexture().getWidth(), getPlantingSpaceTexture().getHeight(),
                false, false);
    }

    /**
     * Sets flower that is chosen in ChoosePlantScreen in PlantingSpace
     * @param flower flower that is planted
     */
    public void setPlantedFlower(Flower flower){
        setPlantingSpaceTexture();
        plantedFlower = flower;
        Sound digSound = game.getAssetManager().get("Sounds/dig.mp3");

        if(plantedFlower != null){
            if(plantedFlower.getCurrentGrowthTime() == 0) {
                digSound.play(game.getEffVolume());
            }
        }
    }

    /**
     * Getter for plantedFlower
     * @return returns plantedFlower
     */
    public Flower getPlantedFlower() {
        return plantedFlower;
    }

    /**
     * Getter for plantingSpaceTexture
     * @return returns plantingSpaceTexture
     */
    public Texture getPlantingSpaceTexture() {
        return plantingSpaceTexture;
    }

    /**
     * Getter for choosePlantWindow
     * @return returns choosePlantWindow
     */
    public boolean isChoosePlantWindow() {
        return choosePlantWindow;
    }

    /**
     * Setter for choosePlantWindow
     * @param choosePlantWindow sets choosePlantWindow
     */
    public void setChoosePlantWindow(boolean choosePlantWindow) {
        this.choosePlantWindow = choosePlantWindow;
    }

    /**
     * Setter for plantIsReady
     * @param plantIsReady sets plantIsReady
     */
    public void setPlantIsReady(boolean plantIsReady) {
        this.plantIsReady = plantIsReady;
    }

    /**
     * Getter for isUsable
     * @return returns isUsable
     */
    public boolean isUsable() {
        return isUsable;
    }

    /**
     * Setter for isUsable
     * @param usable sets isUsable
     */
    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    /**
     * Listener class for PlantingSpace
     */
    class PlayerListener extends InputListener{
        /**
         * Method when PlantingSpace is pressed
         * @param event
         * @param x
         * @param y
         * @param pointer
         * @param button
         * @return
         */
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            if(getPlantedFlower() == null){
                setChoosePlantWindow(true);
            }else{
                if(getPlantedFlower().isPlantFinished()){
                    setPlantIsReady(true);
                }
            }
            return false;
        }
    }
}
