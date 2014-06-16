
package thwack.and.bash.game.test;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.entity.mob.Bat;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.level.Level;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.test.GameDebug;
import thwack.and.bash.game.ui.GameUI;
import thwack.and.bash.game.util.Util.Box2D;
import thwack.and.bash.game.util.Util.Objects;
import thwack.and.bash.game.util.Util.Pixels;
import thwack.and.bash.game.util.Util.Values;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PlayScreenDebug extends PlayScreen {

	@Override
	public void show () {

//		box2DRenderer = new Box2DDebugRenderer();

//		world = new World(new Vector2(0, 0), false);
		world = null;	//all logic must work without a real world - ala TDD style!

		player = new Player(null);

		bat = new Bat(null);
		
		snake = new Snake(null);

//		gameUI = new GameUI();

//		Level.load("demo2.tmx", world);

	}

}
