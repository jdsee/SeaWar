package seaWar2.board;

import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;

/**
 * @author joschaseelig
 * @see LocalBoard
 */
public class LocalBoardImpl implements LocalBoard {
    private final Game game;

    private final Field[][] board = new Field[10][10]; //Board must be adressed like this: board[row][column].
    private int pendingShips;

    private final Ship quintuple;
    private final Ship[] quadrouples;
    private final Ship[] triples;
    private final Ship[] doubles;

    public LocalBoardImpl(Game game) {
        this.game = game;
        this.pendingShips = 10;
        for (int i = 0; i <= MAX_ROW_INDEX; i++)
            for (int j = 0; j <= MAX_COLUMN_INDEX; j++)
                this.board[i][j] = Field.createField();

        this.quintuple = Ship.createShip(5);
        this.quadrouples = new Ship[]{
                Ship.createShip(4),
                Ship.createShip(4)
        };
        this.triples = new Ship[]{
                Ship.createShip(3),
                Ship.createShip(3),
                Ship.createShip(3),
        };
        this.doubles = new Ship[]{
                Ship.createShip(2),
                Ship.createShip(2),
                Ship.createShip(2),
                Ship.createShip(2),
        };
    }

    /**
     * Sets FieldStatus of field at the specified coordinates.
     *
     * @param row
     * @param column
     * @param fieldStatus
     * @throws OutOfBoardException if the specified coordinates not existing on this board.
     */
    @Override
    public void setFieldStatus(int row, int column, FieldStatus fieldStatus) throws OutOfBoardException {
        checkCoordinates(row, column);
    }

    /**
     * @see LocalBoard
     */
    @Override
    public Field[][] getFields() {
        return board;
    }

    /**
     * Gives FieldStatus of the field at the specified coordinates.
     *
     * @param column
     * @param row
     * @return FieldStatus of the field at specified coordinates.
     * @throws OutOfBoardException if the specified coordinates not existing on this board.
     */
    @Override
    public FieldStatus getFieldStatus(int row, int column) throws OutOfBoardException {
        checkCoordinates(row, column);
        return board[row][column].getFieldStatus();
    }

    /**
     * Returns ship of specified length.
     *
     * @param length
     * @return ship
     * @throws ShipNotAvailableException if Ship is already place up or specified length is not available
     * @throws StatusException           if GameStatus is unequal to GameStatus.PREPARING
     */
    @Override
    public Ship getUnsetShip(int length) throws ShipNotAvailableException, StatusException {
        this.checkStatus(GameStatus.PREPARING);

        switch (length) {
            case 2:
                for (Ship ship : this.doubles)
                    if (!ship.isPlaced())
                        return ship;
                break;
            case 3:
                for (Ship ship : this.triples)
                    if (!ship.isPlaced())
                        return ship;
                break;
            case 4:
                for (Ship ship : this.quadrouples)
                    if (!ship.isPlaced())
                        return ship;
                break;
            case 5:
                if (!this.quintuple.isPlaced())
                    return this.quintuple;
                break;
            default:
                throw new ShipNotAvailableException();
        }
        throw new ShipNotAvailableException("All ships of this type are already placed.");
    }

    /**
     * Returns the ship at the specified coordinates.
     *
     * @param row
     * @param column
     * @return the ship at the specified coordinates.
     * @throws OutOfBoardException if the specified coordinates not existing on board.
     */
    @Override
    public Ship getShip(int row, int column) throws OutOfBoardException {
        checkCoordinates(row, column);

        return board[row][column].getShip();
    }

