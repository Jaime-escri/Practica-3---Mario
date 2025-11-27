package tp1.exceptions;

public class GameLoadException extends GameModelException {
    public GameLoadException(String s){
        super(s);
    }
    public GameLoadException(String s, Throwable cause){
        super(s,cause);
    }
}
