
package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//Movable entity
public abstract class Mob extends Entity {

	protected Rectangle boundingBox;	//can be anything, does not neccessarily be the box of the body (good for unit test)

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Mob (CollisionBody collisionBody) {
		super(null, collisionBody);
	}

	protected final void initMobAnimation(MobAnimation mobAnimation){
		try {
			this.mobAnimation = mobAnimation;
			if(mobAnimation != null) {
				sprite = new Sprite((mobAnimation.getStartAnimation(0)));
			}
		} catch (Exception e) {
			System.err.println("Mob.java error="+e+", hopefully this is just a run from a unit test! ;)");
		}
	}

	protected MobAnimation mobAnimation;

	public abstract MobAnimation createMobAnimation();

	public void move (Vector2 movement) {
		if (movement.x != 0 && movement.y != 0) {
			movement.x = movement.x * 0.75f;
			movement.y = movement.y * 0.75f;
		}
		if(getBody() != null) getBody().setLinearVelocity(movement);
	}

}
