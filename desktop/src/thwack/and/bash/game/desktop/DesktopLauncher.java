
package thwack.and.bash.game.desktop;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.test.GameDebug;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 800 / 4 * 3;
		config.resizable = false;
		config.useGL30 = false;
		config.vSyncEnabled = false;
//		new LwjglApplication(new Game(), config);
		/**
		 * Setup:
		 * 
		 * 1. Set your sdk.dir in local.properties like sdk.dir=C:/adt-bundle-windows-x86_64-20140702/sdk
		 * 2. Hit "gradlew eclipse"
		 * 3. Point Run Configuration's Argument "Working directory" Other to ${workspace_loc:Thwack and Bash-android/assets}
		 * 
		 * You have two options to make this work:
		 * 1. Comment it (the code below) out and use the real Game class (the code above)!
		 * 2. Add the "test/" folder and use it as a source folder :)
		 * The choice is yours!
		 */
		new LwjglApplication(new Game(), config);	//use this if you like! :)
//		new LwjglApplication(new GameDebug(), config);	//use this if you like! :)
	}
}
