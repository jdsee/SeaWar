package seaWar2;

/**
 * Superclass of all exceptions thrown in that GameImpl.
 */
public class SeaWarException extends Exception {

    public SeaWarException() {
        super();
    }

    public SeaWarException(String message) {
        super(message);
    }

    public SeaWarException(String message, Exception e) {
        super(message, e);
    }

}
