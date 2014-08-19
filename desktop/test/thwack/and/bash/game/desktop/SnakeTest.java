package thwack.and.bash.game.desktop;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.collision.SnakeBoundingBoxGuard;
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
import com.badlogic.gdx.math.Rectangle;
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
		PlayScreenDebug screen = new PlayScreenDebug();
		Game.setScreen(screen);
screen.show();	//TODO to mock this method
		Snake snake = screen.getSnake();
		Vector2 sPos = new Vector2();
		Rectangle sBox = new Rectangle();
		sBox.width= 64;
		sBox.height = 64;
		snake.setBoundingBox(sBox);
		SnakeBoundingBoxGuard guard = (SnakeBoundingBoxGuard) snake.getLos();
		guard.setSnake(snake);
		sPos.x = 10;
		sPos.y = 10;
		snake.setPosition(sPos);
		Vector2 pPos = new Vector2();
		Rectangle pBox = new Rectangle();
		pBox.width= 64;
		pBox.height = 64;
		Player player = screen.getPlayer();
		player.setBoundingBox(pBox);
//		guard.setCollidedObject(player);
		pPos.x = 10;
		pPos.y = 10;
		player.setPosition(pPos);
		float delta = 3000;	//maximum tick before the assert
		for(int i=1; i<delta; i++) {
			screen.update(i);
		}
		Assert.assertTrue(snake.getAi().getState() == SnakeAnimationType.ATTACK.ID);
	}

//	@Test
	public void testSnakeNotNearPlayer() {
		PlayScreenDebug screen = new PlayScreenDebug();
		Game.setScreen(screen);
		screen.show();
		Snake snake = screen.getSnake();
		Vector2 pPos = new Vector2();
		pPos.x = 10;
		pPos.y = 10;
		screen.getPlayer().setPosition(pPos);
		snake.updateAI(1);
		Assert.assertFalse(snake.getAi().getState() == SnakeAnimationType.ATTACK.ID);
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
