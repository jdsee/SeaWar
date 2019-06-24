package seaWar2.view;

import seaWar2.Game;

import java.io.InputStream;
import java.io.OutputStream;

public interface BoardCommands {

    String PRINT_BOARDS = "print";
    String SHOOT = "shoot";
    String SET_SHIP = "set";
    String REMOVE_SHIP = "remove";
    String START_GAME = "start";
    String GIVE_UP = "surrender";
    String TALK = ":";
    String GET_STATUS = "status";
    String SAVE = "save";
    String RESTORE = "load";
    String CONNECT = "connect";
    String FILL = "fill";
    String EXIT = "exit";

    String OPEN_CMD_NR = "1";
    String OPEN_CMD = "open";
    String JOIN_CMD_NR = "2";
    String JOIN_CMD = "join";

    String CHANNEL_NAME_SERVER =  "server";
    String CHANNEL_NAME_CLIENT = "client";

    static BoardCommandsImpl createBoardCommands(Game game) {
        return new BoardCommandsImpl(game);
    }

    static BoardCommandsImpl createBoardCommands(Game game, InputStream in, OutputStream out) {
        return new BoardCommandsImpl(game, in, out);
    }

    void runGame();
}
