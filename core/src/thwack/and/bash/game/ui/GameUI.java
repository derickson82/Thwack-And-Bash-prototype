
package thwack.and.bash.game.ui;

import thwack.and.bash.game.ui.popup.Popup;
import thwack.and.bash.game.ui.popup.RightSidePopup;

public class GameUI {

	public GameUI () {
		popups = new Popup[2];
		popups[RIGHT_SIDE] = new RightSidePopup();
	}

	private Popup[] popups;

	private static final int RIGHT_SIDE = 0, LEFT_SIDE = 1;

	// private ScreenViewport viewport;
	// private Stage stage;
	// private final Slot[] SLOTS;
	//
	// public void update(float delta){
	// viewport.update();
	// }
	//
	// public void drawStage(){
	// stage.draw();
	// }
	//
	// public void drawSprites(SpriteBatch batch){
	// for(Slot slot : SLOTS){
	// Sprite sprite = slot.actionEntity.getIconSprite();
	// sprite.setBounds(slot.slotImage.getX() + 1, slot.slotImage.getY() + 1, slot.slotImage.getWidth() - 2,
// slot.slotImage.getHeight() - 2);
	// sprite.draw(batch);
	// }
	// }
	//
	// public class Slot{
	//
	// public Slot(){
	//
	// }
	//
	// private ActionEntity actionEntity;
	// private ImageButton slotImage;
	//
	// public ActionEntity getActionEntity() {
	// return actionEntity;
	// }
	//
	// public void setActionEntity(ActionEntity actionEntity) {
	// this.actionEntity = actionEntity;
	// }
	//
	// }
	//
	// SLOTS = new Slot[3];
	//
	// Table table = new Table();
	// table.setBounds(0, 0, Game.getWidth(), 100);
	//
	// for(int i = 0; i < SLOTS.length; i++){
	// SLOTS[i] = new Slot();
	// SLOTS[i].setActionEntity(ActionEntity.NULL);
	// SLOTS[i].slotImage = new ImageButton(Game.getSlotImageButtonStyle());
	// SLOTS[i].slotImage.setDisabled(true);
	// table.add(SLOTS[i].slotImage).bottom().size(50).pad(50);
	// SLOTS[i].actionEntity = new FireTEST(null, null);
	// }
	//
	// viewport = new ScreenViewport();
	// stage = new Stage(viewport, Game.getStaticBatch());
	// stage.addActor(table);
	//

}
