package thwack.and.bash.game.screen;

import thwack.and.bash.game.Game;
import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.types.BatAnimationType;
import thwack.and.bash.game.animation.types.PlayerAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.entity.mob.Bat;
import thwack.and.bash.game.entity.mob.Player;
import thwack.and.bash.game.level.Level;
import thwack.and.bash.game.util.Util;
import thwack.and.bash.game.util.Util.Meters;
import thwack.and.bash.game.util.Util.Pixels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

//ALL THIS CODE IS USED FOR THE GAMES PROTOTYPE, IT'S NOT FINAL
public class PlayScreen implements GameScreen{

    private Bat bat;
    private Player player;
    private World world;

    private Box2DDebugRenderer box2DRenderer;

    @Override
    public void update(float delta) {
	player.update(delta);
	bat.update(delta);
	world.step(1 / 60f, 6, 2);
	Game.getCamera().position.set(Meters.toPixels(player.getX()), Meters.toPixels(player.getY()), 0);
    }

    @Override
    public void render(SpriteBatch batch) {
	Level.render();
	batch.begin();
	player.draw(batch);
	bat.draw(batch);
	batch.end();
	box2DRenderer.render(world, Game.getCamera().combined.scl(Util.PIXELS_PER_METER));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

	Game.setClearColor(Color.BLUE);

	box2DRenderer = new Box2DDebugRenderer();

	world = new World(new Vector2(0,0), false);

	Level.load("demo.tmx", world);

	//PLAYER START
	BodyDef bodyDef = new BodyDef();
	bodyDef.position.x = Pixels.toMeters(Game.getWidth() / 2);
	bodyDef.position.y = Pixels.toMeters(Game.getHeight() / 2);
	bodyDef.type = BodyType.DynamicBody;
	PolygonShape shape = new PolygonShape();
	shape.setAsBox(Pixels.toMeters(35) / 2, Pixels.toMeters(46) / 2);
	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = shape;
	CollisionBody collisionBody = new CollisionBody(bodyDef, fixtureDef, world);
	shape.dispose();

	//THIS IS JUST TEST;
	Texture upRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-upward_35x46.png"));
	TextureRegion[][] upRegions2DArray = TextureRegion.split(upRegionsSheet, 35, 46);
	TextureRegion[] upRegionsArray = toArray(upRegions2DArray, 3, 1);

	Texture leftRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-sideways_33x46.png"));
	TextureRegion[][] leftRegions2DArray = TextureRegion.split(leftRegionsSheet, 33, 46);
	TextureRegion[] leftRegionsArray = toArray(leftRegions2DArray, 3, 1);

	Texture downRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-downard_35x45.png"));
	TextureRegion[][] downRegions2DArray = TextureRegion.split(downRegionsSheet, 35, 45);
	TextureRegion[] downRegionsArray = toArray(downRegions2DArray, 3, 1);

	Texture rightRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/walking-sideways_33x46.png"));
	TextureRegion[][] rightRegions2DArray = TextureRegion.split(rightRegionsSheet, 33, 46);
	TextureRegion[] rightRegionsArray = toArray(rightRegions2DArray, 3, 1);
	flipRegions(rightRegionsArray, true, false);

	MobAnimation playerAnimation = new MobAnimation();
	playerAnimation.beginSettingAnimations();
	playerAnimation.setAnimation(new Animation(.333f, upRegionsArray), PlayerAnimationType.UP.ID);
	playerAnimation.setAnimation(new Animation(.333f, leftRegionsArray), PlayerAnimationType.LEFT.ID);
	playerAnimation.setAnimation(new Animation(.333f, downRegionsArray), PlayerAnimationType.DOWN.ID);
	playerAnimation.setAnimation(new Animation(.333f, rightRegionsArray), PlayerAnimationType.RIGHT.ID);
	playerAnimation.endSettingAnimations();

	player = new Player(playerAnimation, collisionBody);

	//PLAYER END

	//BAT START
	bodyDef = new BodyDef();
	bodyDef.position.x = 3;
	bodyDef.position.y = 3;
	bodyDef.type = BodyType.KinematicBody; //So it doesn't fly away
	shape = new PolygonShape();
	shape.setAsBox(Pixels.toMeters(64 / 2), Pixels.toMeters(62 / 2));
	fixtureDef = new FixtureDef();
	fixtureDef.shape = shape;
	collisionBody = new CollisionBody(bodyDef, fixtureDef, world);

	Texture flyingRegionsSheet = new Texture(Gdx.files.internal("textureatlas/play/input/bat_64x62.png"));
	TextureRegion[][] flyingRegions2DArray = TextureRegion.split(flyingRegionsSheet, 64, 52);
	TextureRegion[] flyingRegionsArray = toArray(flyingRegions2DArray, 3, 1);

	MobAnimation batAnimation = new MobAnimation();
	batAnimation.beginSettingAnimations();
	batAnimation.setAnimation(new Animation(.1f, flyingRegionsArray), BatAnimationType.FLYING.ID);
	batAnimation.endSettingAnimations();

	bat = new Bat(batAnimation, collisionBody);

    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }

    public TextureRegion[] toArray(TextureRegion[][] array2D, int width, int height){
	TextureRegion[] array = new TextureRegion[width * height];
	int index = 0;
	for(int i = 0; i < width; i++){
	    for(int j = 0; j < height; j++){
		array[index++] = array2D[j][i];
	    }
	}
	return array;
    }

    public TextureRegion[] flipRegions(TextureRegion[] data, boolean xMir, boolean yMir){
	for(int i = 0; i < data.length; i++){
	    data[i].flip(xMir, yMir);
	}
	return data;
    }

}
