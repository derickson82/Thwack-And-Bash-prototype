package thwack.and.bash.game.collision;

import com.badlogic.gdx.math.Vector2;

import thwack.and.bash.game.entity.Entity;

public interface SnakeGuard {

    public Entity hit(float x, float y);

	public Vector2 getEndLOS();

	public void setStartLOS(Vector2 start);

	public Object getNormal();

	public Vector2 getStartLOS();

	public Object getCollision();

	public boolean isPlayerNearby();

}
