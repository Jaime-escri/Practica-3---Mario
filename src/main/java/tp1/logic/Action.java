package tp1.logic;


/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);
	
	private int x;
	private int y;
	
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	public static Action stringToAction(String word){

		if(word == null){
			return STOP;
		}

		switch (word.toUpperCase()) {
			case "RIGHT" , "R":{
				return RIGHT;
			}case "LEFT" , "L":{
				return LEFT;
			}case "UP" , "U": {
				return UP;
			}case "DOWN" , "D": {
				return DOWN;
			}case "STOP", "S":{
				return STOP;
			}
				
			default: return null;
		}
	}

	public static String actionToString(Action action){
		
		switch (action) {
			case RIGHT:{
				return "RIGHT";
			}
			case LEFT: {
				return "LEFT";
			}
			case UP: {
				return "UP";
			}
			case DOWN:{
				return "DOWN";
			}
				
			default: return " ";
		}
		
	}

	
}
