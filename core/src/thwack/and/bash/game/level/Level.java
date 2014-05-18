package thwack.and.bash.game.level;

import java.util.ArrayList;
import java.util.Iterator;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.EmptyEntity;
import thwack.and.bash.game.entity.Entity;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.util.Util.Pixels;
import thwack.and.bash.game.util.Util.Values;

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
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

// TODO: ADD SO WHEN THE Values.CHUNK_SIZE < MAPVIEwPORT, SCALE, COULD CAMERA.ZOOM BE A SOLUTION

public class Level {

    private Level(){
    }

    private static Vector2 currentChunk = new Vector2(0,0);
    private static World world;
    private static TiledMap currentTiledMap;
    private static OrthogonalTiledMapRenderer mapRenderer;


    private static EmptyEntity[] walls; // I = 0 = UPPER |||| I = 1 = RIGHT |||| I = 2 = LOWER |||| I = 3 = LEFT
    private static Player player;

    private static Array<Runnable> runnables = new Array<Runnable>();
    private static Array<Array<TiledMap>> tiledMapChunks; //Chunks of the map; //FIRST ROW / X; THEN COL / Y

    public static void update(float delta){
	world.step(1 / 60f, 6, 2);
	for(int i = 0; i < runnables.size; i++){
	    Runnable r = runnables.get(i);
	    r.run();
	}
	runnables.clear();
	System.out.println(player.getPosition());
    }

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
	tiledMapChunks = loadWholeMap(new TmxMapLoader().load("levels/" + tmxLevel));
	currentChunk.set(0, 0);
	currentTiledMap = loadChunk();
	mapRenderer = new OrthogonalTiledMapRenderer(currentTiledMap, Game.getBatch());
	addBox2D();
	addWalls();
	setContactListener();
	player = (Player) Entity.getEntity("player");
    }

    public static float getLevelX(){
	return currentChunk.x * Values.CHUNK_SIZE_X;
    }

    public static float getLevelY(){
	return currentChunk.y * Values.CHUNK_SIZE_Y;
    }

    public static World getWorld(){
	return world;
    }

    private static void clearLevelBox2D(){
	if(!world.isLocked()){
	    Array<Body> bodies = new Array<Body>();
	    world.getBodies(bodies);
	    for(int i = 0; i < bodies.size; i++){
		if(bodies.get(i).getUserData() == null) {
		    continue;
		}
		if(bodies.get(i).getUserData().equals("tile")) {
		    removeBody(bodies.get(i));
		}
	    }
	}
    }

    private static void removeBody(Body body){
	Array<JointEdge> list = body.getJointList();
	while(list.size > 0){
	    world.destroyJoint(list.get(0).joint);
	}
	world.destroyBody(body);
    }

    private static void addBox2D(){
	//TODO: TEMPORARY FIX; GDX.GL.GLVIEWPORT F*CKS THINGS UP
	float xScale = Game.getMapViewport().width / Game.getWidth();
	float yScale = Game.getMapViewport().height / Game.getHeight();
	float xOffset = Pixels.toMeters(Game.getMapViewport().x);
	float yOffset = Pixels.toMeters(Game.getMapViewport().y);
	//	float xOffset = 0;
	//	float yOffset = 0;
	float tileSize = 0;
	ArrayList<Box2DCell> cells = new ArrayList<Box2DCell>();
	MapLayer mapLayer = currentTiledMap.getLayers().get("collision");
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
		bodyDef.position.x = Pixels.toMeters(c.x * tileSize + x2 * tileSize / 2) * xScale + xOffset;
		bodyDef.position.y = Pixels.toMeters(c.y * tileSize + tileSize / 2) * yScale + yOffset;
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		body.setUserData("tile");
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Pixels.toMeters(x2 * tileSize / 2) * xScale, Pixels.toMeters(tileSize / 2) * yScale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0;
		Fixture f = body.createFixture(fixtureDef);
		f.setUserData("tile");
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
	if(yCells.size() != 0) {
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
		bodyDef.position.x = Pixels.toMeters(c.x * tileSize + tileSize / 2) * xScale + xOffset;
		bodyDef.position.y = Pixels.toMeters(c.y * tileSize + y2 * tileSize / 2) * yScale + yOffset;
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		body.setUserData("tile");
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Pixels.toMeters(tileSize / 2) * xScale, Pixels.toMeters(y2 * tileSize / 2) * yScale);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0;
		Fixture f = body.createFixture(fixtureDef);
		f.setUserData("tile");
		yCells.remove(c);
		if(yCells.size() <= 0){
		    break;
		}
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
	    rect = new Rectangle(Pixels.toMeters(view.x + view.width / 2 + currentChunk.x * Values.CHUNK_SIZE_X), Pixels.toMeters(view.y + view.height + currentChunk.y * Values.CHUNK_SIZE_Y), Pixels.toMeters(view.width), Pixels.toMeters(1));
	}
	else if(i == 1){
	    rect = new Rectangle(Pixels.toMeters(view.x + view.width + currentChunk.x * Values.CHUNK_SIZE_X), Pixels.toMeters(view.y + view.height / 2 + currentChunk.y * Values.CHUNK_SIZE_Y), Pixels.toMeters(1), Pixels.toMeters(view.height));
	}
	else if(i == 2){
	    rect = new Rectangle(Pixels.toMeters(view.x + view.width / 2 + currentChunk.x * Values.CHUNK_SIZE_X), Pixels.toMeters(view.y + currentChunk.y * Values.CHUNK_SIZE_Y), Pixels.toMeters(view.width), Pixels.toMeters(1));
	}
	else if(i == 3){
	    rect = new Rectangle(Pixels.toMeters(view.x + currentChunk.x * Values.CHUNK_SIZE_X), Pixels.toMeters(view.y + view.height / 2 + currentChunk.y * Values.CHUNK_SIZE_Y), Pixels.toMeters(1), Pixels.toMeters(view.height));
	}
	return rect;
    }

    private static void setContactListener(){
	world.setContactListener(new ContactListener(){

	    @Override
	    public void beginContact(Contact contact) {
		if(check(walls[0], contact)){
		    runnables.add(new Runnable(){
			@Override
			public void run(){
			    Vector2 c = new Vector2(0, 1);
			    if(updateRenderer(c)) {
				setPlayerPosition(c);
				clearLevelBox2D();
				addBox2D();
			    }
			}
		    });
		}
		else if(check(walls[1], contact)){
		    runnables.add(new Runnable(){
			@Override
			public void run(){
			    Vector2 c = new Vector2(1, 0);
			    if(updateRenderer(c)){
				setPlayerPosition(c);
				clearLevelBox2D();
				addBox2D();
			    }
			}
		    });
		}
		else if(check(walls[2], contact)){
		    runnables.add(new Runnable(){
			@Override
			public void run(){
			    Vector2 c = new Vector2(0, -1);
			    if(updateRenderer(c)){
				setPlayerPosition(c);
				clearLevelBox2D();
				addBox2D();
			    }
			}
		    });
		}
		else if(check(walls[3], contact)){
		    runnables.add(new Runnable(){
			@Override
			public void run(){
			    Vector2 c = new Vector2(-1, 0);
			    if(updateRenderer(c)){
				setPlayerPosition(c);
				clearLevelBox2D();
				addBox2D();
			    }
			}
		    });
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
	    //System.out.println("A fixture you just collied with or you's user data == null, fix this");
	    return false;
	}
	if(b.getUserData() == null){
	    //System.out.println("B fixture you just collied with or you's user data == null, fix this");
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


    private static boolean updateRenderer(Vector2 c){//change
	Vector2 newChunk = currentChunk.add(c);
	if(newChunk.x < 0 || newChunk.y < 0 || newChunk.x >= tiledMapChunks.size || newChunk.y >= tiledMapChunks.get(0).size){
	    currentChunk.sub(c);
	    return false;
	}
	currentChunk = newChunk;
	currentTiledMap = loadChunk();
	mapRenderer.setMap(currentTiledMap);
	return true;
    }

    private static TiledMap loadChunk(){
	System.out.println(currentChunk.y);
	return tiledMapChunks.get((int)currentChunk.x).get((int)currentChunk.y);
    }

    private static Array<Array<TiledMap>> loadWholeMap(TiledMap wholeMap){
	Array<Array<TiledMap>> maps = new Array<Array<TiledMap>>();
	TiledMapTileLayer firstLayer = (TiledMapTileLayer) wholeMap.getLayers().get(0);
	int xChunks = (int)((firstLayer.getWidth()) / Values.CHUNK_SIZE_X);
	int yChunks = (int)((firstLayer.getHeight()) / Values.CHUNK_SIZE_Y);

	for(int xChunk = 0; xChunk < xChunks; xChunk++){
	    maps.add(new Array<TiledMap>());
	    for(int yChunk = 0; yChunk < yChunks; yChunk++){
		TiledMap map = new TiledMap();
		Iterator<MapLayer> layerIterator = wholeMap.getLayers().iterator();
		while(layerIterator.hasNext()){
		    TiledMapTileLayer wholeMapLayer = (TiledMapTileLayer) layerIterator.next();
		    TiledMapTileLayer newLayer = new TiledMapTileLayer((int)Values.CHUNK_SIZE_X, (int)Values.CHUNK_SIZE_Y, (int) firstLayer.getTileWidth(), (int)firstLayer.getTileHeight());
		    for(int xTile = 0; xTile < Values.CHUNK_SIZE_X; xTile++){
			for(int yTile = 0; yTile < Values.CHUNK_SIZE_Y; yTile++){
			    newLayer.setCell(xTile, yTile, wholeMapLayer.getCell((int)(xTile + xChunk * Values.CHUNK_SIZE_X), (int)(yTile + yChunk * Values.CHUNK_SIZE_Y)));
			}
		    }
		    newLayer.setName(wholeMapLayer.getName());
		    map.getLayers().add(newLayer);
		}
		maps.get(xChunk).add(map);
	    }
	}
	return maps;
    }

    private static void setPlayerPosition(Vector2 c){ // c == change
	float scale = 2;
	if(c.x > 0){
	    player.setX(Pixels.toMeters(Game.getMapViewport().x + player.getSprite().getWidth() + scale));
	}
	else if(c.y > 0){
	    player.setY(Pixels.toMeters(Game.getMapViewport().y + player.getSprite().getHeight() + scale));
	}
	else if(c.x < 0){
	    player.setX((Pixels.toMeters(Game.getMapViewport().x + Game.getMapViewport().width - (player.getSprite().getWidth() + -scale))));
	}
	else if(c.y < 0){
	    player.setY(Pixels.toMeters(Game.getMapViewport().y + Game.getMapViewport().height - (player.getSprite().getHeight() + -scale)));
	}
    }
}

