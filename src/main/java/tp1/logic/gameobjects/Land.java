
package tp1.logic.gameobjects;


import tp1.logic.GameObject;
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.GameWorld;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.GameItem;

public class Land extends GameObject{
    public Land (Position posicion, GameWorld world){
        super(posicion,world);
        this.icon = Messages.LAND;
    }

    public Land(){
        super();
    }


    public Position getPosition(){
        return pos;
    }


   @Override
    public String toString(){
        return Messages.LAND;
    }

    @Override
    public String getIcon(){
        return Messages.LAND;
    }

    public boolean isSolid(){
        return true;
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

    @Override
    public boolean receiveInteraction(Mario obj) {
        return true;
    }

    @Override
    public boolean receiveInteraction(Goomba obj) {
        return true;
    }

    @Override
    public boolean receiveInteraction(Land obj) {
        return false;
    }

    @Override
    public boolean receiveInteraction(ExitDoor obj) {
        return false;
    }
    @Override
    public boolean receiveInteraction(Mushroom obj){
        return true;
    }
    @Override
    public boolean receiveInteraction(Box obj){
        return false;
    }

    @Override
    public GameObject parse(String[] words, GameWorld world) throws CommandParseException{
        if(!words[1].toLowerCase().equals("l") && !words[1].toLowerCase().equals("land")){
            return null;
        }
        try {
            Position pos = Position.parse(words[0]);
            return new Land(pos, world);
        } catch (PositionParseException ppe) {
            throw new CommandParseException(Messages.INVALID_POSITION.formatted(words[0]) , ppe);
        }    
       
    }
    @Override
    public String getDescription(){
        return "("+pos.getRow()+"," + pos.getCol()+") LAND";
    }


}