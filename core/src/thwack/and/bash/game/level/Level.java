package thwack.and.bash.game.level;

import java.util.ArrayList;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.util.Util.Pixels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Level {

    private Level(){
    }

    private static TiledMap tiledMap;
    private static OrthogonalTiledMapRenderer mapRenderer;

    public static void render(){
	mapRenderer.setView(Game.getCamera());
	mapRenderer.render();
    }

    public static void load(String tmxLevel, World world){
	tiledMap = new TmxMapLoader().load("levels/" + tmxLevel);
	mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Game.getBatch());

	addBox2D(world);
    }

    private static void addBox2D(World world){
	float tileSize = 0;
	ArrayList<Box2DCell> cells = new ArrayList<Box2DCell>();
	MapLayer mapLayer = tiledMap.getLayers().get("collision");
	TiledMapTileLayer layer = (TiledMapTileLayer) mapLayer;
	tileSize = layer.getTileWidth();
	for(int y = 0; y < layer.getHeight(); y++){
	    for(int x = 0; x < layer.getWidth(); x++){
		Cell cell = layer.getCell(x, y);
		if(cell == null){
		    continue;
		}
		if(cell.getTile() == null){
		    continue;
		}

		cells.add(new Box2DCell(cell, x, y));
	    }
	}

	//X ___
	//  l   J
	//   ----
	int i = cells.size() - 1;
	ArrayList<Box2DCell> yCells = new ArrayList<Box2DCell>();
	while(true){
	    System.out.println("Cells Size : " + cells.size());
	    System.out.println("I : " + i);
	    Box2DCell c = cells.get(0);
	    float x = c.x;
	    while(true){
		Box2DCell c2 = findCellToRight(cells, c, x);
		if(c2 != null){
		    cells.remove(c2);
		    x++;
		    i--;
		} else {
		    break;
		}
	    }
	    float x2 = x - c.x + 1;
	    if(x2 - 1 >= 1){
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.x = Pixels.toMeters((c.x * tileSize + (x2 * tileSize) / 2));
		bodyDef.position.y = Pixels.toMeters((c.y * tileSize + tileSize / 2));
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Pixels.toMeters(x2 * tileSize) / 2, Pixels.toMeters(tileSize) / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0;
		body.createFixture(fixtureDef);
	    }
	    if(x2 - 1 == 0){
		yCells.add(c);
	    }
	    cells.remove(c);
	    if(cells.size() <= 0){
		break;
	    }
	}

	//Y
	// _
	//l  l
	//l  l
	//L _J
	while(true){
	    Box2DCell c = yCells.get(0);
	    float y = c.y;
	    while(true){
		Box2DCell c2 = findCellToDown(yCells, c, y);
		if(c2 != null){
		    yCells.remove(c2);
		    y++;
		} else {
		    break;
		}
	    }
	    float y2 = y - c.y + 1;
	    if(y2 == 0){
		y2 = 1;
	    }
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.position.x = Pixels.toMeters((c.x * tileSize + tileSize / 2));
	    bodyDef.position.y = Pixels.toMeters((c.y * tileSize + (y2 * tileSize) / 2));
	    bodyDef.type = BodyType.StaticBody;
	    Body body = world.createBody(bodyDef);
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(Pixels.toMeters(tileSize) / 2, Pixels.toMeters(y2 * tileSize) / 2);
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.friction = 0;
	    body.createFixture(fixtureDef);
	    yCells.remove(c);
	    if(yCells.size() <= 0){
		break;
	    }
	}

    }

    private static Box2DCell findCellToRight(ArrayList<Box2DCell> cells, Box2DCell c, float x){
	for(int i = 0; i < cells.size(); i++){
	    if(cells.get(i) == c){
		continue;
	    }
	    if(cells.get(i).y == c.y && cells.get(i).x == x + 1){
		Box2DCell c2 = cells.get(i);
		cells.remove(i);
		return c2;
	    }
	}
	return null;
    }

    private static Box2DCell findCellToDown(ArrayList<Box2DCell> cells, Box2DCell c, float y){
	for(int i = 0; i < cells.size(); i++){
	    if(cells.get(i) == c){
		continue;
	    }
	    if(cells.get(i).y == y + 1 && cells.get(i).x == c.x){
		Box2DCell c2 = cells.get(i);
		cells.remove(i);
		return c2;
	    }
	}
	return null;
    }



}

