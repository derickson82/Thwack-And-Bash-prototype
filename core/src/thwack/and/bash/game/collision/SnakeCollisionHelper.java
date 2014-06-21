package thwack.and.bash.game.collision;

import thwack.and.bash.game.entity.Entity;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public class SnakeCollisionHelper {

	public static final String PLAYER_ID = "player";
	public static final String SNAKE_ID = "snake";
	public static final String TILE_ID = "tile";
	public static final String BAT_ID = "bat";
    Fixture fixturePlayer;
    Fixture fixtureUnknown;

	public Fixture getFixturePlayer() {
		return fixturePlayer;
	}

	public Fixture getFixtureHit() {
		return fixtureUnknown;
	}

	public boolean isPlayer(Contact contact) {
		boolean ret = false;
		
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if(fixtureA.getBody().getUserData() != null) {
			if(fixtureA.getBody().getUserData().equals(SnakeCollisionHelper.PLAYER_ID)) {
				ret = true;
				fixturePlayer = fixtureA;
				fixtureUnknown = fixtureB;
			}
		}
		if(fixtureB.getBody().getUserData() != null) {
			if(fixtureB.getBody().getUserData().equals(SnakeCollisionHelper.PLAYER_ID)) {
				ret = true;
				fixturePlayer = fixtureB;
				fixtureUnknown = fixtureA;
			}
		}

		return ret;
	}

	public boolean isPlayer(Entity contact) {
		boolean ret = false;

//		if(contact.get)
		return ret;
	}
}
