package thwack.and.bash.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.util.Util.Meters;
import thwack.and.bash.game.util.Util.Objects;
import thwack.and.bash.game.util.Util.Pixels;

public class GameDebug extends Game implements GameScreen {

	// private Stage stage;
	// private ScreenViewport viewport;
	private SnakeDebug snake;
	BitmapFont debugFont;
	private static float STATUS_TEXT_SCALE = 1.0f;

	@Override
	public void create() {
		super.create();

		// viewport = new ScreenViewport(Objects.SCREEN_CAMERA);
		// stage = new Stage(viewport);

		GameScreen screen = null;
		// set to any screen that you work on - no need splash or whatever that
		// slows you down! :)
		screen = new PlayScreenDebug();
//		screen = new PlayScreen();
		setScreen(screen);
		Snake realSnake = ((PlayScreen) screen).getSnake();
		try {
			snake = new SnakeDebug(realSnake.getCollisionBody());
			((SnakeDebug)snake).setShapeRenderer(new SnakeShapeRenderer());	//note: it is snake own shapeRenderer, that is the trick! ;)
			((ShapeRenderer)((SnakeDebug)snake).getShapeRenderer()).setColor(Color.RED);
			((SnakeDebug)snake).setGameDebug(this);
			((PlayScreen) screen).setSnake(snake);	//replace with fake snake!
		} catch (Exception e) {
			System.err.println("GameDebug.java error: " + e + ", hopefully this is just a run from a unit test!");
//			if(snake == null) {
//				snake = new SnakeDebug(null);	//must be from unit test
//			}
		}
		// speed up for test ;)
		if (snake != null) {
//			snake.setWinderingSpeed(snake.getWinderingSpeed() * 10);
//			snake.setDirectionChangeSpeed(1000);
		}
		
		debugFont = new BitmapFont();
		debugFont.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		debugFont.scale(STATUS_TEXT_SCALE*.3f);
	}

	@Override
	public void update(float delta) {

	}

	public void drawLine(ShapeRenderer targetGO, Vector2 start, Vector2 end) {
		//drawing LOS
		targetGO.begin(ShapeType.Line);
		targetGO.identity();
		float dx = Meters.toPixels(start.x);
		float dy = Meters.toPixels(start.y);
		start.x = dx;
		start.y = dy;
		end.x = dx - snake.getSprite().getWidth()*4/3;
//		end.y = dy - snake.getSprite().getHeight()*4/3;
		end.y = dy;
		targetGO.line(start, end);
		targetGO.end();

		//drawing collision point with normal line
		targetGO.begin(ShapeType.Line);
		targetGO.identity();
//		snake.getLosFront().setStartLOS(start);
//		snake.getLosFront().setEndLOS(end);
//		start.x = Meters.toPixels(snake.getLosFront().getCollision().x/2);
//		start.y = Meters.toPixels(snake.getLosFront().getCollision().y/2);
//		end.x = Meters.toPixels(snake.getLosFront().getNormal().x/2);
//		end.y = Meters.toPixels(snake.getLosFront().getNormal().y/2);
//		targetGO.line(start, end);		
		targetGO.end();
		
		
//		targetGO.begin(ShapeType.Filled);
//		targetGO.identity();
//		targetGO.box(Meters.toPixels(start.x), Meters.toPixels(start.y), 0, snake.getSprite().getWidth()/2, snake.getSprite().getHeight()/2, 0);
//		targetGO.end();
	}

	@Override
	public void render() {
		super.render();
		
		batch.begin();
//		font.draw(batch, "" + debugStatusText, 50, 110);
//		font.draw(batch, "" + debugStatusTextExt, 50, 80);
		debugFont.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 20, 25);
		batch.end();
	}

	//c.f. https://code.google.com/p/libgdx/wiki/scene2d
	public void draw(ShapeRenderer renderer, SpriteBatch batch, float parentAlpha) {
		batch.end();

		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());

		// review snake los
//		drawLine(renderer, snake.getPosition(), snake.getLosFront().getEndLOS());
		//renderer.rect(0, 0, getWidth(), getHeight());

//		drawCross(renderer, ((Vector2)snake.getLosFront().getCollision()).x, ((Vector2)snake.getLosFront().getCollision()).y);

		batch.begin();
	}

	public void drawCross(ShapeRenderer shapeRenderer, float x, float y) {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.setColor(255, 0, 0, 0.5f);
		shapeRenderer.line(x - 10, y, x + 10, y); // horizontal line
		shapeRenderer.line(x, y + 10, x, y - 10); // vertical line
		shapeRenderer.end();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

}
