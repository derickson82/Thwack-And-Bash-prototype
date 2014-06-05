
package thwack.and.bash.game.level;

import java.util.ArrayList;

import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.util.Util.Objects;
import thwack.and.bash.game.util.Util.Pixels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/*
 * THIS IS NOT THE FINAL VERSION,
 */

public class Level {

	private Level () {
	}

	private static Array<Entity> entities;

	private static World world;
	private static OrthogonalTiledMapRenderer mapRenderer;
	private static TiledMap tiledMap;

	private static Player player;
	private static Array<Entity> entities;

	public static void update (float delta) {
		world.step(1 / 60f, 6, 2);
		for(int i = 0; i < entities.size; i++){
			entities.get(i).update(delta);
		}
	}

	public static void render (SpriteBatch batch) {
		mapRenderer.setView(Objects.GAME_CAMERA);
		mapRenderer.render();
<<<<<<< HEAD
		boolean needToEnd = false;
		if(!batch.isDrawing()) {
			batch.begin();
			needToEnd = true;
		}
		for(int i = 0; i < entities.size; i++){
			entities.get(i).draw(batch);
		}
		if(needToEnd){
			batch.end();
		}
=======
		for(int i = 0; i < entities.size; i++){
			entities.get(i).draw(batch);
		}
>>>>>>> 53a516b36ecf5beaa071f7a6c160e408f9180e14
	}

	public static void load (String tmxLevel, World world) {
		Level.world = world;
		tiledMap = new TmxMapLoader().load("levels/" + tmxLevel);
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		addBox2D();
		setContactListener();
		entities = new Array<Entity>();
<<<<<<< HEAD
	}

	public static void addEntity(Entity e){
		checkIfPlayer(e);
		entities.add(e);
	}

	public static void addEntities(Entity[] e){
		checkIfPlayer(e);
		entities.addAll(e);
	}

	public static void addEntities(Array<Entity> e){
		checkIfPlayer(e);
		entities.addAll(e);
	}

	public static void removeEntity(Entity e){
		entities.removeValue(e, true);
	}

	public static void removeEntities(Entity[] e2){
		for(Entity e : e2) {
			entities.removeValue(e, true);
		}
	}

	public static void removeEntities(Array<Entity> e){
		entities.removeAll(e, true);
=======
>>>>>>> 53a516b36ecf5beaa071f7a6c160e408f9180e14
	}

	public static World getWorld () {
		return world;
	}

<<<<<<< HEAD
	private static void checkIfPlayer(Entity e){
		if(e instanceof Player){
			player = (Player) e;
		}
	}

