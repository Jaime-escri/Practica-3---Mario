package tp1.logic.gameobjects;

import java.util.concurrent.CompletionException;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
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
        if(!alive){
            return;
        }

        Position below = Position.move(pos, Action.DOWN); //Posición de debajo, primera comprobación para caída automática
        boolean haySuelo = world.isSolid(below); //Comprobamos si hay suelo para siguiente mov automático

        if(!haySuelo){
            pos = below;

            if(!world.isInsideBoard(Position.move(below, Action.DOWN))){ //Si cae y no hay land, muere el goomba
                alive = false;
            }
            return;     
        }

        Position next = Position.move(pos, Action.RIGHT); //Si hay suelo, comenzamos a moverlo a la derecha (dir = 1)
        boolean hayObstaculo = world.isSolid(next); //Si hay obstaculo, movemos a la dirección contraria (izquierda)

        if(hayObstaculo){
            dir = -dir;

        }else{
            pos = next;
        }


        dir = world.isNotInBoard(pos);
        world.doInteractionsFrom(this);
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
        if(!words[2].toLowerCase().equals("mushroom") && !words[2].toLowerCase().equals("mu")){
            return null;
        }
        if(words.length < 3){
            throw new CommandParseException("Imposible to complete Mushroom, must be minimum 3 arguments");   
        }
        int direction = 1;

        try{
            Position pos = Position.parse(words[1]);
            return new Mushroom(direction,false,pos,world);
        }catch(PositionParseException ppe){
            throw new CommandParseException("Imposible position to parse", ppe);
        }
        
        
    }
     
}
