package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class fastPlant extends Flower {

    public fastPlant(int tier){
        currentTier = tier;
        setupTextures();
        addListener(new fastPlant.PlayerListener());
        growthTime = 1000;
        coinValue = 10;
    }

    public void setupTextures(){
        if(currentTier == 1){
            textureList[0] = new Texture("t1seed.png");
            textureList[1] = new Texture("t1stalk.png");
            textureList[2] = new Texture("t1finished.png");
        }else if(currentTier == 2){
            textureList[0] = new Texture("t2seed.png");
            textureList[1] = new Texture("t2stalk.png");
            textureList[2] = new Texture("t2finished.png");
        }
        flowerTexture = textureList[0];
    }

    public void updateTexture(){
        if((float)currentGrowthTime/growthTime > 0.1f){
            flowerTexture = textureList[1];
        }else if((float)currentGrowthTime/growthTime >= 1.0f){
            flowerTexture = textureList[2];
        }else{
            flowerTexture = textureList[0];
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
