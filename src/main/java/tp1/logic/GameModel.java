package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;

public interface GameModel{
    public boolean isFinished();
	public void update();
	public void reset(int level);
    public void addAction(Action a);
    public void setFinish();
    public void setLevel(int level);
    public void save(String filename) throws GameModelException;
    public void load(String filename) throws GameLoadException;
}