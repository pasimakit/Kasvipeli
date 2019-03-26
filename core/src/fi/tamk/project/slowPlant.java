package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class slowPlant extends Flower {

    public slowPlant(int tier){
        currentTier = tier;
        setupTextures();
        addListener(new slowPlant.PlayerListener());
        growthTime = 5000;
        coinValue = 45;
    }

    public void setupTextures(){
        if(currentTier == 1){
            textureList[0] = new Texture("t1seed_slow.png");
            textureList[1] = new Texture("t1stalk_slow.png");
            textureList[2] = new Texture("t1finished_slow.png");
        }else if(currentTier == 2){
            textureList[0] = new Texture("t2seed.png");
            textureList[1] = new Texture("t2stalk.png");
            textureList[2] = new Texture("t2finished.png");
        }
        flowerTexture = textureList[0];
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
