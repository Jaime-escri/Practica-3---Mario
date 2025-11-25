package tp1.logic;

import tp1.exceptions.GameModelException;

public interface GameModel{
    public boolean isFinished();
	public void update();
	public void reset(int level);
    public void addAction(Action a);
    public void setFinish();
    public void setLevel(int level);
}