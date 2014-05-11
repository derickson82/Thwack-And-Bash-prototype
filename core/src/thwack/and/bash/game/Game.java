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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class Game extends ApplicationAdapter{

    private static OrthographicCamera staticCamera;
    private static OrthographicCamera gameCamera;

    private static SpriteBatch gameBatch;
    private static SpriteBatch staticBatch;

    //UI STUFF
    private static BitmapFont font;

    private static TextureAtlas mainMenuAtlas;
    private static Skin mainMenuSkin;

    private static ImageButtonStyle slotImageButtonStyle;
    private static TextButtonStyle blueTextButtonStyle;

    public static OrthographicCamera getStaticCamera(){
	return staticCamera;
    }

    public static OrthographicCamera getGameCamera(){
	return gameCamera;
    }

    public static SpriteBatch getStaticBatch(){
	return staticBatch;
    }

    public static SpriteBatch getGameBatch(){
	return gameBatch;
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
	return staticCamera.viewportWidth;
    }

    public static float getHeight(){
	return staticCamera.viewportHeight;
    }

    public static float getGameWidth(){
	return gameCamera.viewportWidth;
    }

    public static float getGameHeight(){
	return gameCamera.viewportHeight;
    }

    @Override
    public void create(){
	staticBatch = new SpriteBatch();
	gameBatch = new SpriteBatch();

	staticCamera = new OrthographicCamera();
	staticCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	gameCamera = new OrthographicCamera();
	gameCamera.setToOrtho(false, staticCamera.viewportWidth, staticCamera.viewportHeight);

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

	updateStaticBatch();
	updateGameBatch();

	screen.update(Gdx.graphics.getDeltaTime());
	screen.render(gameBatch, staticBatch);
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

    private void updateStaticBatch(){
	staticCamera.update();
	staticBatch.setProjectionMatrix(staticCamera.combined);
    }

    private void updateGameBatch(){
	gameCamera.update();
	gameBatch.setProjectionMatrix(gameCamera.combined);
    }

}
