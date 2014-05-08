package thwack.and.bash.game.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


//Starting with three animation, one for each direction
//TODO: ADD SPRITE ANIMATION RETURN CAPABILITY
public class MobAnimation {

    public MobAnimation(){
	animations = new Animation[ANIMATIONS];
    }

    private static final int ANIMATIONS = 4;

    private Animation[] animations;
    private TextureRegion currentFrame;

    private int stillFrame = 0;
    private int lastDir;
    private int dir;
    private float stateTime = 0;
    private boolean addingAnimations;

    //TODO: ADD ATTACKING STATE AND SUCH
    public void update(float delta, int newDir){
	if(addingAnimations){
	    System.err.println("You need to call endAddingAnimations() in MOBAnimations class before you can update it");
	}
	if(newDir != -1){
	    dir = newDir;
	    if(lastDir != dir){
		stateTime = -delta;
		lastDir = dir;
	    }
	    stateTime += delta;
	} else {
	    stateTime = stillFrame * animations[dir].frameDuration;
	}
	currentFrame = animations[dir].getKeyFrame(stateTime, true);
    }

    public TextureRegion getCurrentFrame(){
	return currentFrame;
    }

    public void beginSettingAnimations(){
	addingAnimations = true;
    }

    public void setAnimation(Animation animation, int id){
	if(addingAnimations) {
	    animations[id] = animation;
	} else {
	    System.err.println("You need to call startAddingAnimations() in MOBAnimations class before you can update it");
	}

    }

    public void setStillAnimationFrame(int frame){
	if(addingAnimations) {
	    stillFrame = frame;
	} else {
	    System.err.println("You need to call startAddingAnimations() in MOBAnimations class before you can update it");
	}
    }

    public void endSettingAnimations(){
	addingAnimations = false;
    }

    //TODO: FIX THIS METHOD; IT'S WEIRD
    public TextureRegion getStartAnimation(int ID){
	return animations[ID].getKeyFrames()[0];
    }







}
