package seaWar2.board;

public class ShipException extends BoardException {

    public ShipException() {
        super();
    }

    public ShipException(String message) {
        super(message);
    }

    public ShipException(String message, Exception e) {
        super(message, e);
    }

}
