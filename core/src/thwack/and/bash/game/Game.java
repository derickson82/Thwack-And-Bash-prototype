package thwack.and.bash.game;

import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.SplashScreen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class Game extends ApplicationAdapter{

    private static OrthographicCamera gameCamera;
    private static OrthographicCamera screenCamera;

    private static SpriteBatch batch;

    private static Rectangle mapViewport;

    //UI STUFF
    private static BitmapFont font;

    private static TextureAtlas mainMenuAtlas;
    private static Skin mainMenuSkin;

    private static ImageButtonStyle slotImageButtonStyle;
    private static TextButtonStyle blueTextButtonStyle;

    public static OrthographicCamera getGameCamera(){
	return gameCamera;
    }

    public static OrthographicCamera getScreenCamera(){
	return screenCamera;
    }

    public static SpriteBatch getBatch(){
	return batch;
    }

    public static Rectangle getMapViewport(){
	return mapViewport;
    }

    public static ImageButtonStyle getSlotImageButtonStyle(){
	return slotImageButtonStyle;
    }

    public static TextButtonStyle getBlueTextButtonStyle(){
	return blueTextButtonStyle;
    }

    public static BitmapFont getFont(){
	return font;
    }

    private static GameScreen screen;
    private static GameScreen lastScreen;
    private static GameScreen nextScreen;

    public static void setScreen(GameScreen screen){
	if(Game.screen != null){
	    Game.screen.dispose();
	    Game.lastScreen = Game.screen;
	}
	Game.screen = screen;
	Game.screen.show();
    }

    public static void startLastScreen(){
	setScreen(lastScreen);
    }

    public static void setNextScreen(GameScreen nextScreen){
	Game.nextScreen = nextScreen;
    }

    public static void startNextScreen(){
	setScreen(nextScreen);
	nextScreen = null;
    }

    public static GameScreen getCurrentScreen(){
	return screen;
    }

    public static void clearScreen(){
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void setClearColor(Color color){
	Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
    }

    public static float getWidth(){
	return screenCamera.viewportWidth;
    }

    public static float getHeight(){
	return screenCamera.viewportHeight;
    }


    @Override
    public void create(){
	batch = new SpriteBatch();

	screenCamera = new OrthographicCamera();
	screenCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	mapViewport = new Rectangle(1f / 8f * getWidth(), 1f / 8f * getHeight(), 6f / 8f * getWidth(), 6f / 8f * getHeight());

	gameCamera = new OrthographicCamera();
	gameCamera.setToOrtho(false, getWidth(), getHeight());

	font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));


	mainMenuAtlas = new TextureAtlas(Gdx.files.internal("textureatlas/UI/output/mainMenuAtlas.atlas"));
	mainMenuSkin = new Skin(mainMenuAtlas);

	blueTextButtonStyle = new TextButtonStyle();
	blueTextButtonStyle.down = mainMenuSkin.getDrawable("BlueButtonDown");
	blueTextButtonStyle.over = mainMenuSkin.getDrawable("BlueButtonOver");
	blueTextButtonStyle.up = mainMenuSkin.getDrawable("BlueButtonUp");
	blueTextButtonStyle.font = font;
	blueTextButtonStyle.fontColor = Color.BLACK;

	slotImageButtonStyle = new ImageButtonStyle();
	slotImageButtonStyle.up = mainMenuSkin.getDrawable("BlueButtonUp");
	slotImageButtonStyle.over = mainMenuSkin.getDrawable("BlueButtonOver");
	slotImageButtonStyle.disabled = mainMenuSkin.getDrawable("BlueButtonDown");

	setScreen(new SplashScreen());
    }

    @Override
    public void render(){
	clearScreen();

	gameCamera.update();
	screenCamera.update();
	batch.setProjectionMatrix(screenCamera.combined);

	screen.update(Gdx.graphics.getDeltaTime());
	screen.render(batch);
    }

    @Override
    public void dispose(){
	screen.dispose();
    }

    @Override
    public void resume(){
	screen.resume();
    }

    @Override
    public void pause(){
	screen.pause();
    }

    @Override
    public void resize(int width, int height){
	screen.resize(width, height);
    }

}
