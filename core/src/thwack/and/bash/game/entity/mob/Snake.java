package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.BatAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.mob.ai.AI;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Snake extends Mob {

	public Snake(MobAnimation animation, CollisionBody collisionBody) {
		super(animation, collisionBody);
		ai = new AI();
		movement = new Vector2(0, 0);
	}

	private AI ai;
	private Vector2 movement;
	private float time = 0;

	@Override
	public void update(float delta) {
		mobAnimation.update(delta, BatAnimationType.FLYING.ID);
		SPRITE.setRegion(mobAnimation.getCurrentFrame());
		time += delta;
		if (time >= 1) {
			updateAI();
			time = 0;
		}
		if (ai.getState() == State.SIDE_WINDERING.STATE) {
			move(movement);
		}
	}

	private void updateAI() {
		ai.setState(MathUtils.random(0, 1));

		if (ai.getState() == State.STOP.STATE) {
			movement.set(0, 0);
		}

		else if (ai.getState() == State.SIDE_WINDERING.STATE) {
			int x = MathUtils.random(0, 2) - 1;
			int y = MathUtils.random(0, 2) - 1;
			movement.set(x, y);
		}

	}

	//TODO is this necessary, we already have an enum type in thwack.and.bash.game.animation.types.SnakeAnimationType, can we reuse that?
	private enum State {
		STOP(0), SIDE_WINDERING(1);
		State(int state) {
			STATE = state;
		}

		private final int STATE;

	}

}
