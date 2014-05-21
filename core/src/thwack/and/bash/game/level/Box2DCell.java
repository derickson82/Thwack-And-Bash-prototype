
package thwack.and.bash.game.level;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Box2DCell {
	public Box2DCell (Cell cell, float x, float y) {
		this.cell = cell;
		this.x = x;
		this.y = y;
	}

	public Cell cell;
	public float x, y;
}
