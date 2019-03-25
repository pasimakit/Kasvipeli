package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class PlantingSpace extends Actor {

    Texture plantingSpaceTexture;
    boolean choosePlantWindow, plantIsReady;

    Flower plantedFlower;

    //growthTime-=stepcounter<totalsteps


    public PlantingSpace() {
        setPlantingSpaceTexture();
        addListener(new PlayerListener());
    }

    public Texture getPlantingSpaceTexture() {
        return plantingSpaceTexture;
    }

    public void setPlantingSpaceTexture() {
        plantingSpaceTexture = new Texture("flowerbed_shadow_bot.png");
    }

    public void act(float delta){
        super.act(delta);
    }

    public void draw(Batch batch, float alpha){
        batch.draw(plantingSpaceTexture,this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                0,0,
                plantingSpaceTexture.getWidth(), plantingSpaceTexture.getHeight(),
                false, false);
    }
    // Sijoitetaan kasvatuspaikkaan kukka
    public void setPlantedFlower(Flower flower){
        plantedFlower = flower;
    }

    //vahditaan koska kasvatuspaikkaa painetaan
    class PlayerListener extends InputListener{
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log("space_clicked", "touch started at (" + x + ", " + y + ")");
            if(plantedFlower == null){
                choosePlantWindow = true;
            }else{
                if(plantedFlower.plantFinished){
                    plantIsReady = true;
                }
            }
            return false;
        }
    }
}
