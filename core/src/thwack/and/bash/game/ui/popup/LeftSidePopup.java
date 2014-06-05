package thwack.and.bash.game.ui.popup;

import thwack.and.bash.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class LeftSidePopup extends Popup{

	private static final Vector2 SIZE = new Vector2(Game.getWidth() / 6, Game.getHeight()), HIDE = new Vector2(-SIZE.x, 0), SHOW = new Vector2(0, 0);
	private Sprite background;

	@Override
	public void init() {
		background = new Sprite(new Texture(Gdx.files.internal("blue_square_menu_background.png")));
		background.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		background.setSize(SIZE.x, SIZE.y);
	}

	@Override
	public void layout() {
		background.setU2(SIZE.x / 5);
		background.setV2(SIZE.y / 5);
	}

	@Override
	public void draw(SpriteBatch batch) {
		boolean needToEnd = false;
		if(!batch.isDrawing()){
			batch.begin();
			needToEnd = true;
		}
		background.draw(batch);
		if(needToEnd){
			batch.end();
		}
	}

	@Override
	public void update(float delta) {
		background.setPosition(pos.x, pos.y);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public Vector2 hidePosition(){
		return HIDE.cpy();
	}

	@Override
	public Vector2 showPosition() {
		return SHOW.cpy();
	}



	@Override
	public Vector2 size() {
		return SIZE;
	}



	@Override
	public String getName(){
		return "LeftSidePopup";
	}

}