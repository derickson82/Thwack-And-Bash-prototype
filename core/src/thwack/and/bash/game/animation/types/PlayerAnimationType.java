package thwack.and.bash.game.animation.types;

public enum PlayerAnimationType {
    UP(0), LEFT(1), DOWN(2), RIGHT(3), ;

    private PlayerAnimationType(int id){
	this.ID = id;
    }

    public final int ID;
}
