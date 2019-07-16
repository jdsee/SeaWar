package seaWar2.board;

import seaWar2.Game;
import seaWar2.StatusException;

import java.util.List;

public interface LocalBoard extends Board {

    static LocalBoard createLocalBoard(Game game) {
        return new LocalBoardImpl(game);
    }

    /**
     * Returns Array of all fields on this board.
     *
     * @return Field[][] of this board.
     */
    Field[][] getFields();

    /**
     * Returns the field at the specified coordinates of this board.
     *
     * @param row
     * @param column
     * @return field at specified coordinates.
     * @throws OutOfBoardException if the specified coordinates not existing on board.
     */
    Field getField(int row, int column) throws OutOfBoardException;

    /**
     * Returns ship of specified length.
     *
     * @param length
     * @return ship
     * @throws ShipNotAvailableException if Ship is already place up or specified length is not available
     * @throws StatusException           if GameStatus is unequal to GameStatus.PREPARING
     */
    Ship getUnsetShip(int length) throws ShipNotAvailableException, StatusException;

    FieldStatus getFieldStatus(int row, int column) throws OutOfBoardException;

    /**
     * Returns the ship at the specified coordinates. Null if no ship is referenced at these coordinates.
     *
     * @param row
     * @param column
     * @return the ship at the specified coordinates.
     * @throws OutOfBoardException if the specified coordinates not existing on board.
     */
    Ship getShip(int row, int column) throws OutOfBoardException;

    /**
     * Sets the specified ship on the specified coordinates on this board.
     * The coordinates refer to the upper left corner of the ship.
     *
     * @param ship
     * @param column
     * @param row
     * @param horizontal alignment of the ship on the board -> {@true} if horizontal
     * @throws OutOfBoardException      if the specified coordinates not existing on board.
     * @throws InvalidPositionException if the specified coordinates already place or illegal.
     * @throws StatusException          if GameStatus is unequal to GameStatus.PREPARING
     */
    void setShip(Ship ship, int row, int column, boolean horizontal)
            throws OutOfBoardException, InvalidPositionException, StatusException, ShipAlreadySetException;

    /**
     * Unsets the ship at the specified coordinates on this board.
     * If no ship is placed at these coordinates, nothing happens.
     *
     * @param column
     * @param row
     * @throws OutOfBoardException if the specified coordinates not existing on this board.
     * @throws StatusException     if GameStatus is unequal to GameStatus.PREPARING
     */
    void unsetShip(int row, int column) throws OutOfBoardException, StatusException;

    /**
     * Checks if this board is isReady to play --> all ships are place.
     *
     * @return {@true} if this board is isReady to play.
     */
    boolean isReady();

    /**
     * Shoots on the given coordinates of this board. this is actually an attack of the enemy.
     *
     * @param column
     * @param row
     * @return result
     * @throws StatusException     if GameStatus is unequal to GameStatus.ACTIVE
     * @throws OutOfBoardException if the specified coordinates not existing on board.
     */
    ShotResult shoot(int row, int column) throws StatusException, OutOfBoardException;

    /**
     * Returns the amount of not sunk ships on this board.
     *
     * @return the amount of not sunk ships on this board.
     */
    int getSpareShips();

    List<Ship[]> getShipFleet();

    /**
     * Checks if game is lost.
     *
     * @return true if all ships are destroyed on this board
     */
    boolean lost();         //--> probably not necessary

    /**
     * Saves this board.
     */
    void save() throws Exception;

}
