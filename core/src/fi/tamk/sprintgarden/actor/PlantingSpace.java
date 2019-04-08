package fi.tamk.sprintgarden.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import fi.tamk.sprintgarden.game.MainGame;

public class PlantingSpace extends Actor {

    private Texture plantingSpaceTexture;
    private boolean choosePlantWindow;
    private boolean plantIsReady;
    private boolean isUsable;

    private Flower plantedFlower;

    private MainGame game;

    public PlantingSpace(MainGame game) {
        this.game = game;
        setPlantingSpaceTexture();
        addListener(new PlayerListener());
    }

    public void setPlantingSpaceTexture() {
       plantingSpaceTexture = game.getAssetManager().get("flowerbed_shadow_bot-right.png");
    }

    public void act(float delta){
        super.act(delta);
    }

    public void draw(Batch batch, float alpha){
        batch.draw(getPlantingSpaceTexture(),this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                0,0,
                getPlantingSpaceTexture().getWidth(), getPlantingSpaceTexture().getHeight(),
                false, false);
    }
    // Sijoitetaan kasvatuspaikkaan kukka
    public void setPlantedFlower(Flower flower){
        plantedFlower = flower;
    }

    public Flower getPlantedFlower() {
        return plantedFlower;
    }

    public Texture getPlantingSpaceTexture() {
        return plantingSpaceTexture;
    }

    public boolean isChoosePlantWindow() {
        return choosePlantWindow;
    }

    public void setChoosePlantWindow(boolean choosePlantWindow) {
        this.choosePlantWindow = choosePlantWindow;
    }

    public boolean isPlantIsReady() {
        return plantIsReady;
    }

    public void setPlantIsReady(boolean plantIsReady) {
        this.plantIsReady = plantIsReady;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    //vahditaan koska kasvatuspaikkaa painetaan
    class PlayerListener extends InputListener{
        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            if(getPlantedFlower() == null){
                setChoosePlantWindow(true);
            }else{
                if(getPlantedFlower().isPlantFinished()){
                    setPlantIsReady(true);
                }
            }
            return false;
        }
    }
}
