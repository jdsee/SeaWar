package seaWar2.board;

public class ShipAlreadySetException extends ShipException {

    public ShipAlreadySetException() {
        super();
    }

    public ShipAlreadySetException(String message) {
        super(message);
    }

    public ShipAlreadySetException(String message, Exception e) {
        super(message, e);
    }

}
