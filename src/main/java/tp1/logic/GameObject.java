package tp1.logic;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.view.Messages;

public abstract class GameObject implements GameItem{
    protected Position pos;
    protected GameWorld world;
    protected boolean alive;
    protected String icon;

    public GameObject(Position pos, GameWorld world){
        this.pos = pos;
        this.world = world;
        this.alive = true;
        this.icon = Messages.EMPTY;
    }

    protected GameObject(){

    }

    public Position getPosition(){
        return this.pos;
    }

    public void setPosition(Position pos){
        this.pos = pos;
    }

    public boolean isSolid(){
        return false;
    }

    public boolean isAlive(){
        return alive;
    }

    public String getIcon(){
        return icon;
    }

    public abstract void update();

    public boolean isInPosition(Position position){
        return this.pos.equals(position);
    }

    public GameObject parse(String[] objWords, GameWorld world) throws CommandParseException{
        return null;
    }
}
