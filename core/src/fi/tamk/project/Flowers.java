package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Flowers extends Actor {

    Texture flowerTexture;
    int growthTime;

    public Flowers() {
        setFlowerTexture();
        addListener(new Flowers.PlayerListener());
    }

    public void setFlowerTexture() {
        flowerTexture = new Texture("flower16.png");
    }

    public void act(float delta){
        super.act(delta);
    }

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

    class PlayerListener extends InputListener {

        @Override
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
            return false;
        }
    }
}
