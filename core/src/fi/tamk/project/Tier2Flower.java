package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Tier2Flower extends Flower{
    public Tier2Flower(){
        flowerTextureSprout = new Texture("seed1.png");
        addListener(new Tier2Flower.PlayerListener());
        growthTime = 3000;
        coinValue = 30;
    }
    class PlayerListener extends InputListener {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log("tier2flower", "chosen");
            plantChosen = true;
            return false;
        }
    }

    public boolean isPlantChosen(){
        return plantChosen;
    }

}
