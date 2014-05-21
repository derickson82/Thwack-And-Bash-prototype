
package thwack.and.bash.game.collision;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class CollisionBody {

	public CollisionBody (BodyDef bodyDef, FixtureDef fixtureDef, World world) {
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
	}

	// TODO: ADD THIS SHIT
	public CollisionBody (BodyEditorLoader loader, BodyDef bodyDef, FixtureDef fixtureDef, World world) {

	}

	private Body body;
	private Fixture fixture;

	public Body getBody () {
		return body;
	}

	public Fixture getFixture () {
		return fixture;
	}

}
