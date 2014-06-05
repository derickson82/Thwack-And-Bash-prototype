
package thwack.and.bash.game.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** A version on libgdx's screen, adds update */
public interface GameScreen {

	public void update (float delta);

	public void render (SpriteBatch batch);

	public void resize (int width, int height);

	public void show ();

	public void hide ();

	public void resume ();

	public void pause ();

	public void dispose ();

}
