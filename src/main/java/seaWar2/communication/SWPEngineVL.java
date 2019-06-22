
package seaWar2.communication;

import seaWar2.Game;
import seaWar2.StatusException;
import seaWar2.board.OutOfBoardException;
import seaWar2.board.ShotResult;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface SWPEngineVL extends Runnable {

    static final String SHOT_CMD = "shoot";
    static final String RESULT_CMD = "result";
    static final String GIVEUP_CMD = "giveup";
    static final String START_CMD = "start";
    static final String READY_REQUEST_CMD = "ready";

    static final String WATER_STRING = "water";
    static final String HIT_STRING = "hit";
    static final String SUNK_STRING = "sunk";
    static final String LOST_STRING = "lost";

    static SWPEngineVL createSWPEngineVL(Game game) {
        return new SWPEngineVLImpl(game);
    }

    void handleConnection(DataInputStream dis, DataOutputStream dos);

    void closeConnection() throws IOException;

    void readShotParameter() throws IOException, StatusException, OutOfBoardException;

    ShotResult readResultParameter() throws IOException;

    void readStartParameter() throws IOException;

    void writeStartPDU(boolean callerStarts) throws IOException;

    boolean writeReadyRequestPDU() throws IOException;

    void writeReadyRequestReactionPDU() throws IOException;

    void writeShotPDU(int row, int column) throws IOException;

    void writeResultPDU(ShotResult res) throws IOException;

    void writeGiveUpPDU() throws IOException;
}