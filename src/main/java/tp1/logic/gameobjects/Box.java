package tp1.logic.gameobjects;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Box extends GameObject{
    private boolean open;

    public Box(Position pos, GameWorld world, boolean open){
        super(pos,world);
        this.open = open;
        this.icon = Messages.BOX_FULL;
    }

    public Box(){
        super();
    }

    public Position getPosition(){
        return pos;
    }

    @Override
    public String toString(){
       if(this.open){
            return Messages.BOX_EMPTY;
        }
        return Messages.BOX_FULL;
    }

    @Override
    public String getIcon(){
        if(this.open){
            return Messages.BOX_EMPTY;
        }
        return Messages.BOX_FULL;
       
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

    public  boolean receiveInteraction(Land obj){
        return false;
    }
	public  boolean receiveInteraction(ExitDoor obj){
        return false;
    }
	public  boolean receiveInteraction(Mario obj){
        if(obj.getLastAction() == Action.UP && !this.open){
            Position pos1 = Position.move(this.pos, Action.UP);
            this.open = true;
            this.icon = Messages.BOX_EMPTY;
            Mushroom m = new Mushroom(1,false,pos1,world);
            world.getObjectContainer().spawnLater(m);
            world.addPoints(50);
        }
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
        //Si no es box, retorn null.
        if(!(objWords[1].toLowerCase().equals("box") || objWords[1].toLowerCase().equals("b"))){
            return null;
        }

        //Si sí que es box, empezamos a gestionar excepciones
        
        boolean aux = false;
        if (objWords.length >= 2) {
            
            String state = objWords[2].toLowerCase();
            if (state.equals("empty") || state.equals("e")) {
                aux = true;   // vacía
            } else if (state.equals("full") || state.equals("f")) {
                aux = false;  // llena
            }else{
                throw new CommandParseException("Invalid state of box, must be empty or full");
            }
        }

        //Intentamos parsear la posición, si no lo consigue, lanzamose excepción
        try {
            Position pos = Position.parse(objWords[0]);
            return new Box(pos,world,aux);  
        } catch (PositionParseException ppe) {
            throw new CommandParseException(Messages.INVALID_POSITION.formatted(objWords[0]), ppe);
        }  
    }
    @Override
    public String getDescription(){
        String aux;
        if(this.open)aux = "FULL";
        else aux = "EMPTY";
        return "("+pos.getRow()+"," + pos.getCol()+") BOX " + aux;
    }
    

}
