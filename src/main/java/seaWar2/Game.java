package seaWar2;

import seaWar2.board.LocalBoard;
import seaWar2.board.RemoteBoard;
import seaWar2.communication.SWPEngine;
import seaWar2.view.BoardCommands;
import seaWar2.view.BoardViewConsole;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author joschaseelig
 */
public interface Game {

    static Game createGame() {
        return new GameImpl();
    }

    static Game createGame(InputStream in, OutputStream out) {
        return new GameImpl(in, out);
    }

    /**
     * Creates a new game and all the required sub-components and runs it subsequently.
     */
    void startGame();

    LocalBoard getLocalBoard();

    RemoteBoard getRemoteBoard();

    SWPEngine getSWPEngine();

    BoardViewConsole getBoardViewConsole();

    BoardCommands getBoardCommands();

    void setStatus(GameStatus status);

    GameStatus getStatus();
}
