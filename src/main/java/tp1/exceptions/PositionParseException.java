package tp1.exceptions;

public class PositionParseException extends GameParseException {
    public PositionParseException(String s){
        super(s);
    }

    public PositionParseException(String s, Throwable cause){
        super(s, cause);
    }
}
