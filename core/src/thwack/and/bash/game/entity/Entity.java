package thwack.and.bash.game.entity;

import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/*
 * EVERYTHING IS IN METERS
 * 
 * TODO: Finish entity
 * TODO: add box2d
 */
public abstract class Entity {

    public Entity(Sprite sprite, CollisionBody body){
	SPRITE = sprite;
	COLLISION_BODY = body;
    }

    protected final Sprite SPRITE;
    protected final CollisionBody COLLISION_BODY;

    public void draw(SpriteBatch batch){
	if(SPRITE.getTexture() != null) {
	    SPRITE.setPosition(Meters.toPixels(getBody().getPosition().x) - SPRITE.getWidth() / 2, Meters.toPixels(getBody().getPosition().y) - SPRITE.getHeight() / 2);
	    SPRITE.setRotation((float)Math.toDegrees(getBody().getAngle()));
	    SPRITE.draw(batch);
	}
    }

    public abstract void update(float delta);

    public void setPosition(Vector2 pos){
	getBody().setTransform(pos, getBody().getAngle());
    }

    public void setPosition(float x, float y){
	getBody().setTransform(x, y, getBody().getAngle());
    }

    public Body getBody(){
	return COLLISION_BODY.getBody();
    }

    public Fixture getFixture(){
	return COLLISION_BODY.getFixture();
    }

}
