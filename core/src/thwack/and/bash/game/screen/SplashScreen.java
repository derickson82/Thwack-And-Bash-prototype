package thwack.and.bash.game.screen;

import thwack.and.bash.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements GameScreen{

    private Texture cdiLogo;
    private float elapsedTime;

    @Override
    public void update(float delta) {
	elapsedTime += delta;
	if(elapsedTime >= 5){
	    startNextScreen();
	}
	if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
	    startNextScreen();
	}
    }

    @Override
    public void render(SpriteBatch batch) {
	batch.begin();
	batch.draw(cdiLogo, Game.getWidth() / 2 - cdiLogo.getWidth() / 2, Game.getHeight() / 2 - cdiLogo.getHeight() / 2);
	batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
	Game.setClearColor(Color.WHITE);
	cdiLogo = new Texture(Gdx.files.internal("textures/cdi.png"));
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
	cdiLogo.dispose();
    }

    private void startNextScreen(){
	Game.setScreen(new MainMenuScreen());
	Game.setClearColor(Color.BLACK);
    }

}
