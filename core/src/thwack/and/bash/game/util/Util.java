package thwack.and.bash.game.util;

public class Util {

    public static final float PIXELS_PER_METER = 35f;

    public static final class Pixels {

	public static final float toMeters(float pixels){
	    return pixels / PIXELS_PER_METER;
	}

    }

    public static final class Meters {

	public static final float toPixels(float meters){
	    return meters * PIXELS_PER_METER;
	}

    }

}
