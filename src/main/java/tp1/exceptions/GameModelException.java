package tp1.exceptions;

public class GameModelException extends Exception {
    public GameModelException(String s){
        super(s);
    }

    public GameModelException(String s, Throwable cause){
        super(s,cause);
    }
}
