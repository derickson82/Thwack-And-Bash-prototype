package thwack.and.bash.game.entity.actionentity;

import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
/*
 * Weapon, Magic,
 */
public abstract class ActionEntity extends Entity{

    public ActionEntity(Sprite sprite, CollisionBody body) {
	super(sprite, body);
    }

    public abstract Sprite getIconSprite();

    public static final ActionEntity NULL = new NullActionEntity(null, null);

    private static class NullActionEntity extends ActionEntity{

	public NullActionEntity(Sprite sprite, CollisionBody body) {
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

}
