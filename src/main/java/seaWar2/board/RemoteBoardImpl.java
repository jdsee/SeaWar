package seaWar2.board;

import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;
import seaWar2.communication.SWPEngine;

import java.io.IOException;

public class RemoteBoardImpl implements RemoteBoard {

    private final Game game;
    private final FieldStatus[][] board;
    private final SWPEngine swpEngine;

    RemoteBoardImpl(Game game) {
        this.game = game;
        this.swpEngine = game.getSWPEngine();
        this.board = new FieldStatus[10][10];
        for (int i = 0; i <= MAX_ROW_INDEX; i++)
            for (int j = 0; j <= MAX_COLUMN_INDEX; j++)
                this.board[i][j] = FieldStatus.UNKOWN;
    }

    @Override
    public void setFieldStatus(int row, int column, FieldStatus fieldStatus) throws OutOfBoardException {
        checkCoordinates(row, column);
        this.board[row][column] = fieldStatus;
    }

    @Override
    public FieldStatus[][] getFieldStatus() {
        return this.board;
    }

    @Override
    public void shoot(int row, int column) throws OutOfBoardException, IOException, StatusException {
        this.checkStatus();
        this.checkCoordinates(row, column);
        this.swpEngine.sendShotCmd(row, column);
    }

    void checkCoordinates(int row, int column) throws OutOfBoardException {
        if (column < MIN_COLUMN_INDEX || row < MIN_ROW_INDEX
                || column > MAX_COLUMN_INDEX || row > MAX_ROW_INDEX)
            throw new OutOfBoardException();
    }

    private void checkStatus() throws StatusException {
        if (this.game.getStatus() != GameStatus.ACTIVE) {
            throw new StatusException();
        }
    }
}
