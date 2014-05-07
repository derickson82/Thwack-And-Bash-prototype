package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

//Movable entity
public abstract class Mob extends Entity{

    public Mob(MobAnimation mobAnimation, CollisionBody collisionBody) {
	super(new Sprite(mobAnimation.getStartAnimation(0)), collisionBody);
	this.mobAnimation = mobAnimation;
    }

    protected MobAnimation mobAnimation;

    //TODO: ADD MOVEMENT TO BOX2D BODY
    public void move(Vector2 movement){
	getBody().setLinearVelocity(movement);
    }

}
