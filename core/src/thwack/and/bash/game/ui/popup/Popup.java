package thwack.and.bash.game.ui.popup;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Popup {

    protected final Vector2 position = hidePosition();

    private boolean hidden = true;

    public final void setPosition(float x, float y){
	position.x = x;
	position.y = y;
    }

    public final void setX(float x){
	position.x = x;
    }

    public final void setY(float y){
	position.y = y;
    }

    public final void toggle(){
	hidden = !hidden;
	//TODO: ADD TWEEN STUFF
    }

    public abstract void layout();
    public abstract void draw(SpriteBatch staticBatch);
    public abstract void update(float delta);
    public abstract void show();
    public abstract void hide();

    public abstract Vector2 hidePosition();
    public abstract Vector2 showPosition();
    public abstract Vector2 size();

}
