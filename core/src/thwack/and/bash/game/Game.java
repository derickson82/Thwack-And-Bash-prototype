package thwack.and.bash.game;

import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.MainMenuScreen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class Game extends ApplicationAdapter{

    private static OrthographicCamera camera;

    private static SpriteBatch batch;

    //UI STUFF
    private static BitmapFont fontScalaSansCaps;
    private static BitmapFont fontScalaSansCond;
    private static BitmapFont fontScalaSansBlack;

    private static TextureAtlas mainMenuAtlas;
    private static Skin mainMenuSkin;

    private static TextButtonStyle blueTextButtonStyle;

    public static OrthographicCamera getCamera(){
	return camera;
    }

    public static SpriteBatch getBatch(){
	return batch;
    }

    public static TextButtonStyle getBlueTextButtonStyle(){
	return blueTextButtonStyle;
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
	return camera.viewportWidth;
    }

    public static float getHeight(){
	return camera.viewportHeight;
    }

    @Override
    public void create(){
	batch = new SpriteBatch();

	camera = new OrthographicCamera();
	camera.setToOrtho(false, 600, 600);

	mainMenuAtlas = new TextureAtlas(Gdx.files.internal("textureatlas/UI/output/mainMenuAtlas.atlas"));
	mainMenuSkin = new Skin(mainMenuAtlas);

	blueTextButtonStyle = new TextButtonStyle();
	blueTextButtonStyle.down = mainMenuSkin.getDrawable("BlueButtonDown");
	blueTextButtonStyle.over = mainMenuSkin.getDrawable("BlueButtonOver");
	blueTextButtonStyle.up = mainMenuSkin.getDrawable("BlueButtonUp");


	setScreen(new MainMenuScreen());
    }

    public void update(){
	screen.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(){
	update();
	clearScreen();
	camera.update();
	batch.setProjectionMatrix(camera.combined);
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
