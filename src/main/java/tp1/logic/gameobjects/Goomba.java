package tp1.logic.gameobjects;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.GameWorld;
import tp1.logic.GameItem;
import tp1.logic.MovingObject;
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.GameObject;

public class Goomba extends MovingObject{


    public Goomba(int dir, boolean isFalling, Position position, GameWorld world){
        super(dir,isFalling,position,world);
        this.alive = true;
        this.icon = Messages.GOOMBA;
    }

    public Goomba(){
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
        return Messages.GOOMBA;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(){
        alive = false;
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
        world.addPoints(100);
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
    public GameObject parse(String[] words, GameWorld world) throws CommandParseException{
        if(!words[1].toLowerCase().equals("goomba") && !words[1].toLowerCase().equals("g")){
            return null;
        }
        int direction = -1;
        if(words.length == 3){
            String aux = words[2];
            if(aux.toLowerCase().equals("right") || aux.toLowerCase().equals("r")) direction = 1;
            else if(aux.toLowerCase().equals("left")|| aux.toLowerCase().equals("l")) direction = -1;
            else throw new CommandParseException(Messages.UNKNOWN_MOVING_OBJECT.formatted(String.join("", words)));
        }
        try{
            Position pos = Position.parse(words[0]);
            return new Goomba(direction,false,pos,world);

        }catch(PositionParseException ppe){
            throw new CommandParseException(Messages.INVALID_POSITION.formatted(words[0]), ppe);
        }
       
       
        
    }
    @Override
    public String getDescription(){
        String aux;
        if(this.dir == 1){
            aux = "RIGHT";
        }else aux = "LEFT";
        return "("+pos.getRow()+"," + pos.getCol()+") GOOMBA " + aux;
    }
}
