package thwack.and.bash.game.ui;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.entity.actionentity.ActionEntity;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameUI {

    public GameUI(){
	SLOTS = new Slot[3];

	Table table = new Table();
	table.setBounds(0, 0, Game.getWidth(), 100);

	for(int i = 0; i < SLOTS.length; i++){
	    SLOTS[i] = new Slot();
	    SLOTS[i].setActionEntity(ActionEntity.NULL);
	    SLOTS[i].slotImage = new ImageButton(Game.getSlotImageButtonStyle());
	    SLOTS[i].slotImage.setDisabled(true);
	    table.add(SLOTS[i].slotImage).bottom().size(50).pad(50);
	}

	viewport = new ScreenViewport();
	stage = new Stage(viewport, Game.getBatch());
	stage.addActor(table);

    }

    private ScreenViewport viewport;
    private Stage stage;
    private final Slot[] SLOTS;

    public void update(float delta){
	viewport.update();

    }

    public void draw(){
	stage.draw();
    }

    public class Slot{

	public Slot(){

	}

	private ActionEntity actionEntity;
	private ImageButton slotImage;

	public ActionEntity getActionEntity() {
	    return actionEntity;
	}

	public void setActionEntity(ActionEntity actionEntity) {
	    this.actionEntity = actionEntity;
	}

    }

}
