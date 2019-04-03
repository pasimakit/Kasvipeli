package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class FastPlant extends Flower {

    public FastPlant(int tier){
        currentTier = tier;
        setupTextures();
        addListener(new FastPlant.PlayerListener());
        growthTime = 1000;
    }

    public void setupTextures(){
        if(currentTier == 1){
            plantName = "fastplant1";
            textureList[0] = new Texture("plants/plant1_stage1.png");
            textureList[1] = new Texture("plants/plant1_stage2.png");
            textureList[2] = new Texture("plants/plant1_stage3_tier1.png");

            coinValue = 10;
        }else if(currentTier == 2){
            plantName = "fastplant2";
            textureList[0] = new Texture("plants/plant1_stage1.png");
            textureList[1] = new Texture("plants/plant1_stage2.png");
            textureList[2] = new Texture("plants/plant1_stage3_tier2.png");

            coinValue = 20;
        }else if(currentTier == 3){
            plantName = "fastplant3";
            textureList[0] = new Texture("plants/plant1_stage1.png");
            textureList[1] = new Texture("plants/plant1_stage2.png");
            textureList[2] = new Texture("plants/plant1_stage3_tier3.png");

            coinValue = 30;
        }
        if(flowerTexture==null) {
            flowerTexture = textureList[2];
        }
    }

    class PlayerListener extends InputListener {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            plantChosen = true;

            if(plantFinished){
                plantHarvested = true;
            }
            return false;
        }
    }

    public boolean isPlantChosen(){
        return plantChosen;
    }
}
