package fi.tamk.sprintgarden;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MediumPlant extends Flower {

    public MediumPlant(int tier){
        setCurrentTier(tier);
        setupTextures();
        addListener(new MediumPlant.PlayerListener());
        setGrowthTime(3000);
    }

    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName("mediumplant1");
            getTextureList()[0] = new Texture("t1seed_medium.png");
            getTextureList()[1] = new Texture("t1stalk_medium.png");
            getTextureList()[2] = new Texture("t1finished_medium.png");

            setCoinValue(28);
        }else if(getCurrentTier() == 2){
            setPlantName("mediumplant2");
            getTextureList()[0] = new Texture("t2seed.png");
            getTextureList()[1] = new Texture("t2stalk.png");
            getTextureList()[2] = new Texture("t2finished.png");

            setCoinValue(56);
        }else if(getCurrentTier() == 3){
            setPlantName("mediumplant3");
            getTextureList()[0] = new Texture("t2seed.png");
            getTextureList()[1] = new Texture("t2stalk.png");
            getTextureList()[2] = new Texture("t2finished.png");

            setCoinValue(112);
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
