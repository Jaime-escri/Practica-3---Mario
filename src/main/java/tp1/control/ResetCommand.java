package tp1.control;

import tp1.view.Messages;
import tp1.view.GameView;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;

public class ResetCommand extends AbstractCommand {
    private static final String NAME = Messages.COMMAND_RESET_NAME;
    private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
    private static final String HELP = Messages.COMMAND_RESET_HELP;
    private int level;

    public ResetCommand(){
        super(NAME,SHORTCUT,DETAILS,HELP);
        this.level = 1;
    }

    private ResetCommand(int level){
        super(NAME,SHORTCUT,DETAILS,HELP);
        this.level = level;
    }

    public void execute(GameModel model, GameView view){
        model.setLevel(level);
        model.reset(level);
        view.showGame();
    }

    public Command parse(String[] words) throws CommandParseException{
        try{
            if(words.length > 1 && matchCommand(words[0])){
                if(words.length >= 1){
                    if(!(words[1].equals("1") || words[1].equals("-1") || words[1].equals("0")))return null;
                    level = Integer.parseInt(words[1]);
                }
                return new ResetCommand(level);
            }
        }  catch (NumberFormatException nfe) {
 	        throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(words[1]), nfe);
        }

        return null;
    }
}
