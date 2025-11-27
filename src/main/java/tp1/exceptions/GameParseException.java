package tp1.exceptions;

public class GameParseException extends GameModelException {
    public GameParseException(String s){
        super(s);
    }

    public GameParseException(String s, Throwable cause){
        super(s,cause);
    }
}
