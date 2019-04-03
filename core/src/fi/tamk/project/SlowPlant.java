package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class SlowPlant extends Flower {

    public SlowPlant(int tier){
        currentTier = tier;
        setupTextures();
        addListener(new SlowPlant.PlayerListener());
        growthTime = 5000;
    }

    public void setupTextures(){
        if(currentTier == 1){
            plantName = "slowplant1";
            textureList[0] = new Texture("t1seed_slow.png");
            textureList[1] = new Texture("t1stalk_slow.png");
            textureList[2] = new Texture("t1finished_slow.png");

            coinValue = 45;
        }else if(currentTier == 2){
            plantName = "slowplant2";
            textureList[0] = new Texture("t2seed.png");
            textureList[1] = new Texture("t2stalk.png");
            textureList[2] = new Texture("t2finished.png");

            coinValue = 90;
        }else if(currentTier == 3){
            plantName = "slowplant3";
            textureList[0] = new Texture("t2seed.png");
            textureList[1] = new Texture("t2stalk.png");
            textureList[2] = new Texture("t2finished.png");

            coinValue = 180;
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
