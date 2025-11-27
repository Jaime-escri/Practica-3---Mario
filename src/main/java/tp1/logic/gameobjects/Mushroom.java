package tp1.logic.gameobjects;


import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.GameItem;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.MovingObject;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject{

    public Mushroom(int dir, boolean isFalling, Position position, GameWorld world){
        super(dir, isFalling, position, world);
        this.alive = true;
        this.icon = Messages.MUSHROOM;
    }

    public Mushroom(){
        super();
    }

    public Position getPosition(){
        return pos;
    }

    @Override
    public boolean isSolid(){
        return false;
    }

   @Override
    public String toString(){
        return Messages.MUSHROOM;
    }

    public boolean isAlive(){
        return alive;
    }

     public void update(){//Exception
        super.update(this);
    }

    @Override
	public boolean interactWith(GameItem other) {
		boolean canInteract = other.isInPosition(this.pos);
		if (canInteract) {
			other.receiveInteraction(this); 
		}
		return canInteract;
	}

    public  boolean receiveInteraction(Land obj){
        return true;
    }

	public  boolean receiveInteraction(ExitDoor obj){
        return false;
    }

	public  boolean receiveInteraction(Mario obj){
        this.alive = false;
        return true;
    }

	public  boolean receiveInteraction(Goomba obj){
        return false;
    }

    public boolean receiveInteraction(Mushroom obj){
        return false;
    }
    public boolean receiveInteraction(Box obj){
        return true;
    }

     @Override
    public GameObject parse(String[] words, GameWorld world)throws CommandParseException{
        if(!words[1].toLowerCase().equals("mushroom") && !words[1].toLowerCase().equals("mu")){
            return null;
        }
        int direction = 1;

        try{
            Position pos = Position.parse(words[0]);
            return new Mushroom(direction,false,pos,world);
        }catch(PositionParseException ppe){
            throw new CommandParseException(Messages.INVALID_POSITION.formatted(words[0]), ppe);
        }
        
        
    }
    @Override
    public String getDescription(){
        return "("+pos.getRow()+"," + pos.getCol()+") MUSHROOM";
    }
     
}
