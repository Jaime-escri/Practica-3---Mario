package tp1.exceptions;

public class ObjectParseException extends GameModelException{
    public ObjectParseException(String s){
        super(s);
    }

    public ObjectParseException(String s, Throwable cause){
        super(s,cause);
    }
}
