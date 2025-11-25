package tp1.exceptions;

public class OffBoardException extends GameModelException{
    public OffBoardException(String s){
        super(s);
    }
    public OffBoardException(String s, Throwable cause){
        super(s,cause);
    }
}
