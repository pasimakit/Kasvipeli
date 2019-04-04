package fi.tamk.sprintgarden;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class FastPlant extends Flower {

    public FastPlant(int tier){
        setCurrentTier(tier);
        setupTextures();
        addListener(new FastPlant.PlayerListener());
        setGrowthTime(1000);
    }

    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName("fastplant1");
            getTextureList()[0] = new Texture("plants/plant1_stage1.png");
            getTextureList()[1] = new Texture("plants/plant1_stage2.png");
            getTextureList()[2] = new Texture("plants/plant1_stage3_tier1.png");

            setCoinValue(10);
        }else if(getCurrentTier() == 2){
            setPlantName("fastplant2");
            getTextureList()[0] = new Texture("plants/plant1_stage1.png");
            getTextureList()[1] = new Texture("plants/plant1_stage2.png");
            getTextureList()[2] = new Texture("plants/plant1_stage3_tier2.png");

            setCoinValue(20);
        }else if(getCurrentTier() == 3){
            setPlantName("fastplant3");
            getTextureList()[0] = new Texture("plants/plant1_stage1.png");
            getTextureList()[1] = new Texture("plants/plant1_stage2.png");
            getTextureList()[2] = new Texture("plants/plant1_stage3_tier3.png");

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
