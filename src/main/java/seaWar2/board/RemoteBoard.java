package seaWar2.board;

import seaWar2.Game;
import seaWar2.StatusException;

import java.io.IOException;

public interface RemoteBoard extends Board {
    static RemoteBoard createRemoteBoard(Game game) {
        return new RemoteBoardImpl(game);
    }

    /**
     * Sets FieldStatus of field at the specified coordinates.
     *
     * @param column
     * @param row
     * @throws OutOfBoardException if the specified coordinates not existing on this board.
     */
    void setFieldStatus(int row, int column, FieldStatus fieldStatus) throws OutOfBoardException;

    /**
     * Returns Array of all FieldStatuses on this board.
     *
     * @return FieldStatus[][] of this board.
     */
    FieldStatus[][] getFieldStatus();

    /**
     * Shoots on the specified coordinates of the opponents board. This actually attacks the opponent.
     *
     * @param row
     * @param column
     * @throws StatusException     if GameStatus is unequal to GameStatus.ACTIVE
     * @throws OutOfBoardException if the specified coordinates not existing on board.
     */
    void shoot(int row, int column) throws StatusException, OutOfBoardException, IOException;
}
