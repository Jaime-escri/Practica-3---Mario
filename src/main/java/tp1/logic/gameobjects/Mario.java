package tp1.logic.gameobjects;


import tp1.logic.GameWorld;
import tp1.logic.MovingObject;
import tp1.logic.Position;
import tp1.view.Messages;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameObject;


public class Mario extends MovingObject{

	//Quitamos el atributo de la posición inidical por duda de si se cae fuera del tablero se reinicia todo el nivel o no
	private Action lastAction;
	private boolean big;
	private List<Action> actions; //Atributo porque el update no lleva parámetros
	
	public Mario(int dir, boolean isFalling, Position pos, boolean aux, GameWorld world){
		super(dir,isFalling,pos,world);
		this.icon = Messages.MARIO_RIGHT;
		this.big = aux;
		this.actions = new ArrayList<>();

		world.setMario(this);
	}

	public Mario(){
		super();
	}


	@Override
	public String getIcon(){
		return icon;
	}

	public Position getPosition(){
		return pos;
	}

	public boolean isSolid(){
		return false;
	}

	public boolean isAlive(){
		return alive;
	}

	public boolean isBig(){
		return big;
	}

	public boolean isInPosition(Position pos){
		return occupies(pos);
	}

	public void update() { //Exception
		Position before = this.getPosition();
		ignoreInstructions(actions);
		doActions(actions, world); 
		actions.clear();


		if(pos.equals(before)){
			automaticMovement(world);
		}
		world.doInteractionsFrom(this);

	}

	public void doActions(List<Action> actions, GameWorld world){ //Exception

		for(int i = 0; i < actions.size(); i++){
			Action a = actions.get(i); //"a" la usamos para igualarla al lastAction, que sirve para ver si mario, al tocar un goomba, venía de abajo
			Position before = this.getPosition();
			String currentAction = Action.actionToString(actions.get(i)); //Cogemos la acción iésima para ejecutarla
			Position newPosition = Position.move(pos,a);

			switch (currentAction) {			
				case "LEFT":{
					if(!obstacle(newPosition, world)){
						pos = newPosition;
						icon = Messages.MARIO_LEFT;
					}
					break;
					
				}
				case "RIGHT":{
					if(!obstacle(newPosition, world)){
						pos = newPosition;
						icon = Messages.MARIO_RIGHT;
					}
					break;
					
				}
				case "DOWN":{
					while(!obstacle(newPosition, world)){
						pos = newPosition;
						icon = Messages.MARIO_STOP;

						if(!world.isInsideBoard(Position.move(pos,Action.DOWN))){
							this.alive = false;
						}							
						newPosition = Position.move(newPosition,a);
        			}
					break;
				}
				case "UP":{

					if(isBig()){
						Position headUp = new Position(pos.getRow() -2, pos.getCol()); //Posición arriba de la cabeza
						Position head = Position.move(pos, Action.UP);
						if(!obstacle(headUp, world)){
							pos = head;
						}
					}else{
						if(!obstacle(newPosition, world)){
							pos = newPosition;						
						}
					}		
					break;
				}case "STOP":{
					icon = Messages.MARIO_STOP;
				}
			
				default:
					break;
			}
			//He quitado de aqui la condición de que si mario no se ha movido llame a automaticMovement y luego a world.doInteractionsFrom
			if(!pos.equals(before)){
				lastAction = a;
			}
		}

	}

	public boolean obstacle(Position pos, GameWorld world){
		return world.isSolid(pos);
	}

	public void automaticMovement(GameWorld world){//Exception
		if(!alive){
            return;
        }
		Position below = Position.move(pos,Action.DOWN);
        if(!obstacle(below, world)){
            pos = below;
			icon = Messages.MARIO_STOP;

            if(!world.isInsideBoard(Position.move(pos, Action.DOWN))){
                alive = false;	
				world.playerLoses(); //Mario se ha caído, pierde vida
            }
            return;     
        }
		Position next;
		if(dir == 1) next = Position.move(pos, Action.RIGHT);
		else next = Position.move(pos,Action.LEFT);


        if (obstacle(next, world)) {
        	dir = -dir;
        	if (dir == 1) icon = Messages.MARIO_RIGHT;
        	else icon = Messages.MARIO_LEFT;
        	return;
    	}

		pos = next;

        if (dir == 1) icon = Messages.MARIO_RIGHT;
    	else icon = Messages.MARIO_LEFT;
	}


	public boolean occupies(Position p) { 
    	if (p.equals(pos)){
			return true;
		}
    	if (big && p.equals(Position.move(pos, Action.UP))){
			return true;
		} 
   	 	return false;
	}

