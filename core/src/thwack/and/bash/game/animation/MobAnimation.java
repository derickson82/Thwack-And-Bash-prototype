
package thwack.and.bash.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

//Starting with three animation, one for each direction
//TODO: ADD SPRITE ANIMATION RETURN CAPABILITY
public class MobAnimation {

	public MobAnimation () {
		animations = new ObjectMap<Integer, Animation>();
	}


	private ObjectMap<Integer, Animation> animations;
	private TextureRegion currentFrame;

	private int stillFrame = 0;
	private int lastAnimationKey;
	private int animationKey;
	private float stateTime = 0;
	private boolean addingAnimations;

	// TODO: ADD ATTACKING STATE AND SUCH
	public void update (float delta, int newAnimationKey) {
		if (addingAnimations) {
			System.err.println("You need to call endAddingAnimations() in MOBAnimations class before you can update it");
		}
		if (newAnimationKey != -1) {
			animationKey = newAnimationKey;
			if (lastAnimationKey != animationKey) {
				stateTime = -delta;
				lastAnimationKey = animationKey;
			}
			stateTime += delta;
		} else {
			stateTime = stillFrame * animations.get(animationKey).frameDuration;
		}
		currentFrame = animations.get(animationKey).getKeyFrame(stateTime, true);
	}

	public TextureRegion getCurrentFrame () {
		return currentFrame;
	}

	public void beginSettingAnimations () {
		addingAnimations = true;
	}

	public void setAnimation (Animation animation, int animationKey) {
		if (addingAnimations) {
			animations.put(animationKey, animation);
		} else {
			System.err.println("You need to call startAddingAnimations() in MOBAnimations class before you can update it");
		}

	}

	public void setStillAnimationFrame (int frame) {
		if (addingAnimations) {
			stillFrame = frame;
		} else {
			System.err.println("You need to call startAddingAnimations() in MOBAnimations class before you can update it");
		}
	}

	public void endSettingAnimations () {
		addingAnimations = false;
	}

	// TODO: FIX THIS METHOD; IT'S WEIRD
	public TextureRegion getStartAnimation (int animationKey) {
		return animations.get(animationKey).getKeyFrames()[0];
	}

}
