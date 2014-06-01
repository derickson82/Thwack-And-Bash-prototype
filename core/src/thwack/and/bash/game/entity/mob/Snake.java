package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.SnakeChangingDirection;
import thwack.and.bash.game.animation.SnakeWinding;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.mob.ai.AI;
import thwack.and.bash.game.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Snake extends Mob {

	private float WINDERING_INTERVAL = .5f;	//lower is faster
	private static int surWidth = 20;	//generally it's the length of the first element (columns) of the tmx but does not have to be
	private static int surHeight = 18;	//gennerally the total rows of the tmx but does not have to be
	final int TILE_SIZE = 32;	//this generally is fixed during the game and could be retrieved from a common constant

	public static int getSurWidth() {
		return surWidth;
	}

	public static void setSurWidth(int surWidth) {
		Snake.surWidth = surWidth;
	}

	public static int getSurHeight() {
		return surHeight;
	}

	public void setSurHeight(int surHeight) {
		this.surHeight = surHeight;
	}
	
	public Snake(CollisionBody collisionBody) {
		super(collisionBody);
		super.initMobAnimation(createMobAnimation());
		ai = new AI();
		movement = new Vector2(0, 0);
		ai.setState(SnakeAnimationType.WINDERING.ID);	//if it is not idling, it better be moving!
	}

	private AI ai;
	private Vector2 movement;
	private float time = 0;
	private float wTime = 0;
	private Vector2 lastMovement;
	private float winderingSpeed = 8;
	private float directionChangeSpeed = 100;
	private double idlingCounter = 0;
	private double idlingPeriod = 8;	//in seconds
	private float originalBodyDirection = -1;
	
	public float getWinderingSpeed() {
		return winderingSpeed;
	}

	public void setWinderingSpeed(float winderingSpeed) {
		this.winderingSpeed = winderingSpeed;
	}
	
	public float getDirectionChangeSpeed() {
		return directionChangeSpeed;
	}

	public void setDirectionChangeSpeed(float directionChangeSpeed) {
		this.directionChangeSpeed = directionChangeSpeed;
	}

	@Override
	public void update(float delta) {
		time += delta;
		wTime += delta;
		if (wTime >= WINDERING_INTERVAL) {
			mobAnimation.update(wTime, SnakeAnimationType.IDLING.ID);
			sprite.setRegion(mobAnimation.getCurrentFrame());
			wTime = 0;
		}
		if (time >= 1) {
			updateAI(delta);
			time = 0;
		}
		move(movement);
	}

	private void updateAI(float delta) {
		if (ai.getState() == State.IDLING.STATE) {
			idlingCounter++;
		}
		if(lastMovement == null || ai.getState() == SnakeAnimationType.WINDERING.ID) {
			SnakeWinding m1 = new SnakeWinding(movement.x, movement.y);
			m1.move(winderingSpeed , 210);
			movement.set(m1.getX(), m1.getY());
		} else {
			//change direction
			SnakeChangingDirection m2 = new SnakeChangingDirection(movement.x, movement.y);
			m2.move(directionChangeSpeed, -1, delta);
			sprite.setOrigin(m2.getX()/2, m2.getY()/2);
			//getBody().setFixedRotation(true);
			//sprite.rotate(m2.getAngle());
			if(originalBodyDirection == -1) {
				originalBodyDirection = getBody().getAngle();
			}
			getBody().setTransform(getBody().getPosition(), m2.getAngle());

			movement.set(m2.getX(), m2.getY());
		}
		if(lastMovement == movement) {
			ai.setState(SnakeAnimationType.IDLING.ID);
		}
		if (idlingCounter > idlingPeriod ) {
			getBody().setTransform(getBody().getPosition(), originalBodyDirection);
			ai.setState(SnakeAnimationType.WINDERING.ID);
		}
		lastMovement = movement;
		
		//System.out.println(this.getClass() +": state = " + ai.getState());
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
