package seaWar2.board;

/**
 * @author joschaseelig
 */
public class FieldImpl implements Field {

    private final LocalBoard board;
    private Ship ship;
    private boolean wasShot;
    private boolean shipSunk;

    public FieldImpl(LocalBoard board) {
        this.board = board;
        this.ship = null;
        this.wasShot = false;
        this.shipSunk = false;
    }

    @Override
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Ship getShip() {
        return ship;
    }

    @Override
    public FieldStatus getFieldStatus() {
        if (this.shipSunk) {
            return FieldStatus.SUNK_SHIP;
        }
            if (ship == null)
                return !wasShot ? FieldStatus.WATER : FieldStatus.SHOT_ON_WATER;
            else
                return !wasShot ? FieldStatus.SHIP : FieldStatus.HIT;
    }

    @Override
    public ShotResult shoot() {
        if (wasShot || ship == null) {
            wasShot = true;
            return ShotResult.WATER;
        }
        wasShot = true;
        ShotResult res = ship.shoot();
        if (res == ShotResult.SUNK) {
            try {
                setFieldsSunk(this.ship.getCoordinates());
            } catch (ShipNotSetException e) {
                //Is already checked at this point
            }
        }
        return res;
    }

    private void setFieldsSunk(Coordinate[] coordinates) {
        try {
            int row, column;
            for (Coordinate coordinate : coordinates) {
                row = coordinate.getRow();
                column = coordinate.getColumn();
                this.board.getField(row, column).setShipSunk();
            }
        } catch (OutOfBoardException e) {
            //Is already checked at this point
        }
    }

    @Override
    public void setShipSunk() {
        this.shipSunk = true;
    }
}
