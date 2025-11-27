package tp1.control;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand {
    private static final String NAME = Messages.COMMAND_SAVE_NAME;
    private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
    private static final String HELP = Messages.COMMAND_SAVE_HELP;
    private String file;

    public SaveCommand(){
        super(NAME,SHORTCUT,DETAILS,HELP);
    }

    public SaveCommand(String fileName){
        super(NAME,SHORTCUT,DETAILS,HELP);
        this.file = fileName;
    }

    public Command parse(String[] words) throws CommandParseException{
        if(!matchCommand(words[0])) return null;
        if(words.length < 2)throw new CommandParseException("Missing arguments");
        return new SaveCommand(words[1]);
    }

    public void execute(GameModel model, GameView view) throws CommandExecuteException{
        try {
            model.save(this.file);
        } catch (GameModelException gme) {
            throw new CommandExecuteException(gme.getMessage(), gme);
        }
        view.showMessage(Messages.SAVE_SUCCESFULL_STRING.formatted(this.file));
    }
}
