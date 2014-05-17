package thwack.and.bash.game.level;

import java.util.ArrayList;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.EmptyEntity;
import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.util.Util.Pixels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

public class Level {

    private Level(){
    }

    private static Vector2 currentChunk = new Vector2(0,0);
    private static World world;
    private static TiledMap tiledMap;
    private static OrthogonalTiledMapRenderer mapRenderer;

    private static EmptyEntity[] walls; // I = 0 = UPPER |||| I = 1 = RIGHT |||| I = 2 = LOWER |||| I = 3 = LEFT

    public static void render(){
	Rectangle r = Game.getMapViewport();
	Gdx.gl.glViewport((int)r.x, (int)r.y, (int)r.width, (int)r.height);
	OrthographicCamera c = Game.getGameCamera();
	mapRenderer.setView(c);
	mapRenderer.render();
	Gdx.gl.glViewport(0, 0, (int)Game.getWidth(), (int)Game.getHeight());
	Game.getBatch().setProjectionMatrix(Game.getScreenCamera().combined);
    }


    public static void load(String tmxLevel, World world){
	Level.world = world;
	tiledMap = new TmxMapLoader().load("levels/" + tmxLevel);
	mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Game.getBatch());
	addBox2D();
	addWalls();
	setContactListener();
    }

    private static void addBox2D(){
	//	float scaleX = Game.getGameCamera().viewportWidth / Game.getWidth();
	//	float scaleY = Game.getGameCamera().viewportHeight / Game.getHeight();
	float scaleX = 1;
	float scaleY = 1;
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
		bodyDef.position.x = Pixels.toMeters(c.x * tileSize + x2 * tileSize / 2) * scaleX;
		bodyDef.position.y = Pixels.toMeters(c.y * tileSize + tileSize / 2) * scaleY;
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Pixels.toMeters(x2 * tileSize / 2) * scaleX, Pixels.toMeters(tileSize / 2) * scaleY);
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
	    bodyDef.position.x = Pixels.toMeters(c.x * tileSize + tileSize / 2) * scaleX;
	    bodyDef.position.y = Pixels.toMeters(c.y * tileSize + y2 * tileSize / 2) * scaleY;
	    bodyDef.type = BodyType.StaticBody;
	    Body body = world.createBody(bodyDef);
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(Pixels.toMeters(tileSize / 2) * scaleX, Pixels.toMeters(y2 * tileSize / 2) * scaleY);
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

    private static void addWalls(){
	walls = new EmptyEntity[4];
	for(int i = 0; i < 4; i++){ // I = 0 = UPPER |||| I = 1 = RIGHT |||| I = 2 = LOWER |||| I = 3 = LEFT
	    Rectangle wallRectangle =  getWallRectangle(i);

	    BodyDef bodyDef = new BodyDef();
	    bodyDef.position.set(wallRectangle.x, wallRectangle.y);
	    bodyDef.type = BodyType.StaticBody;

	    PolygonShape polygonShape = new PolygonShape();
	    polygonShape.setAsBox(wallRectangle.width / 2, wallRectangle.height / 2);

	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.density = 0;
	    fixtureDef.friction = 0;
	    fixtureDef.restitution = 0;
	    fixtureDef.shape = polygonShape;

	    CollisionBody collisionBody = new CollisionBody(bodyDef, fixtureDef, world);
	    collisionBody.getBody().setUserData("wall" + i);
	    collisionBody.getFixture().setUserData("wall" + i);

	    walls[i] = new EmptyEntity(new Sprite(), collisionBody);
	}
    }

    private static Rectangle getWallRectangle(int i){
	Rectangle rect = new Rectangle();
	Rectangle view = Game.getMapViewport();
	if(i == 0){
	    rect = new Rectangle(Pixels.toMeters(view.x + view.width / 2), Pixels.toMeters(view.y + view.height), Pixels.toMeters(view.width), Pixels.toMeters(1));
	}
	else if(i == 1){
	    rect = new Rectangle(Pixels.toMeters(view.x + view.width), Pixels.toMeters(view.y + view.height / 2), Pixels.toMeters(1), Pixels.toMeters(view.height));
	}
	else if(i == 2){
	    rect = new Rectangle(Pixels.toMeters(view.x + view.width / 2), Pixels.toMeters(view.y), Pixels.toMeters(view.width), Pixels.toMeters(1));
	}
	else if(i == 3){
	    rect = new Rectangle(Pixels.toMeters(view.x), Pixels.toMeters(view.y + view.height / 2), Pixels.toMeters(1), Pixels.toMeters(view.height));
	}
	return rect;
    }

    private static void setContactListener(){
	world.setContactListener(new ContactListener(){

	    @Override
	    public void beginContact(Contact contact) {
		if(check(walls[0], contact)){
		    System.out.println("UPPER WALL");
		}
		else if(check(walls[1], contact)){
		    System.out.println("RIGHT WALL");
		}
		else if(check(walls[2], contact)){
		    System.out.println("LOWER WALL");
		}
		else if(check(walls[3], contact)){
		    System.out.println("LEFT WALL");
		}
	    }

	    @Override
	    public void endContact(Contact contact) {

	    }

	    @Override
	    public void preSolve(Contact contact, Manifold oldManifold) {

	    }

	    @Override
	    public void postSolve(Contact contact, ContactImpulse impulse) {

	    }

	});
    }

    private static boolean check(Entity e, Contact c){
	Fixture a = c.getFixtureA();
	Fixture b = c.getFixtureB();
	if(a.getUserData() == null){
	    System.out.println("A fixture you just collied with or you's user data == null, fix this");
	    return false;
	}
	if(b.getUserData() == null){
	    System.out.println("B fixture you just collied with or you's user data == null, fix this");
	    return false;
	}


	if(a.getUserData().equals(e.getFixture().getUserData())){
	    return true;
	}
	else if(b.getUserData().equals(e.getFixture().getUserData())){
	    return true;
	}
	return false;
    }
}

