package thwack.and.bash.game.test;

import thwack.and.bash.game.animation.AnimatedSprite;
import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.SnakeChangingDirection;
import thwack.and.bash.game.animation.SnakeWinding;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.collision.SnakeRaycastGuard;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.entity.mob.ai.AI;
import thwack.and.bash.game.test.RendererDebug;
import thwack.and.bash.game.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SnakeDebug extends Snake implements MobDebug {

	private GameDebug gameDebug;
	private RendererDebug shapeRenderer;
	private Vector2 position = new Vector2();

	public GameDebug getGameDebug() {
		return gameDebug;
	}

	public void setGameDebug(GameDebug gameDebug) {
		this.gameDebug = gameDebug;
	}
	
	public RendererDebug getShapeRenderer() {
		return shapeRenderer;
	}

	public void setShapeRenderer(RendererDebug shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}

	public SnakeDebug(CollisionBody collisionBody) {
		super(collisionBody);
		// TODO Auto-generated constructor stub
	}

	//BEGIN override Entity's
	@Override
	public Vector2 getPosition () {
		return position;
	}

	public void setPosition (Vector2 pos) {
		position = pos;
	}

	public void setPosition (float x, float y) {
		position.x = x;
		position.y = y;
	}
	//END

	@Override
	public void draw (SpriteBatch batch) {
		super.draw(batch);
		
		if(gameDebug == null) {
			System.err.println(this.getClass().getName() + ": Game debug is null or empty!");
			System.exit(0);
		}

		if(shapeRenderer != null) {
			((RendererDebug)shapeRenderer).render(batch, gameDebug, this);
		}
	}

}