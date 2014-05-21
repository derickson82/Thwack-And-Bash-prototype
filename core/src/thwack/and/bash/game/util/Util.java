
package thwack.and.bash.game.util;

import thwack.and.bash.game.collision.CollisionBody;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Util {

	public static final class Objects {
		public static final OrthographicCamera GAME_CAMERA = Init.createGameCamera();
		public static final OrthographicCamera SCREEN_CAMERA = Init.createScreenCamera();

		// UI STUFF
		public static final BitmapFont STANDARD_FONT = Init.createStandardFont();

		public static final TextureAtlas MAIN_MENU_ATLAS = Init.createMainMenuAtlas();
		public static final Skin MAIN_MENU_SKIN = Init.createMainMenuSkin();

		public static final ImageButtonStyle SLOT_IMAGE_BUTTON_STYLE = Init.createSlotImageButtonStyle();
		public static final TextButtonStyle BLUE_TEXT_BUTTON_STYLE = Init.createBlueTextButtonStyle();
	}

	public static final class Values {

		// EVERY TILE IS ONE METER
		public static final float PIXELS_PER_METER = 32f;

	}

	public static final class Pixels {

		public static final float toMeters (float pixels) {
			return pixels / Values.PIXELS_PER_METER;
		}

	}

	public static final class Meters {

		public static final float toPixels (float meters) {
			return meters * Values.PIXELS_PER_METER;
		}

	}

	private static final class Init {

		private static final OrthographicCamera createGameCamera () {
			OrthographicCamera c = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			c.setToOrtho(false);
			return c;
		}

		private static final OrthographicCamera createScreenCamera () {
			return createGameCamera();
		}

		private static final BitmapFont createStandardFont () {
			return new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
		}

		private static final TextButtonStyle createBlueTextButtonStyle () {
			TextButtonStyle blueTextButtonStyle = new TextButtonStyle();
			blueTextButtonStyle.down = Objects.MAIN_MENU_SKIN.getDrawable("BlueButtonDown");
			blueTextButtonStyle.over = Objects.MAIN_MENU_SKIN.getDrawable("BlueButtonOver");
			blueTextButtonStyle.up = Objects.MAIN_MENU_SKIN.getDrawable("BlueButtonUp");
			blueTextButtonStyle.font = Objects.STANDARD_FONT;
			blueTextButtonStyle.fontColor = Color.BLACK;
			return blueTextButtonStyle;
		}

		private static final ImageButtonStyle createSlotImageButtonStyle () {
			ImageButtonStyle slotImageButtonStyle = new ImageButtonStyle();
			slotImageButtonStyle.up = Objects.MAIN_MENU_SKIN.getDrawable("BlueButtonUp");
			slotImageButtonStyle.over = Objects.MAIN_MENU_SKIN.getDrawable("BlueButtonOver");
			slotImageButtonStyle.disabled = Objects.MAIN_MENU_SKIN.getDrawable("BlueButtonDown");
			return slotImageButtonStyle;
		}

		private static final TextureAtlas createMainMenuAtlas () {
			return new TextureAtlas(Gdx.files.internal("textureatlas/UI/output/mainMenuAtlas.atlas"));
		}

		private static final Skin createMainMenuSkin () {
			return new Skin(Objects.MAIN_MENU_ATLAS);
		}

	}

	public static final class Box2D{

		/**
		 * 
		 * @param p = position, meters
		 * @param s = size, how big the sprite is, not half of it
		 * @param w = world
		 * @return
		 */
		public static final CollisionBody createSimpleDynamicBody(Vector2 p, Vector2 s, World w){

			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(p.x, p.y);

			PolygonShape shape = new PolygonShape();
			shape.setAsBox(s.x / 2, s.y / 2);

			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 0;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0;

			return new CollisionBody(bodyDef, fixtureDef, w);
		}

	}


	public static final TextureRegion[] toArray (TextureRegion[][] array2D, int width, int height) {
		TextureRegion[] array = new TextureRegion[width * height];
		int index = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				array[index++] = array2D[j][i];
			}
		}
		return array;
	}

	public static final TextureRegion[] flipRegions (TextureRegion[] data, boolean xMir, boolean yMir) {
		for (int i = 0; i < data.length; i++) {
			data[i].flip(xMir, yMir);
		}
		return data;
	}


}
