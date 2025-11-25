package tp1.exceptions;

public class ActionParseException extends GameModelException{
    public ActionParseException(String s){
        super(s);
    }

    public ActionParseException(String s, Throwable cause){
        super(s,cause);
    }
}
