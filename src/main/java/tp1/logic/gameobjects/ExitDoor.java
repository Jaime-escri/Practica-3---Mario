package tp1.logic.gameobjects;

import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.GameWorld;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.GameItem;

public class ExitDoor extends GameObject{

    public ExitDoor(Position posicion, GameWorld world){
        super(posicion, world);
    }

    public ExitDoor(){
        super();
    }


    public Position getPosition(){
        return pos;
    }

    @Override
    public String toString(){
        return Messages.EXIT_DOOR;
    }

    @Override
    public String getIcon(){
        return Messages.EXIT_DOOR;
    }

    public boolean isSolid(){
        return false;
    }

    public void update(){};

    @Override
    public boolean interactWith(GameItem other) {
        boolean canInteract = other.isInPosition(this.pos);

        if (canInteract) {
            other.receiveInteraction(this);
        }

        return canInteract;
    }

    public  boolean receiveInteraction(Land obj){
        return false;
    }
	public  boolean receiveInteraction(ExitDoor obj){
        return false;
    }
	public  boolean receiveInteraction(Mario obj){
        world.marioExited();
        return true;
    }
	public  boolean receiveInteraction(Goomba obj){
        return false;
    }
    public boolean receiveInteraction(Mushroom obj){
        return false;
    }
    public boolean receiveInteraction(Box obj){
        return false;
    }
    
    public GameObject parse(String[] objWords, GameWorld world) throws CommandParseException{
        if(!(objWords[2].toLowerCase().equals("exitdoor") || objWords[2].toLowerCase().equals("ed"))){
            return null;
        }

        if(objWords.length < 3){
            throw new CommandParseException("Imposible to complete ExitDoor, must be minimum 3 arguments");
        }

        
        try {
            Position pos = Position.parse(objWords[1]);
             return new ExitDoor(pos,world);  
        } catch (PositionParseException ppe) {
            throw new CommandParseException("Imposible position to parse", ppe);
        } 
    }
    
}