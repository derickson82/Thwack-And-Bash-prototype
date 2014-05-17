package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.PlayerAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.input.PlayerMovement;

import com.badlogic.gdx.math.Vector2;

public class Player extends Mob{

    public Player(MobAnimation animation, CollisionBody collisionBody) {
	super(animation, collisionBody);
	collisionBody.getBody().setUserData("player");
	collisionBody.getFixture().setUserData("player");
    }


    @Override
    public void update(float delta) {
	Vector2 movement = PlayerMovement.getMovement(5f);
	mobAnimation.update(delta, calculateDirection(movement));
	move(movement);
	SPRITE.setRegion(mobAnimation.getCurrentFrame());
    }

    //TODO: LET ANDREW DECIDE WHICH DIRECTIONS SHOULD BE IN FAVOR if two or more keys are pressed
    private int calculateDirection(Vector2 m){
	if(m.x < 0){
	    return PlayerAnimationType.LEFT.ID;
	}
	else if(m.x > 0){
	    return PlayerAnimationType.RIGHT.ID;
	}
	else if(m.y > 0){
	    return PlayerAnimationType.UP.ID;
	}
	else if(m.y < 0){
	    return PlayerAnimationType.DOWN.ID;
	} else {
	    return -1;
	}
    }

}
