package seaWar2.communication;

import seaWar2.Game;
import seaWar2.StatusException;
import seaWar2.board.OutOfBoardException;
import seaWar2.board.ShipNotSetException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SWPEngine extends Runnable {
    String SHOT_CMD = "shoot";
    String RESULT_CMD = "result";
    String GIVEUP_CMD = "giveup";
    String START_CMD = "start";
    String READY_CMD = "ready";
    String CLOSE_CONNECTION_CMD = "close";
    String TALK_CMD = "talk";

    String WATER_STRING = "water";
    String HIT_STRING = "hit";
    String SUNK_STRING = "sunk";
    String LOST_STRING = "lost";

    static final int RESPONSE_WAIT_TIME = 10_000;

    static SWPEngine createSWPEngine(Game game) {
        return new SWPEngineImpl(game);
    }

    void handleConnection(InputStream in, OutputStream out) throws IOException;

    void sendCloseConnectionCmd() throws IOException;

    void handleShotCmd() throws IOException, StatusException, OutOfBoardException, ShipNotSetException;

    void sendShotCmd(int row, int column) throws IOException, OutOfBoardException, StatusException;

    void sendReadyCmd() throws IOException, StatusException;

    boolean isConnected();

    void sendMessage(String message) throws IOException;
}
