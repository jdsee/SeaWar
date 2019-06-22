package seaWar2.board;

public class CoordinateImpl implements Coordinate {

    private final int column;
    private final int row;

    public CoordinateImpl(int row, int column) {
        this.column = column;
        this.row = row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getRow() {
        return row;
    }
}
