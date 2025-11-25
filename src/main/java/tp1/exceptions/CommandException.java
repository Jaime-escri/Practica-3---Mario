package tp1.exceptions;

public abstract class CommandException extends Exception {

    public CommandException(String s){
        super(s);
    }

    public CommandException(String s, Throwable cause){
        super(s,cause);
    }
}
