package seaWar2.board;

public class EmptyFieldException extends BoardException {

    public EmptyFieldException() {
        super();
    }

    public EmptyFieldException(String message) {
        super(message);
    }

    public EmptyFieldException(String message, Exception e) {
        super(message, e);
    }
}
