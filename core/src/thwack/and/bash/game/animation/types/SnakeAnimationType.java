package thwack.and.bash.game.animation.types;

public enum SnakeAnimationType {
	STOP(0), SIDE_WINDERING(1);

	private SnakeAnimationType(int id) {
		this.ID = id;
	}

	public final int ID;
}
