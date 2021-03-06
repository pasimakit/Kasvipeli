package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;

/**
 * Subclass of Flower that is easiest to grow
 */
public class FastPlant extends Flower {
    /**
     * Constructor for FastPlant. Sets needed values and textures and adds listener.
     * @param tier tier of FastPlant
     * @param game to create game reference for assetManager
     */
    public FastPlant(int tier, MainGame game){
        super(game);
        setCurrentTier(tier);
        setupTextures();
        addListener(new FastPlant.PlayerListener());
        setGrowthTime(500);
    }

    /**
     * Setup texture array based on tier of FastPlant and coin value.
     */
    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName(game.getLocalization().get("fastPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage2_tier1.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage3_tier1.png");

            setCoinValue(10);
        }else if(getCurrentTier() == 2){
            setPlantName(game.getLocalization().get("fastPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage2_tier2.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage3_tier2.png");

            setCoinValue(20);
        }else if(getCurrentTier() == 3){
            setPlantName(game.getLocalization().get("fastPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage2_tier3.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage3_tier3.png");

            setCoinValue(30);
        }
    }

    /**
     * Listener for FastPlant
     */
    class PlayerListener extends InputListener {
        /**
         * Method for when FastPlant is touched.
         * @param event
         * @param x
         * @param y
         * @param pointer
         * @param button
         * @return
         */
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            setPlantChosen(true);

            if(isPlantFinished()){
                setPlantHarvested(true);
                if(getStateTime() == 0){
                    Sound coinSound = game.getAssetManager().get("Sounds/coins.mp3");
                    coinSound.play(game.getEffVolume());
                }
            }
            return false;
        }
    }

    /**
     * Getter for plantChosen
     * @return returns plantChosen
     */
    public boolean isPlantChosen(){
        return super.isPlantChosen();
    }
}
