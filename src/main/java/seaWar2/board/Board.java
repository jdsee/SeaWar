package seaWar2.board;

public interface Board {

    public final static int MIN_COLUMN_INDEX = 0;
    public final static int MIN_ROW_INDEX = 0;
    public final static int MAX_COLUMN_INDEX = 9;
    public final static int MAX_ROW_INDEX = 9;

    public final static char MIN_ROW_CHAR = 'A';
    public final static char MAX_ROW_CHAR = 'J';

    public final static int MAX_SHIP_LENGTH = 5;
    public final static int MIN_SHIP_LENGTH = 2;

    public static final String HORIZONTAL_STRING = "h";
    public static final String VERTICAL_STRING = "v" ;

    /**
     * Sets FieldStatus of field at the specified coordinates.
     *
     * @param column
     * @param row
     * @throws OutOfBoardException if the specified coordinates not existing on this board.
     */
    void setFieldStatus(int row, int column, FieldStatus fieldStatus) throws  OutOfBoardException;
}
