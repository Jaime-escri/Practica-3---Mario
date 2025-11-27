package tp1.view;

import tp1.logic.GameStatus;

public abstract class GameView implements ViewInterface{

	protected GameStatus status;
	
	public GameView(GameStatus status) { //Constructor
		this.status = status;
	}
	
}
