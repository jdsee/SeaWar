package seaWar2;

public class StatusException extends SeaWarException {

    public StatusException() {
        super("This operation is not allowed in this status!");
    }

    public StatusException(String message) {
        super(message);
    }

    public StatusException(String message, Exception e) {
        super(message, e);
    }
}
