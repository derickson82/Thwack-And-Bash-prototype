package thwack.and.bash.game.util;


public class Util {


    public static final class Values {

	//TODO: FIX THIS;
	//    public static final float CHUNK_SIZE_X = Game.getMapViewport().width; //NOT RIGHT, should be a single value
	//    public static final float CHUNK_SIZE_Y = Game.getMapViewport().height;
	public static final float CHUNK_SIZE_X = 20; //TILES, NOT PIXELS
	public static final float CHUNK_SIZE_Y = 20;

	//EVERY TILE IS ONE METER
	public static final float PIXELS_PER_METER = 32f;


    }

    public static final class Pixels {

	public static final float toMeters(float pixels){
	    return pixels / Values.PIXELS_PER_METER;
	}

    }

    public static final class Meters {

	public static final float toPixels(float meters){
	    return meters * Values.PIXELS_PER_METER;
	}

    }

}
