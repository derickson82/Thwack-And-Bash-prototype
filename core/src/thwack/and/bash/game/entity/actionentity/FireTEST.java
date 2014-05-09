package thwack.and.bash.game.entity.actionentity;

import thwack.and.bash.game.collision.CollisionBody;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class FireTEST extends ActionEntity{

    public FireTEST(Sprite sprite, CollisionBody body) {
	super(sprite, body);
    }

    @Override
    public Sprite getIconSprite() {
	return null;
    }

    @Override
    public void update(float delta) {

    }

}
