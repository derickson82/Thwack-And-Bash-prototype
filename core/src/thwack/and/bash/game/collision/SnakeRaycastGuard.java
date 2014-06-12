package thwack.and.bash.game.collision;

import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class SnakeRaycastGuard implements SnakeGuard, RayCastCallback {

	private static final int GIVE_MORE_HIT_RESULTS = 1;
	private static final int NO_MORE_HIT_RESULTS = 0;
	private static final int NEAREST_ONLY = -1;
    private boolean visible;

	/**
	 * LOS = Line of sight
	 * Collision = current collision point
	 * Normal = a line perpendicular to the collision point
	 */
	Vector2 startLOS = new Vector2(), endLOS = new Vector2(), collision = new Vector2(), normal = new Vector2();

	public SnakeRaycastGuard(Vector2 startLOS, Vector2 endLOS) {
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
	public float reportRayFixture(Fixture fix, Vector2 point,
			Vector2 normal, float fraction) {
		
        if (fix.getFilterData().categoryBits == 0)
		    return NO_MORE_HIT_RESULTS;	//don't give a shit

		collision.set(point);
		//collision point = [2.1652417,31.0] seems to be the last visible contact for the 210 direction
		SnakeRaycastGuard.this.normal.set(normal).add(point);

		if (fix.getBody().getUserData().equals("player")) {
			System.out.println("player collision point = [" + collision.x + "," + collision.y + "]");
		    return fraction;	//give me the rest of the current hit
		}

		if (fix.getBody().getUserData().equals("tile")) {
			System.out.println("tile collision point = [" + collision.x + "," + collision.y + "]");
            visible = false;
		    return NO_MORE_HIT_RESULTS;	//don't give a shit
		}

		return NEAREST_ONLY;
	}
	
    public boolean isVisible() {
        return visible;
    }

	@Override
	public Entity hit(float x, float y) {
		// TODO to return the collided entity here

		return null;
	}

}
