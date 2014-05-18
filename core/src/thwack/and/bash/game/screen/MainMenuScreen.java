package thwack.and.bash.game.screen;

import thwack.and.bash.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements GameScreen{

    private Stage stage;
    private ScreenViewport viewport;

    @Override
    public void update(float delta) {
	viewport.update();
	stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
	stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
	viewport = new ScreenViewport(Game.getScreenCamera());
	stage = new Stage(viewport, Game.getBatch());

	TextButton play = new TextButton("Play", Game.getBlueTextButtonStyle());
	TextButton options = new TextButton("Options", Game.getBlueTextButtonStyle());
	TextButton exit = new TextButton("Exit", Game.getBlueTextButtonStyle());

	Table table = new Table();
	table.setBounds(0, 0, viewport.getViewportWidth(), viewport.getViewportHeight());

	table.add(play).pad(50);
	table.row();
	table.add(options).pad(50);
	table.row();
	table.add(exit).pad(50);

	stage.addActor(table);

	play.addListener(new ClickListener(){
	    @Override
	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		Game.setScreen(new PlayScreen());
		return true;
	    }
	});

	options.addListener(new ClickListener(){
	    @Override
	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		Game.setScreen(new OptionsScreen());
		return true;
	    }

	});

	exit.addListener(new ClickListener(){
	    @Override
	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		Gdx.app.exit();
		return true;
	    }
	});

	Gdx.input.setInputProcessor(stage);
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
	stage.dispose();
    }

}
