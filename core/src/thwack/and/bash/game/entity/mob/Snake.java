package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.SnakeMove1;
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

	public Snake(CollisionBody collisionBody) {
		super(collisionBody);
		super.initMobAnimation(createMobAnimation());
		ai = new AI();
		movement = new Vector2(0, 0);
	}

	private AI ai;
	private Vector2 movement;
	private float time = 0;

	@Override
	public void update(float delta) {
		mobAnimation.update(delta, SnakeAnimationType.IDLING.ID);
		sprite.setRegion(mobAnimation.getCurrentFrame());
		time += delta;
		if (time >= 1) {
			updateAI();
			time = 0;
		}
		if (ai.getState() == State.WINDERING.STATE) {
			move(movement);
		}
	}

	private void updateAI() {
		ai.setState(SnakeAnimationType.WINDERING.ID);

		SnakeMove1 m1 = new SnakeMove1(movement.x, movement.y);
		m1.move(8, 225);
		movement.set(m1.getX(), m1.getY());
	}

	//TODO is this necessary, we already have an enum type in thwack.and.bash.game.animation.types.SnakeAnimationType, can we reuse that?
	private enum State {
		IDLING(0), WINDERING(1);
		State(int state) {
			STATE = state;
		}

		private final int STATE;

	}

	@Override
	public MobAnimation createMobAnimation () {
		Texture windingRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/snake-walking_84x64.png"));
		TextureRegion[][] windingRegions2DArray = TextureRegion.split(windingRegionsSheet, 84, 64);
		TextureRegion[] windingRegionsArray = Util.toArray(windingRegions2DArray, 3, 1);

		MobAnimation snakeAnimation = new MobAnimation();
		snakeAnimation.beginSettingAnimations();
		snakeAnimation.setAnimation(new Animation(.1f, windingRegionsArray), SnakeAnimationType.IDLING.ID);
		snakeAnimation.setStillAnimationFrame(1);
		snakeAnimation.endSettingAnimations();
		return snakeAnimation;
	}

}
