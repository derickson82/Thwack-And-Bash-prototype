
package thwack.and.bash.game.ui;

import thwack.and.bash.game.entity.actionentity.ActionEntity;
import thwack.and.bash.game.util.Util.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** An Inventory containing a grid of slots for now.
 * 
 * TODO: ADD RESIZE METHOD, UPDATE ALL SLOT'S ICON SPRITE'S POSITION AND SIZE */
public class Inventory {

	// In slots, not pixels
	// slotWidth is pixels
	public Inventory (float xPos, float yPos, int width, int height, float slotWidth, float slotHeight) {
		slots = new ActionEntity[width][height];
		background = new NinePatch(Objects.MAIN_MENU_SKIN.getRegion("BlueButtonUp"));
		grid = new NinePatch(new Texture("Grid.9.png"));
		this.xPos = xPos;
		this.yPos = yPos;
		backgroundWidth = width * slotWidth;
		backgroundHeight = height * slotHeight;
	}

	private ActionEntity[][] slots;
	private NinePatch grid;
	private NinePatch background;
	private float xPos, yPos, backgroundWidth, backgroundHeight, slotWidth, slotHeight;

	// TODO: ADD GRID
	public void render (SpriteBatch batch) {
		background.draw(batch, xPos, yPos, backgroundWidth, backgroundHeight);
		for (int i = 0; i < slots.length; i++) {
			for (int j = 0; j < slots[0].length; j++) {
				ActionEntity ae = slots[i][j];
				if (ae == null) {
					continue;
				}
				ae.getIconSprite().draw(batch);
			}
		}
	}

	public void setSlot(ActionEntity ae, int i, int j){
		slots[i][j] = ae;
		Sprite s = ae.getIconSprite();
		s.setBounds(i * slotWidth, j * slotHeight, slotWidth, slotHeight);
	}

}
