package seaWar2.view;

import seaWar2.Game;
import seaWar2.board.LocalBoard;
import seaWar2.board.RemoteBoard;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author joschaseelig
 */
public interface BoardViewConsole {
    char WATER_SYMBOL = '·';
    char SHIP_SYMBOL = '»';
    char SHOT_ON_WATER_SYMBOL = '∷';
    char HIT_SYMBOL = '✖';
    char UNKNOWN_SYMBOL = '?';

    static BoardViewConsole createBoardViewConsole(Game game, OutputStream out) {
        return new BoardViewConsoleImpl(game, out);
    }

    /**
     * Prints the local as well as the remote board to the specified PrintStream.
     *
     * @param local
     * @param remote
     */
    void printBoards(LocalBoard local, RemoteBoard remote);

    void printMessage(String message);
}
