package thwack.and.bash.game.test;

import thwack.and.bash.game.animation.AnimatedSprite;
import thwack.and.bash.game.animation.MobAnimation;
import thwack.and.bash.game.animation.SnakeChangingDirection;
import thwack.and.bash.game.animation.SnakeWinding;
import thwack.and.bash.game.animation.types.SnakeAnimationType;
import thwack.and.bash.game.collision.CollisionBody;
import thwack.and.bash.game.collision.SnakeRaycastGuard;
import thwack.and.bash.game.entity.mob.Snake;
import thwack.and.bash.game.entity.mob.ai.AI;
import thwack.and.bash.game.test.RendererDebug;
import thwack.and.bash.game.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SnakeDebug extends Snake {

	public RendererDebug shapeRenderer;

	public SnakeDebug(CollisionBody collisionBody) {
		super(collisionBody);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw (SpriteBatch batch) {
		super.draw(batch);
		
		if(shapeRenderer != null) {
			((RendererDebug)shapeRenderer).render(batch);
		}
	}

}