package fi.tamk.project;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Sunflower extends Flower {

    public Sunflower(){
        flowerTextureSprout = new Texture("seed1.png");
        addListener(new Sunflower.PlayerListener());
        growthTime = 1000;
        coinValue = 10;
    }


    class PlayerListener extends InputListener {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log("sunflower", "chosen");
            plantChosen = true;
            return false;
        }
    }

    public boolean isPlantChosen(){
        return plantChosen;
    }

}
