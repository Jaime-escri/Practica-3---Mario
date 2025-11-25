package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.control.Command;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;

public class GameObjectFactory  {
    private static final List<GameObject> availableObjects = Arrays.asList(
        new Mario(),
        new Goomba(),
        new Land(),
        new ExitDoor(),
        new Mushroom(),
        new Box()
    );

    public static GameObject parse(String[] objWords, GameWorld world) throws GameModelException{
        for(GameObject a : availableObjects){
            if(objWords.length <= 2){
                throw new ObjectParseException("Insuficient arguments");
            }
            try {
                GameObject parsed = a.parse(objWords, world);
                if(parsed != null){
                return parsed;
            }
            } catch (CommandParseException cpe) {
                throw new GameModelException("Fail parsed" , cpe);
            }  
        }
        throw new ObjectParseException("Invalid object to add");
    }
}
