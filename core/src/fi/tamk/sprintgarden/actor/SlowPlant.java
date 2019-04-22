package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;
/**
 * Subclass of Flower that is hardest to grow.
 */
public class SlowPlant extends Flower {

    /**
     * Constructor for SlowPlant. Sets needed values and textures and adds listener.
     * @param tier tier of SlowPlant
     * @param game to create game reference for assetManager
     */
    public SlowPlant(int tier, MainGame game){
        super(game);
        setCurrentTier(tier);
        setupTextures();
        addListener(new SlowPlant.PlayerListener());
        setGrowthTime(2500);
    }
    /**
     * Setup texture array based on tier of SlowPlant and coin value.
     */
    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName(game.getLocalization().get("slowPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage2_tier1.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage3_tier1.png");

            setCoinValue(45);
        }else if(getCurrentTier() == 2){
            setPlantName(game.getLocalization().get("slowPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage2_tier2.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage3_tier2.png");

            setCoinValue(90);
        }else if(getCurrentTier() == 3){
            setPlantName(game.getLocalization().get("slowPlant"));
            getTextureList()[0] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage2_tier3.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage3_tier3.png");

            setCoinValue(180);
        }
    }

    /**
     * Listener for SlowPlant
     */
    class PlayerListener extends InputListener {
        /**
         * Method for when SlowPlant is touched.
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
