package seaWar2.view;

import seaWar2.Game;
import seaWar2.StatusException;
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
    char HIT_SHIP_SYMBOL = '✖';
    char UNKNOWN_SYMBOL = '?';

    String ANSI_RESET = "\u001B[0m";

    String ANSI_HIGH_INTENSITY = "\u001B[1m";
    String ANSI_LOW_INTENSITY = "\u001B[2m";

    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_MAGENTA = "\u001B[35m";
    String ANSI_BRIGHT_CYAN = "\u001B[96m";
    String ANSI_CYAN = "\u001B[36m";
    String ANSI_WHITE = "\u001B[37m";

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

    void printPreparationCommands() throws StatusException;

    void printPlayCommands() throws StatusException;

    /**
     * Prints a message to this views outputstream.
     *
     * @param message Message to print.
     */
    void printMessage(String message);

    /**
     * Prints a prompt to indicate that the user is allowed to enter commands.
     */
    void printPrompt();

    void printHeader();

    void printShipGraphik();
}
