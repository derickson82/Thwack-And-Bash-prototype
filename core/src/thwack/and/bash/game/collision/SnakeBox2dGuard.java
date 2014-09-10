package thwack.and.bash.game.collision;

import thwack.and.bash.game.entity.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class SnakeBox2dGuard implements SnakeGuard, ContactListener {

	private SnakeCollisionHelper collide = new SnakeCollisionHelper();
	private boolean playerNearby;
	
	public boolean isPlayerNearby() {
		return playerNearby;
	}

	public SnakeBox2dGuard(Vector2 foreHeadPos, Vector2 furthestFrontSightPos) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginContact(Contact contact) {
		if(collide.isPlayer(contact)) {
	        Gdx.app.log(this.getClass().getName() + " beginContact", "between player " + collide.getFixturePlayer().getUserData() + " and " + collide.getFixtureHit().getUserData());
	        if(collide.getFixtureHit().getUserData().equals(SnakeCollisionHelper.SNAKE_ID)) {
	        	playerNearby = true;
		        Gdx.app.log(this.getClass().getName(), " attack player !");
	        }
		}
	}

	@Override
	public void endContact(Contact contact) {
		if(collide.isPlayer(contact)) {
	        Gdx.app.log(this.getClass().getName() + " endContact", "between player " + collide.getFixturePlayer().getUserData() + " and " + collide.getFixtureHit().getUserData());
	        if(collide.getFixtureHit().getUserData().equals(SnakeCollisionHelper.SNAKE_ID)) {
	        	playerNearby = false;
		        Gdx.app.log(this.getClass().getName(), " continue");
	        }
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
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
	public void setDebugRenderer(ShapeRenderer renderer) {
		// TODO Auto-generated method stub
		
	}

}
