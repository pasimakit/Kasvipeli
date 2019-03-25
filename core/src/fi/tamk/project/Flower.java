package fi.tamk.project;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Flower extends Actor {

     Texture flowerTexture;
     boolean plantChosen, plantFinished, plantHarvested;

     int growthTime;
     int currentGrowthTime;
     int coinValue;

    ProgressBar growthBar;

     public Flower(){
         setupGrowthBar();
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

    public boolean isPlantChosen(){
        return plantChosen;
    }

    public void updateGrowthBar(PlantingSpace space){

         if(!plantHarvested) {
             growthBar.setBounds(space.getX() + 10, space.getY() - 5, 39, 5);
             growthBar.setValue((float) currentGrowthTime / growthTime);
         }
         if(currentGrowthTime>=growthTime){
             plantFinished = true;
         }
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

        growthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
    }
}
