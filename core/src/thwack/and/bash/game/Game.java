
package thwack.and.bash.game;

import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.SplashScreen;
import thwack.and.bash.game.util.Util.Objects;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	protected static SpriteBatch batch;

	private static GameScreen screen;
	private static GameScreen lastScreen;
	private static GameScreen nextScreen;

	public static void setScreen (GameScreen screen) {
		if (Game.screen != null) {
			Game.screen.dispose();
			Game.lastScreen = Game.screen;
		}
		Game.screen = screen;
		try {
			Game.screen.show();
		} catch (Exception e) {
			System.err.println("Game.java error in setScreen(): error="+e+", hopefully this is just a run from a unit test! ;)");
		}
	}

	public static void startLastScreen () {
		setScreen(lastScreen);
	}

	public static void setNextScreen (GameScreen nextScreen) {
		Game.nextScreen = nextScreen;
	}

	public static void startNextScreen () {
		setScreen(nextScreen);
		nextScreen = null;
	}

	public static GameScreen getCurrentScreen () {
		return screen;
	}

	public static void clearScreen () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void setClearColor (Color color) {
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
	}

	public static float getWidth () {
		float ret = 0;
		try {
			ret = Objects.SCREEN_CAMERA.viewportWidth;
		} catch (Exception e) {
			System.err.println("Util.java unable to get game width from camera, hopefully this is just a run from a unit test! ;)");
		}
		return ret;
	}

	public static float getHeight () {
		float ret = 0;
		try {
			ret = Objects.GAME_CAMERA.viewportHeight;
		} catch (Exception e) {
			System.err.println("Util.java unable to get game height from camera, hopefully this is just a run from a unit test! ;)");
		}
		return ret;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new SplashScreen());
	}

	@Override
	public void render () {
		try {
			clearScreen();
	
			Objects.SCREEN_CAMERA.update();
			Objects.GAME_CAMERA.update();
			//batch.setTransformMatrix(Objects.SCREEN_CAMERA.combined);
	
			screen.update(Gdx.graphics.getDeltaTime());
			screen.render(batch);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose () {
		screen.dispose();
	}

	@Override
	public void resume () {
		screen.resume();
	}

	@Override
	public void pause () {
		screen.pause();
	}

	@Override
	public void resize (int width, int height) {
		screen.resize(width, height);
	}

}
