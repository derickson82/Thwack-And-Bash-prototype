package thwack.and.bash.game.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A version on libgdx's screen, adds update
 */
public interface GameScreen{

    public void update(float delta);

    /*
     * GameBatch should be everything about the game screen
     * static batch is the ui around it
     */
    public void render (SpriteBatch gameBatch, SpriteBatch staticBatch);

    public void resize (int width, int height);

    public void show ();

    public void hide ();

    public void resume ();

    public void pause ();

    public void dispose ();


}
