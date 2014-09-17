
package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.PlayerAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.input.PlayerMovement;
import thwack.and.bash.game.screen.PlayScreen;
import thwack.and.bash.game.util.Util;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Mob {

	public Player (CollisionBody collisionBody) {
		super(collisionBody);
		
		try {
			super.initMobAnimation(createMobAnimation());
			String type = Util.PLAYER_ID;	//"player";
			id = type;	//for unit test
			if(collisionBody != null) {
				collisionBody.getBody().setUserData(id);
				collisionBody.getFixture().setUserData(id);
			}
		} catch (Exception e) {
			System.err.println("Player.java error="+e+", hopefully this is just a run from a unit test! ;)");
		}
	}

	@Override
	public void update (float delta) {
		Vector2 movement = PlayerMovement.getMovement(5f);
		if(mobAnimation != null) mobAnimation.update(delta, calculateDirection(movement));
		move(movement);
		if(sprite != null) sprite.setRegion(mobAnimation.getCurrentFrame());
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

	public void updateBoundingBox(Rectangle bodySpec) {
		if(sprite != null) {
			PlayScreen screen = (PlayScreen) Game.getCurrentScreen();
			screen.getPlayer().setBoundingBox(screen.getPlayer().getSprite().getBoundingRectangle());
		}
		
		if(COLLISION_BODY != null) {
			boundingBox.x = Meters.toPixels(getBody().getPosition().x) - bodySpec.width/2;
			boundingBox.y = Meters.toPixels(getBody().getPosition().y) - bodySpec.height/2;
			//keep the following to debug bounding box properly (in white)
			//boundingBox.x = Meters.toPixels(getBody().getPosition().x);
			//boundingBox.y = Meters.toPixels(getBody().getPosition().y);
		} else {
			boundingBox.x = position.x;
			boundingBox.y = position.y;
		}
		
	}

}
