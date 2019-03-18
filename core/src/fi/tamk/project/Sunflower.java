package fi.tamk.project;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Sunflower extends Flower {

    public Sunflower(){
        flowerTexture = new Texture("seed1.png");
        addListener(new Sunflower.PlayerListener());
        growthTime = 5;
        coinValue = 10;
    }


    class PlayerListener extends InputListener {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log("sunflower", "chosen");
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