	public void addAction(Action a){
		actions.add(a);
	}

	public static boolean isMarioAlive(){
		return true;
	}

	public void ignoreInstructions(List<Action> actions){//Exception2
		for(int i = 0; i < actions.size() - 1; i++){

            String current = Action.actionToString(actions.get(i));
            if(current == "LEFT"){
                for(int j = i + 1; j < actions.size(); j++){

                    String next = Action.actionToString(actions.get(j));
                    if(next == "RIGHT"){
                        actions.remove(j);
                    }
                }

            }
           if(current == "RIGHT"){
            for(int j = i + 1; j < actions.size(); j++){

                    String next = Action.actionToString(actions.get(j));
                    if(next == "LEFT"){
                        actions.remove(j);
                    }
                }
           }
            
            if(current == "DOWN"){
            for(int j = i + 1; j < actions.size(); j++){

                    String next = Action.actionToString(actions.get(j));
                    if(next == "UP"){
                        actions.remove(j);
                    }
                }
           }

            if(current == "UP"){
            for(int j = i + 1; j < actions.size(); j++){

                    String next = Action.actionToString(actions.get(j));
                    if(next == "DOWN"){
                        actions.remove(j);
                    }
                }
           }
           
        }
	}

	public void cleanActions(){
		this.actions.clear();
	}

	@Override
	public boolean interactWith(GameItem other) {//Exception
		//Si es grande comparamos su cabeza
		if(isBig()){
			Position head = Position.move(this.pos, Action.UP);
			boolean aux = other.isInPosition(head);
			if(aux){
				other.receiveInteraction(this);
			}
		}else{
			boolean canInteract = other.isInPosition(this.pos);
			if (canInteract) {
				other.receiveInteraction(this); 
			}
			return canInteract;
		}

		return false;
	
	}

	public  boolean receiveInteraction(Land obj){
		return false;
	}
	public  boolean receiveInteraction(ExitDoor obj){
		return true;
	}
	public  boolean receiveInteraction(Mario obj){
		return false;
	}

	public boolean receiveInteraction(Goomba obj){
		if(!pos.equals(obj.getPosition())){
			return false;
		}
		

        if(lastAction == Action.DOWN){ //Viene de arriba y va hacia abajo 
			return true;
		}else{
			if(this.isBig()){
				big = false;
			}else{
				this.alive = false;
			}
		}
		return true;
	}

	@Override
	public GameObject parse(String[] words, GameWorld world)throws CommandParseException{
		//Valores por defecto, right big
		if(!words[1].toLowerCase().equals("m") && !words[1].toLowerCase().equals("mario")){
			return null;
		}

		//Si la long. es  > 2, entonces especifica los atributos de mario
		int direction = 1;
		boolean aux = true;
		if(words.length > 2){
			String s = words[3].toLowerCase();
			if(s != null){
				if(s.equals("r") || s.equals("right")){
					direction = 1;
				} 
				else if(s.equals("l") || s.equals("left")){
					direction = -1;
				}else{
					throw new CommandParseException(Messages.UNKNOWN_MOVING_OBJECT.formatted(String.join(" ",words)));
				}
			}
			s = words[2].toLowerCase();
			if(s!=null){
				if(s.equals("b") || s.equals("big")) aux = true;
				else if(s.equals("s") || s.equals("small")) aux = false;
				else throw new CommandParseException(Messages.INVALID_SIZE.formatted(String.join(" ",words)));
			}
		}
		
		try {
			Position pos = Position.parse(words[0]);
			return new Mario(direction, false, pos, aux, world);
		} catch (PositionParseException ppe) {
			throw new CommandParseException(Messages.INVALID_POSITION.formatted(words[0]) , ppe);
		}
		
		
	}

	public boolean receiveInteraction(Mushroom obj){
		if(this.isBig()){
			this.big = false;
		}else{
			this.big = true;
		}
		return true;
	}
	public boolean receiveInteraction(Box obj){
		return true;
    }

	public Action getLastAction(){
		return lastAction;
	}

	public Mario retMario(){
		return this;
	}

	@Override
	public String getDescription(){
		String aux, aux1;
		if(isBig()){
			aux = "BIG ";
		}else aux = "SMALL ";

		if(dir == 1) aux1 = "RIGHT";
		else aux1 = "LEFT";
        return "("+pos.getRow()+"," + pos.getCol()+") MARIO " + aux +aux1;
    }
	@Override
	public boolean isMario(){
		return true;
	}

}


