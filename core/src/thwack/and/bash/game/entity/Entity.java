package thwack.and.bash.game.entity;

import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.util.Util.Meters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/*
 * EVERYTHING IS IN METERS
 * 
 * TODO: Finish entity
 * TODO: Collision handler, if a entity touches another entity
 */
public abstract class Entity {

    public Entity(Sprite sprite, CollisionBody body){
	SPRITE = sprite;
	COLLISION_BODY = body;
	entities.add(this);
    }

    protected final Sprite SPRITE;
    protected final CollisionBody COLLISION_BODY;

    //TODO: FIX THIS : TEST METHOD TO SOLVE A PROBLEM;
    private static Array<Entity> entities = new Array<Entity>();
    public static Entity getEntity(String name){
	for(Entity e : entities){
	    if(e.getBody().getUserData().equals(name) || e.getBody().getUserData().equals(name)){
		return e;
	    }
	}
	System.out.println("Couldn't find entity with name " + name);
	return null;
    }

    public void draw(SpriteBatch batch){
	if(SPRITE.getTexture() != null) {
	    SPRITE.setPosition(Meters.toPixels(getBody().getPosition().x) - SPRITE.getWidth() / 2, Meters.toPixels(getBody().getPosition().y) - SPRITE.getHeight() / 2);
	    SPRITE.setRotation((float)Math.toDegrees(getBody().getAngle()));
	    SPRITE.draw(batch);
	}
    }

    public abstract void update(float delta);
    // public abstract void interactionWith(Entity e);

    public void setX(float x){
	setPosition(x, getY());
    }

    public void setY(float y){
	setPosition(getX(), y);
    }

    public void setPosition(Vector2 pos){
	getBody().setTransform(pos, getBody().getAngle());
    }

    public void setPosition(float x, float y){
	getBody().setTransform(x, y, getBody().getAngle());
    }

    public void addToX(float x){
	addToPosition(x, 0);
    }

    public void addToY(float y){
	addToPosition(0, y);
    }

    public void addToPosition(float x, float y){
	getBody().setTransform(x + getX(), y + getY(), 0);
    }

    public Vector2 getPosition(){
	return getBody().getPosition();
    }

    public Sprite getSprite(){
	return SPRITE;
    }

    public Body getBody(){
	return COLLISION_BODY.getBody();
    }

    public Fixture getFixture(){
	return COLLISION_BODY.getFixture();
    }

    public CollisionBody getCollisionBody(){
	return COLLISION_BODY;
    }

    public float getX(){
	return getBody().getPosition().x;
    }

    public float getY(){
	return getBody().getPosition().y;
    }

}
