package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;

/**
 * Subclass of Flower that is middle version of fast and slow plant.
 */
public class MediumPlant extends Flower {

    /**
     * Constructor for MediumPlant. Sets needed values and textures and adds listener.
     * @param tier tier of MediumPlant
     * @param game to create game reference for assetManager
     */

    public MediumPlant(int tier, MainGame game){
        super(game);
        setCurrentTier(tier);
        setupTextures();
        addListener(new MediumPlant.PlayerListener());
        setGrowthTime(1500);
    }

    /**
     * Setup texture array based on tier of MediumPlant and coin value.
     */
    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName(game.getLocalization().get("mediumPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage2_tier1.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage3_tier1.png");

            setCoinValue(28);
        }else if(getCurrentTier() == 2){
            setPlantName(game.getLocalization().get("mediumPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage2_tier2.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage3_tier2.png");

            setCoinValue(56);
        }else if(getCurrentTier() == 3){
            setPlantName(game.getLocalization().get("mediumPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage2_tier3.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/mediumPlant/plant2_stage3_tier3.png");

            setCoinValue(112);
        }
    }

    /**
     * Listener for MediumPlant
     */
    class PlayerListener extends InputListener {
        /**
         * Method for when MediumPlant is touched.
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

            if(isPlantFinished()) {
                setPlantHarvested(true);
                if (getStateTime() == 0) {
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
