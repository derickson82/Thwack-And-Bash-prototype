
package thwack.and.bash.game;

import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.SplashScreen;
import thwack.and.bash.game.tween.VectorTweener;
import thwack.and.bash.game.util.Util.Objects;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Game extends ApplicationAdapter {

	private static SpriteBatch batch;

	private static GameScreen screen;
	private static GameScreen lastScreen;
	private static GameScreen nextScreen;

	public static void setScreen (GameScreen screen) {
		if (Game.screen != null) {
			Game.screen.dispose();
			Game.lastScreen = Game.screen;
		}
		Game.screen = screen;
		Game.screen.show();
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
		return Objects.SCREEN_CAMERA.viewportWidth;
	}

	public static float getHeight () {
		return Objects.GAME_CAMERA.viewportHeight;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		Tween.registerAccessor(Vector2.class, new VectorTweener());
		setScreen(new SplashScreen());
	}

	@Override
	public void render () {
		clearScreen();

		float delta = Gdx.graphics.getDeltaTime();

		Objects.SCREEN_CAMERA.update();
		Objects.GAME_CAMERA.update();
		Objects.TWEEN_MANAGER.update(delta);
		//batch.setTransformMatrix(Objects.SCREEN_CAMERA.combined);

		screen.update(delta);
		screen.render(batch);
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
