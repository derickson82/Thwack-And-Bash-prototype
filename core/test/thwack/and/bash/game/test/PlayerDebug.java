package thwack.and.bash.game.test;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayerDebug extends Player implements MobDebug {

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

	public PlayerDebug(CollisionBody collisionBody) {
		super(collisionBody);
		
		if(sprite != null) {
			boundingBox = sprite.getBoundingRectangle();
//			boundingBox.x = sprite.getX();
//			boundingBox.y = sprite.getY();
		}
	}

	@Override
	public void setPosition (Vector2 pos) {
		super.setPosition(pos);

		position = pos;
	}

	@Override
	public void setPosition (float x, float y) {
		super.setPosition(x, y);
		
		position.x = x;
		position.y = y;
	}

	@Override
	public float getX () {
		float ret = -1;
		
		if(getBody() != null) {
			ret = getBody().getPosition().x;
		} else {
			ret = boundingBox.getX();
		}
		return ret;
	}

	@Override
	public float getY () {
		float ret = -1;
		
		if(getBody() != null) {
			ret = getBody().getPosition().y;
		} else {
			ret = boundingBox.getY();
		}
		return ret;
	}

	@Override
	public Vector2 getPosition() {
		Vector2 ret = null;
		
		if(getBody() != null) {
			ret = getBody().getPosition();
		} else {
			ret = new Vector2(boundingBox.getX(), boundingBox.getY());
		}
		return ret;
	}
	
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

	@Override
	protected void updateBoundingBox() {
		if(COLLISION_BODY != null) {
			boundingBox.x = Meters.toPixels(getBody().getPosition().x);
			boundingBox.y = Meters.toPixels(getBody().getPosition().y);
		} else {
			boundingBox.x = position.x;
			boundingBox.y = position.y;
		}
	}

	@Override
	public void move (Vector2 movement) {
		super.move(movement);

		position = movement;
	}
}