package thwack.and.bash.game.entity.mob.ai;

/**
 * Right now a simple state machine
 * @author Theodor
 *
 */
public class AI {

    public AI(){
	state = 0;
    }

    private int state;

    public void setState(int state){
	this.state = state;
    }

    public int getState(){
	return state;

    }

}
