package thwack.and.bash.game.desktop;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.entity.mob.Bat;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.test.GameDebug;
import thwack.and.bash.game.test.PlayScreenDebug;
import thwack.and.bash.game.test.SnakeDebug;
import thwack.and.bash.game.test.SnakeShapeRenderer;
import thwack.and.bash.game.util.Util.Box2D;
import thwack.and.bash.game.util.Util.Pixels;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SnakeTest {

	private static Game game;

	public class Globals {
		public static final float NORMAL_TICK_RATE = 1f/30;	//in fps
		public static final float HIGH_TICK_RATE = 1f/60;	//in fps
	}

	@BeforeClass
	public static void setupGdx() {
		final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		config.renderInterval = Globals.NORMAL_TICK_RATE;
		game = new GameDebug();
		new HeadlessApplication(game, config);
	}

	@Test
	public void testSnakeNearPlayer() {
		PlayScreen screen = new PlayScreenDebug();
		game.setScreen(screen);
		Snake realSnake = ((PlayScreen) screen).getSnake();
		SnakeDebug snake = new SnakeDebug(realSnake.getCollisionBody());
		((PlayScreen) screen).setSnake(snake);	//replace with fake snake!
		((SnakeDebug)snake).setShapeRenderer(new SnakeShapeRenderer());	//note: it is snake own shapeRenderer, that is the trick! ;)
		((ShapeRenderer)((SnakeDebug)snake).getShapeRenderer()).setColor(Color.RED);
		// speed up for test ;)
		if (snake != null) {
			snake.setWinderingSpeed(snake.getWinderingSpeed() * 10);
			snake.setDirectionChangeSpeed(1000);
		}

//		World world = new World(new Vector2(0, 0), false);
//		Snake snake = new Snake(null);
//		Vector2 sPos = new Vector2();
//		sPos.x = 10;
//		sPos.y = 10;
//		snake.setPosition(sPos);
//		Player player = new Player(Box2D.createSimpleDynamicBody(
//				new Vector2(Pixels.toMeters(Game.getWidth() / 2), Pixels.toMeters(Game.getHeight() / 2)), // Position
//				new Vector2(Pixels.toMeters(35), Pixels.toMeters(46)), // Size
//				null));
		Vector2 pPos = new Vector2();
		pPos.x = 10;
		pPos.y = 10;
		screen.getPlayer().setPosition(pPos);
		snake.updateAI(1);
		Assert.assertTrue(snake.getAi().getState() == SnakeAnimationType.ATTACK.ID);
	}

//	@Test
	public void testSnakeInGame() {
//		bat = new Bat(Box2D.createSimpleDynamicBody(
//		new Vector2(3, 15), //Position
//		new Vector2(Pixels.toMeters(64), Pixels.toMeters(62)), // size
//		world));
        fail("Not yet implemented");
	}

}
