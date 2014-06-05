
package thwack.and.bash.game.ui.popup;

import java.util.Iterator;

import thwack.and.bash.game.tween.VectorTweener;
import thwack.and.bash.game.util.Util.Objects;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Popup {

	public Popup () {
		init();
		layout();
		pos = new Vector2();
		pos.x = hidePosition().x;
		pos.y = hidePosition().y;
	}

	public Vector2 pos;

	private static final float DURATION = 0.25f;

	private boolean hidden = true;
	private boolean tweening = false;

	public final void toggle () {
		if (tweening) {
			killTarget(getName());
		}
		hidden = !hidden;
		tweening = true;
		Vector2 target;
		if (hidden) {
			target = hidePosition();
		} else {
			target = showPosition();
		}
		Tween.to(pos, VectorTweener.POSITION_XY, DURATION).target(target.x, target.y).ease(Quint.INOUT).setCallback(new TweenCallback() {
			@Override
			public void onEvent (int type, BaseTween<?> source) {
				tweening = false;
			}
		}).setUserData(getName()).start(Objects.TWEEN_MANAGER);
	}

	public final boolean isCompletlyHidden () {
		return hidden && !tweening;
	}

	private boolean killTarget (String name) {
		Iterator<BaseTween<?>> i = Objects.TWEEN_MANAGER.getObjects().iterator();
		while (i.hasNext()) {
			BaseTween<?> bt = i.next();
			if (bt.getUserData().equals(name)) {
				bt.kill();
			}
			return true;
		}
		return false;
	}

	public abstract void init ();

	public abstract void layout ();

	public abstract void draw (SpriteBatch batch);

	public abstract void update (float delta);

	public abstract void show ();

	public abstract void hide ();

	public abstract Vector2 size();

	public abstract Vector2 hidePosition ();

	public abstract Vector2 showPosition ();

	public abstract String getName ();

}
