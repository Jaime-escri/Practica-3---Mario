package tp1.control;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.GameModelException;
import tp1.logic.ActionList;
import tp1.logic.GameModel;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.view.Messages;
import tp1.view.GameView;

public class AddObjectCommand extends AbstractCommand {
    private static final String NAME = Messages.COMMAND_OBJECT_NAME;
    private static final String SHORTCUT = Messages.COMMAND_OBJECT_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_OBJECT_DETAILS;
    private static final String HELP = Messages.COMMAND_OBJECT_HELP;
    private String[] objects;
    
    
    public AddObjectCommand(){
        super(NAME,SHORTCUT,DETAILS,HELP);
    }

    public AddObjectCommand(String[] words){
        super(NAME,SHORTCUT,DETAILS,HELP);
        this.objects = words;
    }

    public Command parse(String[] words){
        if(matchCommand(words[0])){
            String[] objects = words;
            return new AddObjectCommand(objects);
        }
        return null;
    }

    public void execute(GameModel model, GameView view) throws CommandExecuteException{
        GameWorld world = (GameWorld) model;
        try {
            GameObject gameobject = GameObjectFactory.parse(this.objects, world);
            world.addObject(gameobject);
        } catch (GameModelException gme) {
            throw new CommandExecuteException("Imposible to execute the command", gme);
        }
       
        
        
        view.showGame();
    }

     
}
