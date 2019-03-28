package fi.tamk.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Fonts {

    FitViewport fontViewport;

    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    BitmapFont smallestFont;
    BitmapFont smallFont;
    BitmapFont mediumFont;
    BitmapFont largeFont;


    public Fonts(){
        fontViewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void createSmallestFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        smallestFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void createSmallFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.valueOf("#733e39");
        parameter.size = 58;
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
