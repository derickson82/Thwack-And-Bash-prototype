
package thwack.and.bash.game.screen;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.entity.mob.Bat;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.level.Level;
import thwack.and.bash.game.ui.GameUI;
import thwack.and.bash.game.util.Util.Box2D;
import thwack.and.bash.game.util.Util.Objects;
import thwack.and.bash.game.util.Util.Pixels;
import thwack.and.bash.game.util.Util.Values;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

//ALL THIS CODE IS USED FOR THE GAMES PROTOTYPE, IT'S NOT FINAL
public class PlayScreen implements GameScreen {

	private GameUI gameUI;

	private Box2DDebugRenderer box2DRenderer;

	private FPSLogger fpsLogger;

	@Override
	public void update (float delta) {
		player.update(delta);
		bat.update(delta);
		Level.update(delta);
	}

	@Override
	public void render (SpriteBatch batch) {
		Level.render(batch);
		batch.setProjectionMatrix(Objects.GAME_CAMERA.combined);
		batch.begin();
		player.draw(batch);
		bat.draw(batch);
		batch.end();

		// gameUI.drawStage();
		//
		// staticBatch.begin();
		// gameUI.drawSprites(staticBatch);
		// staticBatch.end();
		box2DRenderer.render(Level.getWorld(), Objects.GAME_CAMERA.combined.scl(Values.PIXELS_PER_METER));
	}

	@Override
	public void resize (int width, int height) {

	}

	@Override
	public void show () {

		box2DRenderer = new Box2DDebugRenderer();

		World world = new World(new Vector2(0, 0), false);

		Level.addEntities(new Player(Box2D.createSimpleDynamicBody(
			new Vector2(Pixels.toMeters(Game.getWidth() / 2), Pixels.toMeters(Game.getHeight() / 2)), // Position
			new Vector2(Pixels.toMeters(35), Pixels.toMeters(46)), // Size
			world)));

		Level.addEntities(new Bat(Box2D.createSimpleDynamicBody(
			new Vector2(3, 15), //Position
			new Vector2(Pixels.toMeters(64), Pixels.toMeters(62)), // size
			world)));

		gameUI = new GameUI();

		Level.load("demo2.tmx", world);

	}

	@Override
	public void hide () {

	}

	@Override
	public void resume () {

	}

	@Override
	public void pause () {

	}

	@Override
	public void dispose () {

	}

}
