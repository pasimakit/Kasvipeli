package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlantingSpace extends Actor {

    int growthTime;
    boolean usable;
    Texture plantingSpaceTexture;

    public PlantingSpace() {
        setPlantingSpaceTexture();
    }

    public Texture getPlantingSpaceTexture() {
        return plantingSpaceTexture;
    }

    public void setPlantingSpaceTexture() {
        plantingSpaceTexture = new Texture("plantingspace2.png");
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
}
