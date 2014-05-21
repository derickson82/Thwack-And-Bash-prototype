
package thwack.and.bash.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

}
