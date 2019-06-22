package seaWar2.board;

public interface Coordinate {

    static Coordinate createCoordinate(int row, int column){
        return new CoordinateImpl(row, column);
    }

    int getColumn();

    int getRow();

}
