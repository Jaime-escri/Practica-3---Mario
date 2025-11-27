package tp1.logic;

import java.util.List;

import tp1.logic.gameobjects.Mario;

public interface GameConfiguration {
   public int getRemainingTime();
   public int points();
   public int numLives();
   // game objects
   public Mario getMario();
   public List<GameObject> getNPCObjects();
}