package fi.tamk.project;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface Plant {

    void setTexture();
    void setGrowthTime();
    int getGrowthTime();
    void setValue();
    void draw(Batch batch, float alpha);
    void act(float delta);
    boolean isPlantChosen();
    void setLocation(float x, float y, int width, int height);

}
