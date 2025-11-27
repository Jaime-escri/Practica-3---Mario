package tp1.control;



import tp1.exceptions.ActionParseException;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;


public class ActionCommand extends AbstractCommand{
    private static final String NAME = Messages.COMMAND_ACTION_NAME;
    private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
    private static final String HELP = Messages.COMMAND_ACTION_HELP;
    private ActionList listActions;

    public ActionCommand(){
        super(NAME,SHORTCUT,DETAILS,HELP);
    }

    private ActionCommand(ActionList list){
        super(NAME,SHORTCUT,DETAILS,HELP);
       this.listActions = list;
    }

    public Command parse(String[] words) throws CommandParseException{
        if(!matchCommand(words[0])){
            return null;
        }
        ActionList list = new ActionList();
        try {
            list.addStringAction(words);   
        } catch (ActionParseException ape) {
            throw new CommandParseException(Messages.UNKNOWN_ACTION.formatted(words[1]), ape);
        }
        return new ActionCommand(list);
       
    }

    public void execute(GameModel model, GameView view){
        if(listActions != null){
            for(Action a : listActions.getActions()){
                model.addAction(a);
            }
        }
        model.update();
        view.showGame();
    }

}
