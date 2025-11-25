package tp1.exceptions;

public class CommandParseException extends CommandException {

    public CommandParseException(String s){
        super(s);
    }

    public CommandParseException(String s, Throwable cause){
        super(s,cause);
    }
}
