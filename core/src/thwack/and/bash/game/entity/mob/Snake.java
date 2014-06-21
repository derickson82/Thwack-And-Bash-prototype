package thwack.and.bash.game.entity.mob;

import thwack.and.bash.game.animation.AnimatedSprite;
import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.SnakeChangingDirection;
import thwack.and.bash.game.animation.SnakeWinding;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.collision.SnakeBoundingBoxGuard;
import thwack.and.bash.game.collision.SnakeBox2dGuard;
import thwack.and.bash.game.collision.SnakeCollisionHelper;
import thwack.and.bash.game.collision.SnakeGuard;
import thwack.and.bash.game.collision.SnakeRaycastGuard;
import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.entity.mob.ai.AI;
import thwack.and.bash.game.util.Util;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class Snake extends Mob {

	private float WINDERING_INTERVAL = .25f;	//lower is faster
	private static int surWidth = 20;	//generally it's the length of the first element (columns) of the tmx but does not have to be
	private static int surHeight = 18;	//gennerally the total rows of the tmx but does not have to be
	final int TILE_SIZE = 32;	//this generally is fixed during the game and could be retrieved from a common constant
	final float PI = 3.1415f;
	float mobWidth = 0;
	float mobHeight = 0;
	float mobX = -1;	//initial pos
	float mobY = -1;	//initial pos
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

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
	
	public float getModWidth() {
		return mobWidth;
	}

	public void setModWidth(float mobWidth) {
		this.mobWidth = mobWidth;
	}

	public float getModHeight() {
		return mobHeight;
	}

	public void setModHeight(float mobHeight) {
		this.mobHeight = mobHeight;
	}

	public AI getAi() {
		return ai;
	}

	public Snake(CollisionBody collisionBody) {
		super(collisionBody);
		super.initMobAnimation(createMobAnimation());
		String type = SnakeCollisionHelper.SNAKE_ID;
		id = type;	//for unit test
		if(collisionBody != null) {
			collisionBody.getBody().setUserData(id);
			collisionBody.getFixture().setUserData(id);
			setWorld(collisionBody.getBody().getWorld());
	        world.setContactListener((ContactListener) los);
		}
		//line of sight setup
		Vector2 foreHeadPos = new Vector2().set(mobWidth/2, mobHeight/2);	//supposed to be its head but set it to be casting from its tummy instead :)
		Vector2 furthestFrontSightPos = new Vector2().set(mobX - mobWidth*2, mobY + mobHeight);	//yes, it can only see so much!
//		losFront = new SnakeRaycastGuard(foreHeadPos, furthestFrontSightPos);	//TODO this is the crapiest guard ever invented!
//		los = new SnakeBox2dGuard(foreHeadPos, furthestFrontSightPos);
		los = new SnakeBoundingBoxGuard();
		ai = new AI();
		movement = new Vector2(0, 0);
		ai.setState(SnakeAnimationType.WINDERING.ID);	//if it is not idling, it better be moving!
//		ai.setState(SnakeAnimationType.ATTACK.ID);

		if(losFront == null && los == null) {
			System.err.println("You need at least one guard for the snake. Otherwise, collision detection would not work!");
		}
	}

	private AI ai;
	private Vector2 movement;
	private float time = 0;
	private float wTime = 0;
	private Vector2 lastMovement;
	private float winderingSpeed = 20;
	private float directionChangeSpeed = .5f;
	private double idlingCounter = 0;
	private double attackCounter = 0;
	private double idlingPeriod = 2;	//in seconds
	private float originalBodyDirection = -1;
	private double attackPeriod = 3000;
	private int totalAttackFrames = 0;
//	private int STILL_FRAME_FLAG = -1;
	private int STILL_FRAME_INDEX = 0;
	MobAnimation snakeAnimation;
	Animation nativeAnimation;
	TextureRegion[] windingRegionsArray;
	private SnakeGuard losFront;
//	private SnakeGuard losLeft;
//	private SnakeGuard losRight;
	private SnakeGuard los;
	private World world;

	public SnakeGuard getLosFront() {
		return losFront;
	}

	public void setLosFront(SnakeGuard losFront) {
		this.losFront = losFront;
	}

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
	public void draw (SpriteBatch batch) {
		super.draw(batch);
		
//		handleCollision(getPosition(), losFront.getEndLOS());
	}
	
	/** TODO this cause box2d to crash !!! */
	public void handleCollision(Vector2 start, Vector2 end) {
		//LOS
		float dx = Meters.toPixels(start.x);
		float dy = Meters.toPixels(start.y);
		start.x = dx;
		start.y = dy;
		end.x = dx - getSprite().getWidth()*4/3;
		end.y = dy - getSprite().getHeight()*4/3;

		//collision point with normal line
		getLosFront().setStartLOS(start);
		getLosFront().setStartLOS(end);
		start.x = Meters.toPixels(((Vector2)getLosFront().getCollision()).x);
		start.y = Meters.toPixels(((Vector2)getLosFront().getCollision()).y);
		end.x = Meters.toPixels(((Vector2)getLosFront().getNormal()).x);
		end.y = Meters.toPixels(((Vector2)getLosFront().getNormal()).y);
	}

	@Override
	public void move (Vector2 movement) {
		super.move(movement);

//		if (ai.getState() != State.IDLING.STATE) {
//			if (movement.x != 0 && movement.y != 0) {
//				movement.x = movement.x * 0.75f;
//				movement.y = movement.y * 0.75f;
//			}
//			getBody().setLinearVelocity(movement);
//		}
//		else
//		if(ai.getState() == SnakeAnimationType.REVERSE.ID) {
//		}
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

		if(world != null) {
			if(losFront != null) { 
				world.rayCast((RayCastCallback) losFront, losFront.getStartLOS(), losFront.getEndLOS());
			}
		}
		else
		if(los != null) {
			Entity collided = los.hit(movement.x, movement.y);
		}
	}

	private Vector2 translate(Vector2 pos, Vector2 offset) {
		Vector2 newPos = new Vector2();
		newPos.x = pos.x + offset.x;
		newPos.y = pos.y + offset.y;
		
		return newPos;
	}
	
	private float toRadian(float degree) {
		return (degree/180)*PI;
	}
	
	private float toDegree(float radian) {
		return radian*180/PI;
	}

	public void updateAI(float delta) {
		if (ai.getState() == State.IDLING.STATE) {
			//mobAnimation.update(delta, STILL_FRAME_FLAG);	//this does not work for some reason, oh well until then
			idlingCounter++;
		}
		else
		if (ai.getState() == State.ATTACK.STATE) {
			AnimatedSprite attackSprite = new AnimatedSprite("textureatlas/play/input/snake-attack_79x41.png", 1, 3);
			totalAttackFrames = attackSprite.getFrameCols()*attackSprite.getFrameCols();
			sprite.setRegion(attackSprite.getAnimation().getKeyFrame(delta%totalAttackFrames));
			attackCounter++;
		}
//		else
//		if(ai.getState() == SnakeAnimationType.REVERSE.ID) {
//			SnakeWinding m1 = new SnakeWinding(movement.x, movement.y);
//			m1.move(winderingSpeed , 10);
//			movement.set(m1.getX(), m1.getY());
//		}
		else
		if(lastMovement == null || ai.getState() == SnakeAnimationType.WINDERING.ID) {
			SnakeWinding m1 = new SnakeWinding(movement.x, movement.y);
			m1.move(winderingSpeed , 210);
			movement.set(m1.getX()/2, m1.getY()/2);
			Vector2 pos = getPosition();
			if(pos != null) {
				Gdx.app.log(this.getClass().getName(), "current pos [" + pos.x + "," + pos.y + "]");
			} else {
				Gdx.app.log(this.getClass().getName(), "Snake current position is null/empty!?");
			}
		}
		else {
			//change direction
			SnakeChangingDirection m2 = new SnakeChangingDirection(movement.x, movement.y);
			m2.move(directionChangeSpeed, -1, delta);
			sprite.setOrigin(m2.getX()/2, m2.getY()/2);
			//getBody().setFixedRotation(true);
			//sprite.rotate(m2.getAngle());
			if(originalBodyDirection == -1) {
				originalBodyDirection = getBody().getAngle();
			}
//			getBody().setTransform(getBody().getPosition(), toRadian(getBody().getAngle())*delta);
			getBody().setTransform(m2.getPosition(), toRadian(m2.getAngle())*delta);
		}
		
//		if(getBody().getPosition().x < 0) {
//			//reverse the snake direction
//			ai.setState(SnakeAnimationType.REVERSE.ID);
//		}
//		if(getBody().getPosition().y > surHeight/2) {
//			//reverse the snake direction
//			movement.set(-movement.x, -movement.y);
//		}
		if(los.isPlayerNearby()) {
			ai.setState(SnakeAnimationType.ATTACK.ID);
			//attackCounter = 0;
		}
//		else {
//			ai.setState(SnakeAnimationType.WINDERING.ID);
//		}
		else
		if(lastMovement == movement) {
			try {
				doNothing();
				ai.setState(SnakeAnimationType.IDLING.ID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (idlingCounter > idlingPeriod ) {
			try {
				move();
				ai.setState(SnakeAnimationType.WINDERING.ID);
				idlingCounter = 0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (attackCounter > attackPeriod  ) {
			ai.setState(SnakeAnimationType.IDLING.ID);
			attackCounter = 0;
		}
		lastMovement = movement;
		
		//System.out.println(this.getClass() +": state = " + ai.getState());
	}

	//TODO is this necessary, we already have an enum type in thwack.and.bash.game.animation.types.SnakeAnimationType, can we reuse that?
	private enum State {
		IDLING(0), WINDERING(1), REVERSE(2), ATTACK(3);
		State(int state) {
			STATE = state;
		}

		private final int STATE;

	}

	private void doNothing() throws Exception {
		if(snakeAnimation == null) {
			System.err.println("MobAnimation is null or empty!");
			return;
		}

		snakeAnimation.beginSettingAnimations();
		nativeAnimation = new Animation(.1f, windingRegionsArray[STILL_FRAME_INDEX]);
		snakeAnimation.setAnimation(nativeAnimation, SnakeAnimationType.IDLING.ID);
		snakeAnimation.setStillAnimationFrame(STILL_FRAME_INDEX);
		snakeAnimation.endSettingAnimations();
	}

	private void move() throws Exception {
		if(snakeAnimation == null) {
			System.err.println("MobAnimation is null or empty!");
			return;
		}

		snakeAnimation.beginSettingAnimations();
		nativeAnimation = new Animation(.1f, windingRegionsArray);
//		snakeAnimation.setAnimation(nativeAnimation, SnakeAnimationType.WINDERING.ID);	//for some reason this crash mobAnimation!!! :o
		snakeAnimation.setAnimation(nativeAnimation, SnakeAnimationType.IDLING.ID);
		snakeAnimation.setStillAnimationFrame(STILL_FRAME_INDEX);
		snakeAnimation.endSettingAnimations();
	}

	@Override
	public MobAnimation createMobAnimation() {
		try {
		Texture windingRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/snake-walking_84x64.png"));
		TextureRegion[][] windingRegions2DArray = TextureRegion.split(windingRegionsSheet, 84, 64);
//		TextureRegion[] windingRegionsArray = Util.toArray(windingRegions2DArray, 3, 1);
		windingRegionsArray = Util.toArray(windingRegions2DArray, 3, 1);

//		MobAnimation snakeAnimation = new MobAnimation();
		snakeAnimation = new MobAnimation();
		} catch (Exception e) {
			//allow headless run
			System.err.println("Snake.java: error="+e+", hopefully this is just a run from a unit test! ;)");
		}
		try {
			move();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return snakeAnimation;
	}

}
