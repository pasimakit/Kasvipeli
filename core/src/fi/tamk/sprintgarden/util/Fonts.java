package fi.tamk.sprintgarden.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Fonts {

    private FitViewport fontViewport;

    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    private BitmapFont smallestFont;
    private BitmapFont smallFont;
    private BitmapFont mediumFont;
    private BitmapFont largeFont;
    private BitmapFont titleFont;


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
        parameter.size = 50;
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
    public void createTitleFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.valueOf("#733e39");
        parameter.size = 96;
        titleFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public FitViewport getFontViewport() {
        return fontViewport;
    }

    public BitmapFont getSmallestFont() {
        return smallestFont;
    }

    public BitmapFont getSmallFont() {
        return smallFont;
    }

    public BitmapFont getMediumFont() {
        return mediumFont;
    }

    public BitmapFont getLargeFont() {
        return largeFont;
    }

    public BitmapFont getTitleFont() {
        return titleFont;
    }
}
