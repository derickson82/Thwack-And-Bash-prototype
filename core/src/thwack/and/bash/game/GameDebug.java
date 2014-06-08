package thwack.and.bash.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.test.RendererDebug;
import thwack.and.bash.game.util.Util.Objects;

public class GameDebug extends Game implements GameScreen {

	// private Stage stage;
	// private ScreenViewport viewport;
	private Snake snake;

	class SnakeShapeRenderer extends ShapeRenderer implements RendererDebug {
		public void render(SpriteBatch batch) {
			draw((ShapeRenderer)snake.shapeRenderer, batch, 1);
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
		snake = ((PlayScreen) screen).getSnake();
		snake.shapeRenderer = new SnakeShapeRenderer();	//note: it is snake own shapeRenderer, that is the trick! ;)
		((ShapeRenderer)snake.shapeRenderer).setColor(Color.RED);
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
		targetGO.line(start, end);
		targetGO.end();
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

		// review snake los
		drawLine(renderer, snake.getLosFront().getStartLOS(), snake
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
