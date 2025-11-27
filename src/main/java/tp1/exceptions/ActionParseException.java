package tp1.exceptions;

public class ActionParseException extends GameParseException{
    public ActionParseException(String s){
        super(s);
    }

    public ActionParseException(String s, Throwable cause){
        super(s,cause);
    }
}
