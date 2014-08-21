
package thwack.and.bash.game.test;

import com.badlogic.gdx.math.Rectangle;

import thwack.and.bash.game.Game;
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
		try {
			bat = new Bat(null);
		} catch (Exception e) {
			//e.printStackTrace();
			//no one cares about these entities (at least for now)
		}

		try {
			player = new PlayerDebug(null);
		} catch (Exception e) {
			//e.printStackTrace();
			//it possibly will crash, yadah yadah we know, but no one cares in unit test world!
		}
		player.setId(SnakeCollisionHelper.PLAYER_ID);
		Rectangle playerBoundingBox = new Rectangle(200, 300, 32, 32);
		player.setBoundingBox(playerBoundingBox);	//mock it, hell ya

		try {
			snake = new SnakeDebug(null);
		} catch (Exception e) {
			//e.printStackTrace();
			//it possibly will crash, yadah yadah we know, but no one cares in unit test world!
		}
//		float mW = 32;
//		float mH = 32;
//		snake.setModWidth(mW);
//		snake.setModHeight(mH);
		snake.setId(SnakeCollisionHelper.SNAKE_ID);
		Rectangle snakeBoundingBox = new Rectangle(100, 100, 32, 32);
		snake.setBoundingBox(snakeBoundingBox);

	}

}
