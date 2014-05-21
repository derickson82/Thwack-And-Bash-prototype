
package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

//Movable entity
public abstract class Mob extends Entity {

	public Mob (MobAnimation mobAnimation, CollisionBody collisionBody) {
		super(new Sprite(mobAnimation.getStartAnimation(0)), collisionBody);
		this.mobAnimation = mobAnimation;
	}

	protected MobAnimation mobAnimation;

	public void move (Vector2 movement) {
		if (movement.x != 0 && movement.y != 0) {
			movement.x = movement.x * 0.75f;
			movement.y = movement.y * 0.75f;
		}
		getBody().setLinearVelocity(movement);
	}

}
