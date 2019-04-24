package fi.tamk.sprintgarden.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Class that holds different fonts with different styles. Also has viewport for fonts with higher
 * resolution for sharper text.
 */
public class Fonts {
    /**
     * Viewport for fonts.
     */
    private FitViewport fontViewport;
    /**
     * Font viewport width.
     */
    final int SCREEN_WIDTH = 1920;
    /**
     * Font viewport height.
     */
    final int SCREEN_HEIGHT = 1080;
    /**
     * Smallest font with 48 size.
     */
    private BitmapFont smallestFont;
    /**
     * Small font with 50 size and brown color.
     */
    private BitmapFont smallFont;
    /**
     * Medium font with 72 size and black border.
     */
    private BitmapFont mediumFont;
    /**
     * Large font with 72 size and brown color.
     */
    private BitmapFont largeFont;
    /**
     * Title font with 96 size and brown color.
     */
    private BitmapFont titleFont;
    /**
     * Largest font with 96 size and black borders and shadow.
     */
    private BitmapFont largestFont;
    /**
     * Congrats font with 172 szie and brown color.
     */
    private BitmapFont congratsFont;


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
        parameter.color = Color.valueOf("#733e39");
        largeFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void createLargestFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 92;
        parameter.borderWidth = 3;
        parameter.borderColor = Color.BLACK;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 5;
        parameter.shadowOffsetY = 5;
        largestFont = generator.generateFont(parameter);
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

    public void createCongratsFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.valueOf("#733e39");
        parameter.size = 172;
        congratsFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public BitmapFont getCongratsFont() {
        return congratsFont;
    }

    public BitmapFont getLargestFont() {
        return largestFont;
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

    public void disposeFonts(){
        smallestFont.dispose();
        smallFont.dispose();
        mediumFont.dispose();
        largeFont.dispose();
        largestFont.dispose();
        congratsFont.dispose();
        titleFont.dispose();
    }
}
