
package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.BatAnimationType;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.mob.ai.AI;
import thwack.and.bash.game.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Snake extends Mob {

	public Snake (CollisionBody collisionBody) {
		super(collisionBody);
		ai = new AI();
		movement = new Vector2(0, 0);
	}

	private AI ai;
	private Vector2 movement;
	private float time = 0;

	@Override
	public void update (float delta) {
		mobAnimation.update(delta, BatAnimationType.FLYING.ID);
		sprite.setRegion(mobAnimation.getCurrentFrame());
		time += delta;
		if (time >= 1) {
			updateAI();
			time = 0;
		}
		if (ai.getState() == State.SIDE_WINDERING.STATE) {
			move(movement);
		}
	}

	private void updateAI () {
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

	// TODO is this necessary, we already have an enum type in thwack.and.bash.game.animation.types.SnakeAnimationType, can we
// reuse that?
	private enum State {
		STOP(0), SIDE_WINDERING(1);
		State (int state) {
			STATE = state;
		}

		private final int STATE;

	}

	@Override
	public MobAnimation createMobAnimation () {
		Texture side_winderingRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/snake-walking_84x64.png"));
		TextureRegion[][] side_winderingRegions2DArray = TextureRegion.split(side_winderingRegionsSheet, 84, 64);
		TextureRegion[] side_winderingRegionsArray = Util.toArray(side_winderingRegions2DArray, 2, 1);

		MobAnimation snakeAnimation = new MobAnimation();
		snakeAnimation.beginSettingAnimations();
		snakeAnimation.setAnimation(new Animation(.1f, side_winderingRegionsArray), SnakeAnimationType.SIDE_WINDERING.ID);
		snakeAnimation.setStillAnimationFrame(1);
		snakeAnimation.endSettingAnimations();
		return snakeAnimation;
	}

	//

}
