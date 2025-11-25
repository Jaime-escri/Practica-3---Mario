package tp1.logic;

import java.util.List;
import java.util.ArrayList;
import tp1.view.Messages;


public class GameObjectContainer {
	private List<GameObject> gameObjects;
	private List<GameObject> pendingToAdd;


	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
		pendingToAdd = new ArrayList<>();
	}

	public void add(GameObject o){
		gameObjects.add(o);
	}

	public void spawnLater(GameObject o){
		pendingToAdd.add(o);
	}

	public String positionToString(Position pos){
		for(GameObject g : gameObjects){
			if(g.isAlive() && g.isInPosition(pos)){
				return g.getIcon();
			}
		}

		return Messages.EMPTY;
	}

	/*public String positionToString(Position pos){
		for(GameObject g : gameObjects){
			if(g.getPosition().equals(pos) && g.isAlive()){
				return g.getIcon();
			}
		}
		return Messages.EMPTY;
	} */

	public void update(){
		for(GameObject g : gameObjects){
			g.update();
		}
		

		removeDeadObjects();

		if(!pendingToAdd.isEmpty()){
			gameObjects.addAll(pendingToAdd);
			pendingToAdd.clear();
		}
	}

	

	

	/*public void removeDeadObjects(){
		for(int i = 0; i < gameObjects.size(); i++){
			GameObject obj = gameObjects.get(i);
			if(!obj.isAlive()){
				gameObjects.remove(i);
				i--;
			}
			
		}

	}*/



	public void removeDeadObjects() {

		// Lista temporal
		List<GameObject> toRemove = new ArrayList<>();

		// 1. Recorremos la lista y solo anotamos los que están muertos
		for (GameObject g : gameObjects) {
			if (!g.isAlive()) {
				toRemove.add(g);
			}
		}

    	// 2. AHORA sí eliminamos todos los muertos de golpe
		for (GameObject dead : toRemove) {
			gameObjects.remove(dead);
		}
	}


	public int size(){
		return gameObjects.size();
	}



	public void doInteractionsFrom(GameItem obj) {
		for (GameObject other : gameObjects) {
			if (obj != other && other.isAlive()) {
				obj.interactWith(other);
				other.interactWith(obj);
			}
		}
	}
	

	public void cleanObjects(){
		gameObjects.clear();
	}

}

//SOLO PARA LA LÓGICA

/*public void update(Game game, List<Action> actions){

		mario.update(game, actions); //Actualizamos a mario
		if(mario.interactWith(exitDoor)){ 
			game.marioExited();
		}

		removeDeadObjects(); //Si mario ha tocado a un goomba, este muere, y lo quitamos antes de actualizarlo
		
		for(Goomba goomba : goombas){
			goomba.update(game); //Actualizamos a los goombas para ver si estos, después del movimiento de mario, interaccionan con él
		}

		doInteractionsFrom(mario);
		removeDeadObjects(); //Si han interaccionado, los eliminamos 

		if(mario.isAlive() == false){
			game.playerLoses();
		}	
	} 
		
	
	public void cleanGoombas(){
		goombas.clear();
	}

	public void doInteractionsFrom(Mario mario){
		for(Goomba g : goombas){
			boolean interact = mario.interactWith(g);
			if(interact && !g.isAlive()){
				game.addPoints();
			}
			
		}
	}

*/


