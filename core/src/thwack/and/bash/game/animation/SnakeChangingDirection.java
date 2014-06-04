package thwack.and.bash.game.animation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

import com.badlogic.gdx.math.Vector2;

public class SnakeChangingDirection {
	private float x;
	private float y;
	private float angle;
	SnakeHead head;
	SnakePart tummy;
	SnakePart tail;
	List<SnakePart> body;

	private SnakeChangingDirection() {
	}

	private void init() {
		head = new SnakeHead();
		tummy = new SnakePart();
		tail = new SnakePart();
		// assuming that our snake is a retarded with only one head, tummy and
		// tail ;)
		body = new ArrayList<SnakePart>();
		body.add(tummy);
		body.add(tail);
	}

	class SnakeHead {
		Vector2 direction = new Vector2();
		Vector2 position = new Vector2();
		double angelspeed;
		ArrayList<Vector2> pastPositions = new ArrayList<Vector2>();
	}

	class SnakePart {
		Vector2 position = new Vector2();
		double distanceToHead;
	}
	
	public SnakeHead getHead() {
		return head;
	}

	public List<SnakePart> getBody() {
		return body;
	}

	public SnakeChangingDirection(float x, float y) {
		this.x = x;
		this.y = y;
		init();
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
		doSnakeMove(speed, direction, delta);

		System.out.println(this.getClass() + ": angle = " + angle);
	}

	private double getDistance(Vector2 point1, Vector2 point2) {
		double retVal = 0;
		float x1, y1, x2, y2;
		x1 = point1.x;
		y1 = point1.y;
		x2 = point2.x;
		y2 = point2.y;

		retVal = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));	//TODO slow!!!
		//retVal = x2 - x1;
		
		return retVal;
	}

	private void doSnakeMove(float speed, float direction, float delta) {
		head.pastPositions.add(head.position);
		doSimpleRotate(speed, 0, delta);

		double distancetohead = 0;
		double nextdistancetohead = -1;
		for (int i = head.pastPositions.size() - 1; i > 0; i--) {
			nextdistancetohead = distancetohead
					+ getDistance(head.pastPositions.get(i),
							head.pastPositions.get(i - 1));

			for (SnakePart snakePart : body) {
				if (nextdistancetohead < getDistance(head.position,
						snakePart.position)) { // this logic might need
												// adjustment
					snakePart.position.x = ((Vector2) head.pastPositions.get(i)).x;
				}
			}
			distancetohead = nextdistancetohead;
		}

		if (head.pastPositions.size() > 10000) {
			head.pastPositions.remove(0);
		}
		
		x = head.pastPositions.get(0).x;
		y = head.pastPositions.get(0).y;		
//		x = head.pastPositions.get(head.pastPositions.size()-1).x;
//		y = head.pastPositions.get(head.pastPositions.size()-1).y;		
//		x = body.get(head.pastPositions.size()-1).position.x = x;
//		y = body.get(head.pastPositions.size()-1).position.y = y;
//		x = body.get(0).position.x = x;
//		y = body.get(0).position.y = y;
	}

	public Vector2 getPosition() {
		Vector2 pos = new Vector2();
		pos.x = x;
		pos.y = y;

		return pos;
	}

	private void doSimpleRotate(float speed, float direction, float delta) {
		angle += delta * speed;
		angle %= 360; // the angle must be <= 360
		while (angle < 0) {
			// can not be negative values
			angle += 360;
		}
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
}