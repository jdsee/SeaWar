package seaWar2.board;

public class ShipImpl implements Ship {

    private Coordinate[] coordinates;
    private int hitCount = 0;
    private final int length;

    public ShipImpl(int length) {
        this.length = length;
    }

    /**
     * Shoots one element of the ship.
     */
    @Override
    public ShotResult shoot() {
        hitCount++;
        return hitCount < this.getLength() ? ShotResult.HIT : ShotResult.SUNK;
    }

    /**
     * Returns {@true} if this ship is placed on the board, {@false} if unset.
     *
     * @return {@true} if this ship is placed on the board, {@false} if unset.
     */
    @Override
    public boolean isPlaced() {
        return coordinates != null;
    }

    /**
     * Returns ShipStatus of this ship.
     *
     * @return ShipStatus of this ship.
     */
    @Override
    public ShipStatus getStatus() {
        return hitCount == this.getLength() ? ShipStatus.INTACT
                : hitCount > 0 ? ShipStatus.DAMAGED : ShipStatus.SUNK;
    }

    /**
     * Returns the coordinates of this ship.
     *
     * @return the coordinates of this ship.
     * @throws ShipNotSetException if no coordinates are place for this ship.
     */
    @Override
    public Coordinate[] getCoordinates() throws ShipNotSetException {
        if (coordinates == null)
            throw new ShipNotSetException();
        else return coordinates;
    }

    @Override
    public void place(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void unset() {
        coordinates = null;
    }

    /**
     * Returns length of this ship.
     *
     * @return length of this ship.
     */
    @Override
    public int getLength() {
        return length;
    }
}
