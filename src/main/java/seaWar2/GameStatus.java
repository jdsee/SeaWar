package seaWar2;

/**
 * All possible statuses a board can have.
 */
public enum GameStatus {
    PREPARING("PREPARING"), READY("READY"), ACTIVE("ACTIVE"), PASSIVE("PASSIVE"), GAME_OVER("GAME_OVER");

    private final String printVal;

    GameStatus(String printVal) {
        this.printVal = printVal;
    }

    @Override
    public String toString() {
        return printVal;
    }
}
