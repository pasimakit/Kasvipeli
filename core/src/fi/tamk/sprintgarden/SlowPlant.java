package fi.tamk.sprintgarden;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class SlowPlant extends Flower {

    public SlowPlant(int tier){
        setCurrentTier(tier);
        setupTextures();
        addListener(new SlowPlant.PlayerListener());
        setGrowthTime(5000);
    }

    public void setupTextures(){
        if(getCurrentTier() == 1){
            setPlantName("slowplant1");
            getTextureList()[0] = new Texture("t1seed_slow.png");
            getTextureList()[1] = new Texture("t1stalk_slow.png");
            getTextureList()[2] = new Texture("t1finished_slow.png");

            setCoinValue(45);
        }else if(getCurrentTier() == 2){
            setPlantName("slowplant2");
            getTextureList()[0] = new Texture("t2seed.png");
            getTextureList()[1] = new Texture("t2stalk.png");
            getTextureList()[2] = new Texture("t2finished.png");

            setCoinValue(90);
        }else if(getCurrentTier() == 3){
            setPlantName("slowplant3");
            getTextureList()[0] = new Texture("t2seed.png");
            getTextureList()[1] = new Texture("t2stalk.png");
            getTextureList()[2] = new Texture("t2finished.png");

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
