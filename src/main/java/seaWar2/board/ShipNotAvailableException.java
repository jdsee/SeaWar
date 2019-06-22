package seaWar2.board;

public class ShipNotAvailableException extends ShipException {

    public ShipNotAvailableException() {
        super("Ships of this length are not available.");
    }

    public ShipNotAvailableException(String message) {
        super(message);
    }

    public ShipNotAvailableException(String message, Exception e) {
        super(message, e);
    }

}
