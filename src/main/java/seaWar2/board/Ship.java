package seaWar2.board;

public interface Ship {

    static Ship createShip(int length) {
        return new ShipImpl(length);
    }

    /**
     * Shoots one element of the ship.
     *
     * @return ShotResult
     */
    ShotResult shoot();

    /**
     * Returns {@true} if this ship is place on the board / {@false} if unset.
     *
     * @return {@true} if this ship is place on the board / {@false} if unset.
     */
    boolean isPlaced();

    /**
     * Returns ShipStatus of this ship.
     *
     * @return ShipStatus of this ship.
     */
    ShipStatus getStatus();

    /**
     * Returns the coordinates of this ship.
     *
     * @return the coordinates of this ship.
     * @throws ShipNotSetException if no coordinates are place for this ship.
     */
    Coordinate[] getCoordinates() throws ShipNotSetException;

    /**
     * Sets the coordinates of this ship.
     * Caller of this method must check if the coordinates are valid.
     * The Caller must also check that coordinates match to the references of this ship on the board.
     *
     * @param coordinates
     */
    void place(Coordinate[] coordinates);

    /**
     * Unsets the ship. That means, the coordinates are getting set to null.
     * The calling method have to ensure by itself, that the coordinates on the board are unset.
     * Nothing happens, if the coordinates are not set yet.
     */
    void unset();

    /**
     * Returns length of this ship.
     *
     * @return length of this ship.
     */
    int getLength();
}
