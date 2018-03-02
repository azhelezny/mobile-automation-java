package framework.utils;

public class QaException extends RuntimeException {
    public QaException(String s) {
        super(s);
    }
    public QaException(String message, Exception e){
        super(message,e);
    }
}
