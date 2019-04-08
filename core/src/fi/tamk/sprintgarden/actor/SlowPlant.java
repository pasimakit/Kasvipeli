package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;

public class SlowPlant extends Flower {

    public SlowPlant(int tier, MainGame game){
        super(game);
        setCurrentTier(tier);
        setupTextures();
        addListener(new SlowPlant.PlayerListener());
        setGrowthTime(2500);
    }

    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName("slowplant1");
            getTextureList()[0] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage1.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage2_tier1.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage3_tier1.png");

            setCoinValue(45);
        }else if(getCurrentTier() == 2){
            setPlantName("slowplant2");
            getTextureList()[0] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage1.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage2_tier2.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage3_tier2.png");

            setCoinValue(90);
        }else if(getCurrentTier() == 3){
            setPlantName("slowplant3");
            getTextureList()[0] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage1.png");
            getTextureList()[1] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage2_tier3.png");
            getTextureList()[2] = getGame().getAssetManager().get("plants/slowPlant/plant3_stage3_tier3.png");

            setCoinValue(180);
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
