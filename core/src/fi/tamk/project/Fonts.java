package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {

    OrthographicCamera camera;

    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    BitmapFont smallFont;
    BitmapFont mediumFont;
    BitmapFont largeFont;
    FreeTypeFontGenerator fontGenerator;

    public Fonts(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void createSmallFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        smallFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void createMediumFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;
        parameter.borderWidth = 3;
        parameter.borderColor = Color.BLACK;
        mediumFont = generator.generateFont(parameter);
        generator.dispose();
    }
    public void createLargeFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;
        parameter.borderWidth = 3;
        parameter.borderColor = Color.BLACK;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 5;
        parameter.shadowOffsetY = 5;
        largeFont = generator.generateFont(parameter);
        generator.dispose();
    }
}
