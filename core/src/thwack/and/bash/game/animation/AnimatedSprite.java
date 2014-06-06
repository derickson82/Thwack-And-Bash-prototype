package thwack.and.bash.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSprite {

	private int frameCols; // #1
	private int frameRows; // #2

	Animation animation; // #3
	Texture spriteSheet; // #4
	TextureRegion[] animationFrames; // #5
	SpriteBatch spriteBatch; // #6
	TextureRegion currentFrame; // #7

	public AnimatedSprite(String spriteSheetFile, int frameRows, int frameCols) {
		spriteSheet = new Texture(Gdx.files.internal(spriteSheetFile)); // #9
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth() / frameCols, spriteSheet.getHeight()
						/ frameRows); // #10
		animationFrames = new TextureRegion[frameCols * frameRows];
		int index = 0;
		for (int i = 0; i < frameRows; i++) {
			for (int j = 0; j < frameCols; j++) {
				animationFrames[index++] = tmp[i][j];
			}
		}
		animation = new Animation(0.025f, animationFrames);
	}

	public Animation getAnimation() {
		return animation;
	}

	public int getFrameCols() {
		return frameCols;
	}

	public void setFrameCols(int frameCols) {
		this.frameCols = frameCols;
	}

	public int getFrameRows() {
		return frameRows;
	}

	public void setFrameRows(int frameRows) {
		this.frameRows = frameRows;
	}
	
}
