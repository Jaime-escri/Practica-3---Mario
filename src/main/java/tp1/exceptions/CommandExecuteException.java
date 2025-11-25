package tp1.exceptions;

public class CommandExecuteException extends CommandException {
    public CommandExecuteException(String s){
        super(s);
    }

    public CommandExecuteException(String s, Throwable cause){
        super(s,cause);
    }
}
