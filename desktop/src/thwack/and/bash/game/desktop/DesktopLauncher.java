package thwack.and.bash.game.desktop;

import thwack.and.bash.game.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] arg) {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.width = 600;
	config.height = 600;
	config.resizable = false;
	config.useGL30 = false;
	config.vSyncEnabled = false;
	new LwjglApplication(new Game(), config);
    }
}
