package tp1.exceptions;

public class ObjectParseException extends GameParseException{
    public ObjectParseException(String s){
        super(s);
    }

    public ObjectParseException(String s, Throwable cause){
        super(s,cause);
    }
}
