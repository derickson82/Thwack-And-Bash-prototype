
package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.BatAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.mob.ai.AI;
import thwack.and.bash.game.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bat extends Mob {

	public Bat (CollisionBody collisionBody) {
		super(collisionBody);
		super.initMobAnimation(createMobAnimation());
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
		if (ai.getState() == BatState.FLYING_AND_MOVING.STATE) {
			move(movement);
		}
	}

	private void updateAI () {
		ai.setState(MathUtils.random(0, 1));

		if (ai.getState() == BatState.FLYING.STATE) {
			movement.set(0, 0);
		}

		else if (ai.getState() == BatState.FLYING_AND_MOVING.STATE) {
			int x = MathUtils.random(0, 2) - 1;
			int y = MathUtils.random(0, 2) - 1;
			movement.set(x, y);
		}

	}

	private enum BatState {
		FLYING(0), FLYING_AND_MOVING(1);
		BatState (int state) {
			STATE = state;
		}

		private final int STATE;

	}

	@Override
	public MobAnimation createMobAnimation () {
		Texture flyingRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/bat_64x62.png"));
		TextureRegion[][] flyingRegions2DArray = TextureRegion.split(flyingRegionsSheet, 64, 52);
		TextureRegion[] flyingRegionsArray = Util.toArray(flyingRegions2DArray, 3, 1);

		MobAnimation batAnimation = new MobAnimation();
		batAnimation.beginSettingAnimations();
		batAnimation.setAnimation(new Animation(.1f, flyingRegionsArray), BatAnimationType.FLYING.ID);
		batAnimation.setStillAnimationFrame(1);
		batAnimation.endSettingAnimations();
		return batAnimation;
	}

}
