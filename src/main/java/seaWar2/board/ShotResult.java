package seaWar2.board;

public enum ShotResult {

    /**
     * hit Water
     */
    WATER,

    /**
     * hit ship, not destroyed
     */
    HIT,

    /**
     * SUNK
     */
    SUNK,

    /**
     * hit ship, destroyed and lost game
     */
    LOST

}
