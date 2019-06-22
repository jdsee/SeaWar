package seaWar2.board;

public class InvalidPositionException extends BoardException {

    public InvalidPositionException() {
        super();
    }

    public InvalidPositionException(String message) {
        super(message);
    }

    public InvalidPositionException(String message, Exception e) {
        super(message, e);
    }
}
