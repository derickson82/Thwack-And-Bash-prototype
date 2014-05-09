package thwack.and.bash.game.entity.actionentity;

import thwack.and.bash.game.collision.CollisionBody;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class HammerTEST extends ActionEntity{

    public HammerTEST(Sprite sprite, CollisionBody body) {
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
