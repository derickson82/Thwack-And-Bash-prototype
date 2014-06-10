package thwack.and.bash.game.collision;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class SnakeGuard implements RayCastCallback {

	private static final int NEAREST = -1;
	/**
	 * LOS = Line of sight
	 * Collision = current collision point
	 * Normal = a line perpendicular to the collision point
	 */
	Vector2 startLOS = new Vector2(), endLOS = new Vector2(), collision = new Vector2(), normal = new Vector2();

	public SnakeGuard(Vector2 startLOS, Vector2 endLOS) {
		this.startLOS = startLOS;
		this.endLOS = endLOS;
	}

	public Vector2 getStartLOS() {
		return startLOS;
	}

	public void setStartLOS(Vector2 startLOS) {
		this.startLOS = startLOS;
	}

	public Vector2 getEndLOS() {
		return endLOS;
	}

	public void setEndLOS(Vector2 endLOS) {
		this.endLOS = endLOS;
	}

	public Vector2 getCollision() {
		return collision;
	}

	public Vector2 getNormal() {
		return normal;
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		
		collision.set(point);
		System.out.println("collision point = [" + collision.x + "," + collision.y + "]");
		SnakeGuard.this.normal.set(normal).add(point);
		
		//collision point = [2.1652417,31.0] seems to be the last visible contact for the 210 direction

		return NEAREST;
	}

}
