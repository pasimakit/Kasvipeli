package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;

public class FastPlant extends Flower {

    public FastPlant(int tier, MainGame game){
        super(game);
        setCurrentTier(tier);
        setupTextures();
        addListener(new FastPlant.PlayerListener());
        setGrowthTime(500);
    }

    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName("Flower");
            getTextureList()[0] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage2_tier1.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage3_tier1.png");

            setCoinValue(10);
        }else if(getCurrentTier() == 2){
            setPlantName("Flower");
            getTextureList()[0] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage2_tier2.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage3_tier2.png");

            setCoinValue(20);
        }else if(getCurrentTier() == 3){
            setPlantName("Flower");
            getTextureList()[0] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage0.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage2_tier3.png");
            getTextureList()[3] = getGame().getAssetManager().get("plants/fastPlant/plant1_stage3_tier3.png");

            setCoinValue(30);
        }
    }

    class PlayerListener extends InputListener {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            setPlantChosen(true);

            if(isPlantFinished()){
                setPlantHarvested(true);
            }
            return false;
        }
    }

    public boolean isPlantChosen(){
        return super.isPlantChosen();
    }
}
