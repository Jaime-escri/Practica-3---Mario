package tp1.control;

import tp1.view.GameView;
import tp1.logic.GameModel;
import tp1.view.Messages;

public class ExitCommand extends NoParamsCommand {
    public static final String NAME = Messages.COMMAND_EXIT_NAME;
	public static final String SHORTCUT = Messages.COMMAND_EXIT_SHORTCUT;
	public static final String DETAILS = Messages.COMMAND_EXIT_DETAILS ;
	public static final String HELP = Messages.COMMAND_EXIT_HELP;

    public ExitCommand() {
        super(NAME, DETAILS, SHORTCUT, HELP);
    }

    @Override
    public void execute(GameModel model, GameView view) {
        model.setFinish();
    }

    public Command parse(String[] words){
        if(!matchCommand(words[0])) return null;
        return new ExitCommand();
    }
}