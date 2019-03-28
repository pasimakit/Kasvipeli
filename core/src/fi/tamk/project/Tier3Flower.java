package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Tier3Flower extends Flower {
        public Tier3Flower(){
            flowerTextureSprout = new Texture("seed1.png");
            addListener(new fi.tamk.project.Tier3Flower.PlayerListener());
            growthTime = 5000;
            coinValue = 50;
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
