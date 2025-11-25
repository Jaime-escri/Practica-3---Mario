package tp1.logic;

public abstract class MovingObject extends GameObject{
    protected int dir;
    protected boolean isFalling;
    

    public MovingObject(int dir, boolean isFalling, Position pos, GameWorld world){
        super(pos,world);
        this.dir = dir;
        this.isFalling = isFalling;
    }

    protected MovingObject(){
        super();
    }

    public void move(){
    }
}
