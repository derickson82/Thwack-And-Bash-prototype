package thwack.and.bash.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.screen.GameScreen;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.util.Util.Objects;

public class GameDebug extends Game implements GameScreen {

//	private Stage stage;
//	private ScreenViewport viewport;
	
	@Override
	public void create () {
		super.create();
		
//		viewport = new ScreenViewport(Objects.SCREEN_CAMERA);
//		stage = new Stage(viewport);

		GameScreen screen = null;
		//set to any screen that you work on - no need splash or whatever that slows you down! :)
		screen = new PlayScreen();
		setScreen(screen);
		Snake snake = ((PlayScreen)screen).getSnake();
		//speed up for test ;)
		if(snake != null) {
			snake.setWinderingSpeed(snake.getWinderingSpeed()*10);
			snake.setDirectionChangeSpeed(1000);
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


}
