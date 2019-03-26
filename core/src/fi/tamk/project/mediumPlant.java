package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class mediumPlant extends Flower {

    public mediumPlant(int tier){
        currentTier = tier;
        setupTextures();
        addListener(new mediumPlant.PlayerListener());
        growthTime = 3000;
        coinValue = 28;
    }

    public void setupTextures(){
        if(currentTier == 1){
            textureList[0] = new Texture("t1seed_medium.png");
            textureList[1] = new Texture("t1stalk_medium.png");
            textureList[2] = new Texture("t1finished_medium.png");
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
