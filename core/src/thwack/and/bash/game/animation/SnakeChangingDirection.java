package thwack.and.bash.game.animation;

public class SnakeChangingDirection {
	private float x;
	private float y;
	private float angle;

	public SnakeChangingDirection(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void move(float speed, float direction, float delta) {
		//just simple rotation for now
		angle += delta*speed;
		angle %= 360;      //the angle must be <= 360
		while (angle < 0)  //can not be negative values
		    angle+=360;
		
		System.out.println(this.getClass() +": angle = " + angle);
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
}