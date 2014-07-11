
package thwack.and.bash.game.entity;

import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/*
 * EVERYTHING IS IN METERS
 * 
 * TODO: Finish entity
 * TODO: Collision handler, if a entity touches another entity
 */
public abstract class Entity {

	public Entity (Sprite sprite, CollisionBody body) {
		this.sprite = sprite;
		COLLISION_BODY = body;
		entities.add(this);
	}

	protected String id;
	protected Vector2 position;
	protected Sprite sprite;
	protected final CollisionBody COLLISION_BODY;

	// TODO: FIX THIS : TEST METHOD TO SOLVE A PROBLEM;
	private static Array<Entity> entities = new Array<Entity>();

	public static Entity getEntity (String name) {
		for (Entity e : entities) {
			if (e.getBody().getUserData().equals(name) || e.getBody().getUserData().equals(name)) {
				return e;
			}
		}
		System.out.println("Couldn't find entity with name " + name);
		return null;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void draw (SpriteBatch batch) {
		if (sprite != null && sprite.getTexture() != null && getBody() != null) {
			sprite.setPosition(Meters.toPixels(getBody().getPosition().x) - sprite.getWidth() / 2, Meters.toPixels(getBody().getPosition().y) - sprite.getHeight() / 2);
			sprite.setRotation((float)Math.toDegrees(getBody().getAngle()));
			sprite.draw(batch);
		} else {
//			System.out.println("Entity with the name + " + COLLISION_BODY.getBody().getUserData() + "'s Texture is null");
			System.out.println("Entity with the id [" + id + "] either it's sprite, texture or body is null: sprite [" + sprite + "] body [" + getBody() + "]");
		}
	}

	public abstract void update (float delta);

	// public abstract void interactionWith(Entity e);

	public void setX (float x) {
		setPosition(x, getY());
	}

	public void setY (float y) {
		setPosition(getX(), y);
	}

	public void setPosition (Vector2 pos) {
		if (getBody()!=null) {
			getBody().setTransform(pos, getBody().getAngle());
		} else {
			position = pos;
		}
	}

	public void setPosition (float x, float y) {
		if(getBody() != null) {
			getBody().setTransform(x, y, getBody().getAngle());
		} else {
			Vector2 pos = new Vector2();
			pos.x = x;
			pos.y = y;
			position = pos;
		}
	}

	public void addToX (float x) {
		addToPosition(x, 0);
	}

	public void addToY (float y) {
		addToPosition(0, y);
	}

	public void addToPosition (float x, float y) {
		getBody().setTransform(x + getX(), y + getY(), 0);
	}

	public Vector2 getPosition () {
		return getBody().getPosition();
	}

	public Sprite getSprite () {
		return sprite;
	}

	public Body getBody () {
		return COLLISION_BODY!=null?COLLISION_BODY.getBody():null;
	}

	public Fixture getFixture () {
		return COLLISION_BODY.getFixture();
	}

	public CollisionBody getCollisionBody () {
		return COLLISION_BODY;
	}

	public float getX () {
		return getBody().getPosition().x;
	}

	public float getY () {
		return getBody().getPosition().y;
	}

}