	private static void checkIfPlayer(Entity[] e2){
		for(Entity e : e2){
			if(e instanceof Player){
				player = (Player)e;
=======
	public static void addEntities(Entity... e){
		for(int i = 0; i < e.length; i++){
			entities.add(e[i]);
			if(e[i].getBody().getUserData().equals("player")){
				Level.player = (Player)e[i];
>>>>>>> 53a516b36ecf5beaa071f7a6c160e408f9180e14
			}
		}
	}

<<<<<<< HEAD
	private static void checkIfPlayer(Array<Entity> e2){
		for(int i = 0; i < e2.size; i++){
			if(e2.get(i) instanceof Player) {
				player = (Player) e2.get(i);
			}
=======
	public static void removeEntities(Entity... e){
		for(int i = 0; i < e.length; i++){
			entities.removeValue(e[i], true);
>>>>>>> 53a516b36ecf5beaa071f7a6c160e408f9180e14
		}
	}

	// Shall fix this
	private static void addBox2D () {
		float tileSize = 0;
		ArrayList<Box2DCell> cells = new ArrayList<Box2DCell>();
		MapLayer mapLayer = tiledMap.getLayers().get("collision");
		TiledMapTileLayer layer = (TiledMapTileLayer)mapLayer;
		tileSize = layer.getTileWidth();
		for (int y = 0; y < layer.getHeight(); y++) {
			for (int x = 0; x < layer.getWidth(); x++) {
				Cell cell = layer.getCell(x, y);
				if (cell == null) {
					continue;
				}
				if (cell.getTile() == null) {
					continue;
				}

				cells.add(new Box2DCell(cell, x, y));
			}
		}

		// X ___
		// l J
		// ----
		int i = cells.size() - 1;
		ArrayList<Box2DCell> yCells = new ArrayList<Box2DCell>();
		while (true) {
			Box2DCell c = cells.get(0);
			float x = c.x;
			while (true) {
				Box2DCell c2 = findCellToRight(cells, c, x);
				if (c2 != null) {
					cells.remove(c2);
					x++;
					i--;
				} else {
					break;
				}
			}
			float x2 = x - c.x + 1;
			if (x2 - 1 >= 1) {
				BodyDef bodyDef = new BodyDef();
				bodyDef.position.x = Pixels.toMeters(c.x * tileSize + x2 * tileSize / 2);
				bodyDef.position.y = Pixels.toMeters(c.y * tileSize + tileSize / 2);
				bodyDef.type = BodyType.StaticBody;
				Body body = world.createBody(bodyDef);
				body.setUserData("tile");
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(Pixels.toMeters(x2 * tileSize / 2), Pixels.toMeters(tileSize / 2));
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = shape;
				fixtureDef.friction = 0;
				Fixture f = body.createFixture(fixtureDef);
				f.setUserData("tile");
			}
			if (x2 - 1 == 0) {
				yCells.add(c);
			}
			cells.remove(c);
			if (cells.size() <= 0) {
				break;
			}
		}

		// Y
		// _
		// l l
		// l l
		// L _J
		if (yCells.size() != 0) {
			while (true) {
				Box2DCell c = yCells.get(0);
				float y = c.y;
				while (true) {
					Box2DCell c2 = findCellToDown(yCells, c, y);
					if (c2 != null) {
						yCells.remove(c2);
						y++;
					} else {
						break;
					}
				}
				float y2 = y - c.y + 1;
				if (y2 == 0) {
					y2 = 1;
				}
				BodyDef bodyDef = new BodyDef();
				bodyDef.position.x = Pixels.toMeters(c.x * tileSize + tileSize / 2);
				bodyDef.position.y = Pixels.toMeters(c.y * tileSize + y2 * tileSize / 2);
				bodyDef.type = BodyType.StaticBody;
				Body body = world.createBody(bodyDef);
				body.setUserData("tile");
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(Pixels.toMeters(tileSize / 2), Pixels.toMeters(y2 * tileSize / 2));
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = shape;
				fixtureDef.friction = 0;
				Fixture f = body.createFixture(fixtureDef);
				f.setUserData("tile");
				yCells.remove(c);
				if (yCells.size() <= 0) {
					break;
				}
			}
		}
	}

	private static Box2DCell findCellToRight (ArrayList<Box2DCell> cells, Box2DCell c, float x) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i) == c) {
				continue;
			}
			if (cells.get(i).y == c.y && cells.get(i).x == x + 1) {
				Box2DCell c2 = cells.get(i);
				cells.remove(i);
				return c2;
			}
		}
		return null;
	}

	private static Box2DCell findCellToDown (ArrayList<Box2DCell> cells, Box2DCell c, float y) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i) == c) {
				continue;
			}
			if (cells.get(i).y == y + 1 && cells.get(i).x == c.x) {
				Box2DCell c2 = cells.get(i);
				cells.remove(i);
				return c2;
			}
		}
		return null;
	}

	private static void setContactListener () {
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact (Contact contact) {

			}

			@Override
			public void endContact (Contact contact) {

			}

			@Override
			public void preSolve (Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve (Contact contact, ContactImpulse impulse) {

			}

		});
	}

	private static boolean check (Entity e, Contact c) {
		Fixture a = c.getFixtureA();
		Fixture b = c.getFixtureB();
		if (a.getUserData() == null) {
			// System.out.println("A fixture you just collied with or you's user data == null, fix this");
			return false;
		}
		if (b.getUserData() == null) {
			// System.out.println("B fixture you just collied with or you's user data == null, fix this");
			return false;
		}

		if (a.getUserData().equals(e.getFixture().getUserData())) {
			return true;
		} else if (b.getUserData().equals(e.getFixture().getUserData())) {
			return true;
		}
		return false;
	}

}
