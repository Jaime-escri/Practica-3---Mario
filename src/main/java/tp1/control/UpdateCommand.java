package tp1.control;

import tp1.view.GameView;
import tp1.view.Messages;
import tp1.logic.GameModel;

public class UpdateCommand extends NoParamsCommand{
    private static final String NAME = Messages.COMMAND_UPDATE_NAME;
    private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
    private static final String HELP = Messages.COMMAND_UPDATE_HELP;

    public UpdateCommand(){
        super(NAME,DETAILS,SHORTCUT,HELP);
    }

    public void execute(GameModel model, GameView view){
        model.update();
        view.showGame();
    }
}
