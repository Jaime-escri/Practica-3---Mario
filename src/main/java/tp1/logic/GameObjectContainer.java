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

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(GameObject g : gameObjects){
			sb.append(g.getDescription()).append("\n");
		}
		return sb.toString();
	}


}
