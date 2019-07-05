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
    private int spareShips;

    private final Ship quintuple;
    private final Ship[] quadrouples;
    private final Ship[] triples;
    private final Ship[] doubles;

    public LocalBoardImpl(Game game) {
        this.game = game;
        this.spareShips = 10;
        for (int i = 0; i <= MAX_ROW_INDEX; i++)
            for (int j = 0; j <= MAX_COLUMN_INDEX; j++)
                this.board[i][j] = Field.createField(this);

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
     * @see LocalBoard
     */
    @Override
    public Field[][] getFields() {
        return board;
    }

    /**
     * @see LocalBoard
     */
    @Override
    public Field getField(int row, int column) throws OutOfBoardException {
        this.checkCoordinates(row, column);
        return this.board[row][column];
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

    @Override
    public FieldStatus getFieldStatus(int row, int column) throws OutOfBoardException {

        if (column < MIN_COLUMN_INDEX || row < MIN_ROW_INDEX
                || column > MAX_COLUMN_INDEX || row > MAX_ROW_INDEX)
            throw new OutOfBoardException();

        return board[row][column].getFieldStatus();
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

        this.checkPositionValidity(row, column, ship.getLength(), horizontal);

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
     * Checks if the position for the specified coordinates and ship length is valid.
     * That means, that no other ships are in direct contact to the specified position.
     * Nothing happens if the position is valid.
     *
     * @throws InvalidPositionException if the specified position is invalid.
     */
    private void checkPositionValidity(int row, int column, int shipLength, boolean horizontal)
            throws InvalidPositionException {
        int outerLoop = horizontal ? row : column;
        int innerLoop = horizontal ? column : row;
        int enlarge;
        for (int i = outerLoop - 1; i < outerLoop + 2; i++) {
            /*
             * The outer loop runs three times, because all lines in touch with the ship have to be checked.
             * To ensure that there are no ships at the tips, the enlarge variable enlarges the checked index
             * as well at the top as at the end of the specified position just at the line the ship actually
             * is meant to be placed.
             */
            enlarge = i == outerLoop ? 1 : 0;
            if (i >= MIN_COLUMN_INDEX && i <= MAX_COLUMN_INDEX) {
                for (int j = innerLoop - enlarge; j - innerLoop < shipLength + enlarge; j++) {
                    if (j >= 0
                            && j < 10
                            && (horizontal && board[i][j].getShip() != null
                            || !horizontal && board[j][i].getShip() != null))
                        throw new InvalidPositionException();
                }
            }
        }
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

        if (result == ShotResult.SUNK){
            this.spareShips--;
        }

        return spareShips > 0 ? result : ShotResult.LOST;
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
    public int getSpareShips() {
        return spareShips;
    }

    /**
     * @see LocalBoard
     */
    @Override
    public boolean lost() {
        return spareShips == 0;
    }

    /**
     * @see LocalBoard
     */
    @Override
    public void save() throws Exception {

    }
}
