package fi.tamk.sprintgarden;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Flower extends Actor {

     private Texture flowerTexture;
     private Texture[] textureList = new Texture[3];
     private boolean plantChosen;
     private boolean plantFinished;
     private boolean plantHarvested;

     private String plantName;

     private int growthTime;
     private int currentGrowthTime;
     private int coinValue;
     private int currentTier;

    private ProgressBar growthBar;

     public Flower(){
         setupGrowthBar();
     }

   @Override
    public void act(float delta){

        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(getFlowerTexture(),this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                0,0,
                getFlowerTexture().getWidth(), getFlowerTexture().getHeight(),
                false, false);
    }

    public void updateGrowthBar(PlantingSpace space){

         if(!isPlantHarvested()) {
             getGrowthBar().setBounds(space.getX() + 10, space.getY() - 5, 39, 5);
             getGrowthBar().setValue((float) getCurrentGrowthTime() / getGrowthTime());
         }
         if(getCurrentGrowthTime() >= getGrowthTime()){
             setPlantFinished(true);
         }
    }

    public void updateTexture(){
        if(getCurrentGrowthTime() >= getGrowthTime()){
            flowerTexture = getTextureList()[2];
        }else if((float) getCurrentGrowthTime() / getGrowthTime() > 0.1f){
            flowerTexture = getTextureList()[1];
        }else{
            flowerTexture = getTextureList()[0];
        }
    }

    public void displayTexture(){
         flowerTexture = getTextureList()[2];
    }

    public void setupGrowthBar() {
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();

        Pixmap pixmap = new Pixmap(39, 5, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.rgb888(254,174, 52));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(37, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#FEAE34"));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        setGrowthBar(new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle));
    }

    public Texture getFlowerTexture() {
        return flowerTexture;
    }

    public boolean isPlantChosen() {
        return plantChosen;
    }

    public void setPlantChosen(boolean plantChosen) {
        this.plantChosen = plantChosen;
    }

    public boolean isPlantFinished() {
        return plantFinished;
    }

    public void setPlantFinished(boolean plantFinished) {
        this.plantFinished = plantFinished;
    }

    public boolean isPlantHarvested() {
        return plantHarvested;
    }

    public void setPlantHarvested(boolean plantHarvested) {
        this.plantHarvested = plantHarvested;
    }

    public int getCurrentGrowthTime() {
        return currentGrowthTime;
    }

    public void setCurrentGrowthTime(int currentGrowthTime) {
        this.currentGrowthTime = currentGrowthTime;
    }

    public Texture[] getTextureList() {
        return textureList;
    }

    public void setTextureList(Texture[] textureList) {
        this.textureList = textureList;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public void setGrowthTime(int growthTime) {
        this.growthTime = growthTime;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    public int getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(int currentTier) {
        this.currentTier = currentTier;
    }

    public ProgressBar getGrowthBar() {
        return growthBar;
    }

    public void setGrowthBar(ProgressBar growthBar) {
        this.growthBar = growthBar;
    }
}
