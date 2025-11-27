package tp1.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;


public class FileGameConfiguration implements GameConfiguration{
    private int points;
    private int lives;
    private int time;

    private Mario mario;
    private List<GameObject> gameObjects;
    
    public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException{
        try {
            this.gameObjects = new ArrayList<>();
            this.mario = new Mario();
            loadFromFile(fileName, game);
        } catch (Exception e) {
            throw new GameLoadException(e.getMessage(), e);
        }
    }

    public void loadFromFile(String file, GameWorld world) throws GameLoadException{
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //Leo la primera línea (status del game)
            String firstLine = br.readLine();
            if(firstLine == null) throw new GameLoadException(Messages.INCORRECT_STATUS.formatted(firstLine));
            String[] status = firstLine.trim().split("\\s+"); //Troceo la 1ª línea
            if(status.length < 3 || status.length > 3) throw new GameLoadException(Messages.INCORRECT_STATUS.formatted(firstLine));
            try {
                this.time = Integer.parseInt(status[0]);
                this.points = Integer.parseInt(status[1]);
                this.lives = Integer.parseInt(status[2]);
                if(lives > 3) throw new GameLoadException(Messages.INCORRECT_STATUS.formatted(firstLine));
            } catch (NumberFormatException nfe) {
                throw new GameLoadException(Messages.INCORRECT_STATUS.formatted(firstLine), nfe);
            }

            //Leemos los objetos. *comprobar si uno de ellos es mario*
            String line = br.readLine();
            while(line != null){
                String[] o = line.trim().split("\\s+");
                try {
                    GameObject obj = GameObjectFactory.parse(o, world);
                    if(obj.isMario()){
                        //Cast, pensar cómo evitarlo
                        this.mario = (Mario) obj;
                    }else{
                        gameObjects.add(obj);
                    }

                    if(!world.isInsideBoard(obj.getPosition())) throw new OffBoardException(Messages.OFF_BOARD.formatted(String.join("",line)));

                } catch (ObjectParseException ope) {
                    throw new GameLoadException(Messages.INVALID_CONFIGURATION.formatted(file), ope);
                }catch(OffBoardException obe){
                    throw new GameLoadException(Messages.INVALID_CONFIGURATION.formatted(file), obe);
                }
                line = br.readLine();
            }

        } catch (IOException ie) {
            throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(file), ie);
        }
    }

    public List<GameObject> getNPCObjects(){
        return this.gameObjects;
    }

    public int getRemainingTime(){
        return this.time;
    }
    public int points(){
        return this.points;
    }
    public int numLives(){
        return this.lives;
    }

    public Mario getMario(){
        return this.mario;
    }

}
