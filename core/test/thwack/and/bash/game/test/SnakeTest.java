package thwack.and.bash.game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class SnakeTest {

	public class Globals {
		public static final float NORMAL_TICK_RATE = 1f/60;	//in fps
	}

	@Test
	public void test() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        config.renderInterval = Globals.NORMAL_TICK_RATE;
        new HeadlessApplication(new GameDebug(), config);
        
        fail("Not yet implemented");
	}

}
