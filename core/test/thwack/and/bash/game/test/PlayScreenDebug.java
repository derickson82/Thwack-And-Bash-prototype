
package thwack.and.bash.game.test;

import com.badlogic.gdx.math.Rectangle;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.collision.SnakeBoundingBoxGuard;
import thwack.and.bash.game.collision.SnakeCollisionHelper;
import thwack.and.bash.game.entity.mob.Bat;
import thwack.and.bash.game.entity.mob.Mob;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.level.Level;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.test.GameDebug;
import thwack.and.bash.game.ui.GameUI;
import thwack.and.bash.game.util.Util;
import thwack.and.bash.game.util.Util.Box2D;
import thwack.and.bash.game.util.Util.Objects;
import thwack.and.bash.game.util.Util.Pixels;
import thwack.and.bash.game.util.Util.Values;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PlayScreenDebug extends PlayScreen {

	private Box2DDebugRenderer box2DRenderer;

//	PlayerDebug player;
//	SnakeDebug snake;
	private CollisionBody playerBody;
	private CollisionBody batBody;
	private CollisionBody snakeBody;
	
	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Snake getSnake() {
		return snake;
	}

	@Override
	public void show () {
		try {
			box2DRenderer = new Box2DDebugRenderer();
		} catch (Exception e2) {
			System.err.println("PlayScreenDebug.java error: " + e2 + ", hopefully this is just a run from a unit test!");
		}

		try {
			world = new World(new Vector2(0, 0), false);
			playerBody = Box2D.createSimpleDynamicBody(
					new Vector2(Pixels.toMeters(Game.getWidth() / 2), Pixels.toMeters(Game.getHeight() / 2)), // Position
					new Vector2(Pixels.toMeters(35), Pixels.toMeters(46)), // Size
					world);
			playerBody.getBody().setUserData(Util.PLAYER_ID);
			batBody = Box2D.createSimpleDynamicBody(
					new Vector2(3, 15), //Position
					new Vector2(Pixels.toMeters(64), Pixels.toMeters(62)), // size
					world);
			snakeBody = Box2D.createSimpleDynamicBody(
						new Vector2(Snake.getSurWidth(), Snake.getSurHeight() /* TODO will get the real one one day! */), //initial position
						new Vector2(Pixels.toMeters(64), Pixels.toMeters(62)), // size
						world);

			Level.load("demo2.tmx", world);
		} catch (Exception e1) {
			System.err.println("PlayScreenDebug.java: World initialization error! Is this from a unit test or the world is really messed up??!");
			world = null;	//this is important for unit test to know!
			playerBody = null;
			batBody = null;
			snakeBody = null;
		}

		try {
			bat = new Bat(batBody);
		} catch (Exception e) {
			//e.printStackTrace();
			//who cares about these entities at this point anyway (at least for now)
		}

		try {
			player = new PlayerDebug(playerBody);
		} catch (Exception e) {
			e.printStackTrace();
			//it possibly will crash, yadah yadah we know, but no one cares in unit test world!
		}
		player.setId(SnakeCollisionHelper.PLAYER_ID);
		
		float x = -1;
		float y = -1;
		if(playerBody != null) {
			x = playerBody.getBody().getPosition().x;
			y = playerBody.getBody().getPosition().y;
		} else {
			x = 200;
			y = 300;
			Rectangle playerBoundingBox = new Rectangle(x, y, 32, 32);
			player.setBoundingBox(playerBoundingBox);	//must be from unit test, mock it, hell ya
		}
		player.setPosition(x, y);
		try {
			snake = new SnakeDebug(snakeBody);
			if(playerBody != null) {
				x = snakeBody.getBody().getPosition().x;
				y = snakeBody.getBody().getPosition().y;
			} else {
				x = 10;
				y = 20;
				Rectangle snakeBoundingBox = new Rectangle(x, y, 32, 32);
				snake.setBoundingBox(snakeBoundingBox);	//must be from unit test, mock it, hell ya
			}
			snake.setPosition(x, y);
		} catch (Exception e) {
			e.printStackTrace();
			//it possibly will crash, yadah yadah we know, but no one cares in unit test world!
		}
//		float mW = 32;
//		float mH = 32;
//		snake.setModWidth(mW);
//		snake.setModHeight(mH);
		snake.setId(SnakeCollisionHelper.SNAKE_ID);
		Rectangle snakeBoundingBox = new Rectangle(1000, 1000, 32, 32);
		snake.setBoundingBox(snakeBoundingBox);
	}

	@Override
	public void update (float delta) {
		player.update(delta);
		((PlayerDebug)player).updateBoundingBox();	//tips: comment out or uncomment to stop movement if you like
		bat.update(delta);
		snake.update(delta);
		((SnakeDebug)snake).updateBoundingBox();	//tips: comment out or uncomment to stop movement if you like
		Level.update(delta);
	}

	@Override
	public void render (SpriteBatch batch) {
		super.render(batch);

		box2DRenderer.render(Level.getWorld(), Objects.GAME_CAMERA.combined.scl(Values.PIXELS_PER_METER));
	}

}
