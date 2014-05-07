package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.BatAnimationType;
import thwack.and.bash.game.collision.CollisionBody;

public class Bat extends Mob{

    public Bat(MobAnimation animation, CollisionBody collisionBody) {
	super(animation, collisionBody);
    }

    @Override
    public void update(float delta) {
	mobAnimation.update(delta, BatAnimationType.FLYING.ID);
	SPRITE.setRegion(mobAnimation.getCurrentFrame());
    }

}
