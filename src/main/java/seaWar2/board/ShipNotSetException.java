package seaWar2.board;

public class ShipNotSetException extends ShipException {

    public ShipNotSetException() {
        super();
    }

    public ShipNotSetException(String message) {
        super(message);
    }

    public ShipNotSetException(String message, Exception e) {
        super(message, e);
    }
}
