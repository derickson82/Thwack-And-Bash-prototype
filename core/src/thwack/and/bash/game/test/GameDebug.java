package thwack.and.bash.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	private Snake snake;

	class SnakeShapeRenderer extends ShapeRenderer implements RendererDebug {
		public void render(SpriteBatch batch) {
			draw((ShapeRenderer)((SnakeDebug)snake).shapeRenderer, batch, 1);
		}
	}
	
	@Override
	public void create() {
		super.create();

		// viewport = new ScreenViewport(Objects.SCREEN_CAMERA);
		// stage = new Stage(viewport);

		GameScreen screen = null;
		// set to any screen that you work on - no need splash or whatever that
		// slows you down! :)
		screen = new PlayScreen();
		setScreen(screen);
		Snake realSnake = ((PlayScreen) screen).getSnake();
		snake = new SnakeDebug(realSnake.getCollisionBody());
		((PlayScreen) screen).setSnake(snake);	//replace with fake snake!
		((SnakeDebug)snake).shapeRenderer = new SnakeShapeRenderer();	//note: it is snake own shapeRenderer, that is the trick! ;)
		((ShapeRenderer)((SnakeDebug)snake).shapeRenderer).setColor(Color.RED);
		// speed up for test ;)
		if (snake != null) {
			snake.setWinderingSpeed(snake.getWinderingSpeed() * 10);
			snake.setDirectionChangeSpeed(1000);
		}
	}

	@Override
	public void update(float delta) {

	}

	public void drawLine(ShapeRenderer targetGO, Vector2 start, Vector2 end) {
		targetGO.begin(ShapeType.Line);
		targetGO.identity();
		float dx = Meters.toPixels(start.x);
		float dy = Meters.toPixels(start.y);
		start.x = dx;
		start.y = dy;
		end.x = dx - snake.getSprite().getWidth()*4/3;
		end.y = dy - snake.getSprite().getHeight()*4/3;
		targetGO.line(start, end);
		targetGO.end();
		
//		targetGO.begin(ShapeType.Filled);
//		targetGO.identity();
//		targetGO.box(Meters.toPixels(start.x), Meters.toPixels(start.y), 0, snake.getSprite().getWidth()/2, snake.getSprite().getHeight()/2, 0);
//		targetGO.end();
	}

	@Override
	public void render() {
		super.render();
		
	}

	//c.f. https://code.google.com/p/libgdx/wiki/scene2d
	public void draw(ShapeRenderer renderer, SpriteBatch batch, float parentAlpha) {
		batch.end();

		renderer.setProjectionMatrix(batch.getProjectionMatrix());
		renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(snake.getX(), snake.getY(), 0);

		// review snake los
		drawLine(renderer, snake.getPosition(), snake
				.getLosFront().getEndLOS());
		//renderer.rect(0, 0, getWidth(), getHeight());

		batch.begin();
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
