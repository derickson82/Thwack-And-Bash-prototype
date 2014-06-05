
package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.PlayerAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.input.PlayerMovement;
import thwack.and.bash.game.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends Mob {

	public Player (CollisionBody collisionBody) {
		super(collisionBody);
		super.initMobAnimation(createMobAnimation());
		collisionBody.getBody().setUserData("player");
		collisionBody.getFixture().setUserData("player");
	}

	@Override
	public void update (float delta) {
		Vector2 movement = PlayerMovement.getMovement(5f);
		mobAnimation.update(delta, calculateDirection(movement));
		move(movement);
		sprite.setRegion(mobAnimation.getCurrentFrame());
	}

	// TODO: LET ANDREW DECIDE WHICH DIRECTIONS SHOULD BE IN FAVOR if two or more keys are pressed
	private int calculateDirection (Vector2 m) {
		if (m.x < 0) {
			return PlayerAnimationType.LEFT.ID;
		} else if (m.x > 0) {
			return PlayerAnimationType.RIGHT.ID;
		} else if (m.y > 0) {
			return PlayerAnimationType.UP.ID;
		} else if (m.y < 0) {
			return PlayerAnimationType.DOWN.ID;
		} else {
			return -1;
		}
	}

	@Override
	public MobAnimation createMobAnimation () {
		Texture upRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-upward_35x46.png"));
		TextureRegion[][] upRegions2DArray = TextureRegion.split(upRegionsSheet, 35, 46);
		TextureRegion[] upRegionsArray = Util.toArray(upRegions2DArray, 3, 1);

		Texture leftRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-sideways_33x46.png"));
		TextureRegion[][] leftRegions2DArray = TextureRegion.split(leftRegionsSheet, 33, 46);
		TextureRegion[] leftRegionsArray = Util.toArray(leftRegions2DArray, 3, 1);

		Texture downRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-downard_35x45.png"));
		TextureRegion[][] downRegions2DArray = TextureRegion.split(downRegionsSheet, 35, 45);
		TextureRegion[] downRegionsArray = Util.toArray(downRegions2DArray, 3, 1);

		Texture rightRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-sideways_33x46.png"));
		TextureRegion[][] rightRegions2DArray = TextureRegion.split(rightRegionsSheet, 33, 46);
		TextureRegion[] rightRegionsArray = Util.toArray(rightRegions2DArray, 3, 1);
		Util.flipRegions(rightRegionsArray, true, false);

		MobAnimation playerAnimation = new MobAnimation();
		playerAnimation.beginSettingAnimations();
		playerAnimation.setAnimation(new Animation(.333f, upRegionsArray), PlayerAnimationType.UP.ID);
		playerAnimation.setAnimation(new Animation(.333f, leftRegionsArray), PlayerAnimationType.LEFT.ID);
		playerAnimation.setAnimation(new Animation(.333f, downRegionsArray), PlayerAnimationType.DOWN.ID);
		playerAnimation.setAnimation(new Animation(.333f, rightRegionsArray), PlayerAnimationType.RIGHT.ID);
		playerAnimation.setStillAnimationFrame(1);
		playerAnimation.endSettingAnimations();
		return playerAnimation;
	}

}
