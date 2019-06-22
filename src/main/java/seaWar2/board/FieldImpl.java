package seaWar2.board;

/**
 * @author joschaseelig
 */
public class FieldImpl implements Field {

    private Ship ship = null;
    private boolean wasShot = false;

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
        return ship.shoot();
    }
}
