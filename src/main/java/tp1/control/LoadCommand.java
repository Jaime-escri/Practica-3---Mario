package tp1.control;


import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends AbstractCommand {
    private static final String NAME = Messages.COMMAND_LOAD_NAME;
    private static final String SHORTCUT = Messages.COMMAND_LOAD_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_LOAD_DETAILS;
    private static final String HELP = Messages.COMMAND_LOAD_HELP;
    String fileToOpen;

    public LoadCommand(){
        super(NAME,SHORTCUT,DETAILS,HELP);
    }

    public LoadCommand(String file){
        super(NAME,SHORTCUT,DETAILS,HELP);
        this.fileToOpen = file;
    }

    public Command parse(String[] words) throws CommandParseException{
        if(!matchCommand(words[0])) return null;
        if(words.length <2 ) throw new CommandParseException("Missing arguments for load");
        return new LoadCommand(words[1]);
    }

    public void execute(GameModel model, GameView view) throws CommandExecuteException{
        try {
            model.load(this.fileToOpen);
            view.showGame();
        } catch (GameLoadException gle) {
            throw new CommandExecuteException(Messages.ERROR_CANNOT_LOAD.formatted(fileToOpen), gle);
        }
    }
}
