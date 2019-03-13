package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Flower extends Actor {

     Texture flowerTexture;
     boolean plantChosen;
     int growthTime;
     int coinValue;

     public Flower(){
         addListener(new Flower.PlayerListener());
     }

   @Override
    public void act(float delta){

        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(flowerTexture,this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                0,0,
                flowerTexture.getWidth(), flowerTexture.getHeight(),
                false, false);
    }
    //koska kukkaa painetaan
    class PlayerListener extends InputListener {
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log("flower", "chosen");
            plantChosen = true;
            return false;
        }
    }

    public boolean isPlantChosen(){
        return plantChosen;
    }

}
