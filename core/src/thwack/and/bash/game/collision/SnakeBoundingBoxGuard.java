package thwack.and.bash.game.collision;

import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.entity.mob.Mob;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.test.GameDebug;
import thwack.and.bash.game.test.RendererDebug;
import thwack.and.bash.game.test.SnakeDebug;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SnakeBoundingBoxGuard implements SnakeGuard {
	
	Mob snake;
	private Mob collidedObject;
	boolean isPlayerNearby;
	private ShapeRenderer debugRender;
	
	public ShapeRenderer getDebugRenderer() {
		return debugRender;
	}

	public void setDebugRenderer(ShapeRenderer debugRender) {
		this.debugRender = debugRender;
	}

	public Mob getSnake() {
		return snake;
	}

	public void setSnake(Mob snake) {
		this.snake = snake;
	}
	
	public Mob getCollidedObject() {
		return collidedObject;
	}

	public void setCollidedObject(Mob player) {
		this.collidedObject = player;
	}

	@Override
	public Entity hit(float x, float y) {
		Mob retVal = null;
		if(snake != null && snake.getBoundingBox()!= null && collidedObject != null) {
			Rectangle r1 = snake.getBoundingBox();
			try {
				Player player = (Player)collidedObject;
				Rectangle r2 = player.getBoundingBox();
				System.out.println("/* snake */ fillRect(" + r1.getX() + "," + r1.getY() + "," + r1.getWidth() + "," + r1.getHeight() + "); /* player */ fillRect(" + r2.getX() + "," + r2.getY() + "," + r2.getWidth() + "," + r2.getHeight() + ");");
				if(debugRender != null) {
					GameDebug.drawRect(debugRender, 150, 50, 430, 500);
					GameDebug.drawRect(debugRender, r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight());
					GameDebug.drawRect(debugRender, r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight());
				} else {
					System.out.println("SnakeBoundingBoxGuard.java debug renderer is not active!");
				}

				if(r1 != null && r2 != null && r1.overlaps(r2)) {
					System.out.println("player id [" + player.getId() + "]");
//					if(r2.contains(x, y)) {
						if(player != null && player.getId() != null && player.getId().equals(SnakeCollisionHelper.PLAYER_ID)) {
							retVal = player;	//returns the hit object
							isPlayerNearby = true;
						}
//					}
				} else {
					isPlayerNearby = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return retVal;
	}

	@Override
	public Vector2 getEndLOS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStartLOS(Vector2 start) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getNormal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2 getStartLOS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCollision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPlayerNearby() {
		return isPlayerNearby;
	}

}
