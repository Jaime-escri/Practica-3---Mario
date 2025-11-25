package tp1.control;

import tp1.view.GameView;
import tp1.exceptions.CommandException;
import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;

public interface Command {
    public abstract void execute(GameModel model, GameView view) throws CommandException;
    public Command parse(String[] words) throws CommandParseException;
    public String helpText();
}
