package tp1.control;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;

public abstract class NoParamsCommand extends AbstractCommand{
    public NoParamsCommand(String name, String details, String shortcut, String help){
        super(name, details, shortcut, help);
    }

    public Command parse(String[] commandWords) throws CommandParseException {
 	    if (commandWords.length > 1 && matchCommand(commandWords[0])){
            throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
        }
 	    Command cmd = null;
 	    if (commandWords.length == 1 && matchCommand(commandWords[0])) cmd = this;
 	    return cmd;
    }
   

}
