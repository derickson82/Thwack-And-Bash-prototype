package thwack.and.bash.game.animation.types;

public enum SnakeAnimationType {
	IDLING(0);	//set initial animation

	private SnakeAnimationType(int id) {
		this.ID = id;
	}

	public final int ID;
}
