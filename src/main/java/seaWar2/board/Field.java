package seaWar2.board;

/**
 * This interface describes objects, that extend a ship-object and a FieldStatus.
 * Use a two dimensional array of these fields to receive a board.
 *
 * @author joschaseelig
 */
public interface Field {

    static Field createField() {
        return new FieldImpl();
    }

    /**
     * Sets the specified ship on this field.
     * If there already exists a ship, the specified ships replaces that ship.
     * Null is also a valid argument -> this is needed for unset the ship on this field.
     *
     * @param ship
     */
    void setShip(Ship ship);

    /**
     * Returns the ship on this field, if existing. Else return value is null.
     *
     * @return the ship on this field, if existing.
     */
    Ship getShip();

    /**
     * Returns the FieldStatus of this field.
     *
     * @return the FieldStatus of this field.
     */
    FieldStatus getFieldStatus();

    /**
     * Shoots at this field. If this field extends a ship, the ship will be fired.
     * @return ShotResult of this shot.
     */
    ShotResult shoot();
}
