package thwack.and.bash.game.test;

import thwack.and.bash.game.entity.mob.Snake;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SnakeShapeRenderer extends ShapeRenderer implements RendererDebug {
	public void render(SpriteBatch batch, GameDebug game, MobDebug mob) {
		SnakeDebug s = (SnakeDebug)mob;
		game.draw((ShapeRenderer)s.getShapeRenderer(), batch, 1);
	}
}