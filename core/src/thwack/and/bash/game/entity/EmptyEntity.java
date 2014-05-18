package thwack.and.bash.game.entity;

import thwack.and.bash.game.collision.CollisionBody;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class EmptyEntity extends Entity{

    public EmptyEntity(Sprite sprite, CollisionBody body) {
	super(sprite, body);
    }

    @Override
    public void update(float delta) {

    }

}
