package tp1.logic;

import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.*;

public interface GameWorld {
    public void doInteractionsFrom(GameItem item);
    public boolean isInsideBoard(Position pos);
    public boolean isSolid(Position pos);
    public void addPoints(int p);
    public boolean playerLoses();
    public int isNotInBoard(Position pos);
    public void marioExited();
    public void addObject(GameObject o) throws OffBoardException;
    public GameObjectContainer getObjectContainer();
    public void setMario(Mario mario);
}
    

