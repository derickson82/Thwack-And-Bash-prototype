
package thwack.and.bash.game.ui;

import thwack.and.bash.game.ui.popup.LeftSidePopup;
import thwack.and.bash.game.ui.popup.Popup;
import thwack.and.bash.game.ui.popup.RightSidePopup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameUI {

	public GameUI () {
		popups = new Popup[2];
		popups[RIGHT_SIDE] = new RightSidePopup();
		popups[LEFT_SIDE] = new LeftSidePopup();
	}

	private final Popup[] popups;

	private static final int RIGHT_SIDE = 0, LEFT_SIDE = 1;

	private float coolDown = 0;

	public void update(float delta){
		coolDown += delta;
		if(coolDown >= 0.5f){
			if(Gdx.input.isKeyPressed(Keys.Q)){
				popups[LEFT_SIDE].toggle();
				coolDown = 0;
			}
			else if(Gdx.input.isKeyPressed(Keys.E)){
				popups[RIGHT_SIDE].toggle();
				coolDown = 0;
			}
		}
		for(Popup p : popups){
			if(!p.isCompletlyHidden()) {
				p.update(delta);
			}
		}
	}

	public void render(SpriteBatch batch){
		for(Popup p : popups){
			if(!p.isCompletlyHidden()){
				p.draw(batch);
			}
		}
	}

}