    /**
     * Sets the specified ship on the specified coordinates on this board.
     * The coordinates refer to the upper left corner of the ship.
     *
     * @param ship
     * @param column
     * @param row
     * @param horizontal alignment of the ship on the board -> true if horizontal
     * @throws OutOfBoardException      if the specified coordinates not existing on board.
     * @throws InvalidPositionException if the specified coordinates already place or illegal.
     * @throws StatusException          if GameStatus is unequal to GameStatus.PREPARING
     */
    @Override
    public void setShip(Ship ship, int row, int column, boolean horizontal)
            throws OutOfBoardException, InvalidPositionException, StatusException, ShipAlreadySetException {
        this.checkStatus(GameStatus.PREPARING);
        this.checkCoordinates(row, column);

        if ((horizontal && column - 1 + ship.getLength() > MAX_COLUMN_INDEX)
                || (!horizontal && row - 1 + ship.getLength() > MAX_ROW_INDEX))
            throw new OutOfBoardException();

        if (ship.isPlaced())
            throw new ShipAlreadySetException();

        int first = horizontal ? row : column;
        int next = horizontal ? column : row;
        for (int i = first - 1; i < first + 2; i++)
            if (i >= 0 && i < 10)
                for (int j = next - 1; j - next < ship.getLength() + 1; j++)
                    if (j >= 0 && j < 10
                            && (horizontal && board[i][j].getShip() != null
                            || !horizontal && board[j][i].getShip() != null))
                        throw new InvalidPositionException();

        Coordinate[] coordinates = new Coordinate[ship.getLength()];
        if (horizontal)
            for (int i = column; i - column < ship.getLength(); i++) {
                board[row][i].setShip(ship);
                coordinates[i - column] = Coordinate.createCoordinate(row, i);
            }
        else
            for (int i = row; i - row < ship.getLength(); i++) {
                board[i][column].setShip(ship);
                coordinates[i - row] = Coordinate.createCoordinate(i, column);
            }
        ship.place(coordinates);
    }

    /**
     * Unsets the ship at the specified coordinates on this board.
     * If no ship is placed at these coordinates, nothing happens.
     *
     * @param column
     * @param row
     * @throws OutOfBoardException if the specified coordinates not existing on this board.
     * @throws StatusException     if GameStatus is unequal to GameStatus.PREPARING
     */
    @Override
    public void unsetShip(int row, int column) throws OutOfBoardException, StatusException {
        this.checkStatus(GameStatus.PREPARING);

        if (column < MIN_COLUMN_INDEX || row < MIN_ROW_INDEX
                || column > MAX_COLUMN_INDEX || row > MAX_ROW_INDEX)
            throw new OutOfBoardException();

        Ship ship = this.getShip(row, column);

        if (ship != null) {
            try {
                Coordinate[] coordinates = ship.getCoordinates();
                for (Coordinate coordinate : coordinates)
                    board[coordinate.getRow()][coordinate.getColumn()].setShip(null);
            } catch (ShipNotSetException e) {
                e.printStackTrace();
            } finally {
                ship.unset();
            }
        }
    }

    /**
     * @return {@code true} if this board is isReady to play.
     * @see LocalBoard
     */
    @Override
    public boolean isReady() {
        for (Ship ship : doubles)
            if (!ship.isPlaced()) return false;
        for (Ship ship : triples)
            if (!ship.isPlaced()) return false;
        for (Ship ship : quadrouples)
            if (!ship.isPlaced()) return false;
        return quintuple.isPlaced();
    }

    /**
     * Shoots on the specified coordinates of this board. This is actually an attack of the enemy.
     *
     * @param column
     * @param row
     * @return result
     * @throws StatusException if GameStatus is unequal to GameStatus.PASSIVE.
     * @see LocalBoard
     */
    @Override
    public ShotResult shoot(int row, int column) throws StatusException, OutOfBoardException {
        this.checkStatus(GameStatus.PASSIVE);
        this.checkCoordinates(row, column);

        ShotResult result = board[row][column].shoot();

        return pendingShips > 0 ? result : ShotResult.LOST;
    }

    private void checkCoordinates(int row, int column) throws OutOfBoardException {
        if (column < MIN_COLUMN_INDEX || row < MIN_ROW_INDEX
                || column > MAX_COLUMN_INDEX || row > MAX_ROW_INDEX) {
            throw new OutOfBoardException();
        }
    }

    private void checkStatus(GameStatus required) throws StatusException {
        if (this.game.getStatus() != required) {
            throw new StatusException();
        }
    }

    /**
     * @see LocalBoard
     */
    @Override
    public int getPendingShips() {
        return pendingShips;
    }

    /**
     * @see LocalBoard
     */
    @Override
    public boolean lost() {
        return pendingShips == 0;
    }

    /**
     * @see LocalBoard
     */
    @Override
    public void save() throws Exception {

    }
}
