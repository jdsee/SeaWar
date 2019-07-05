package seaWar2.view;

import seaWar2.Game;

import java.io.InputStream;
import java.io.OutputStream;

public interface BoardCommands {

    String PRINT_BOARDS_CMD = "print";
    String SHOOT_CMD = "shoot";
    String SHOOT_ABR_1_CMD = "s";
    String SHOOT_ABR_2_CMD = "x";
    String SET_SHIP_CMD = "set";
    String SET_SHIP_ABR_CMD = "+";
    String REMOVE_SHIP_CMD = "remove";
    String REMOVE_SHIP_ABR_CMD = "-";
    String START_GAME_CMD = "start";
    String GIVE_UP_CMD = "LLL";
    String TALK_CMD = ":";
    String GET_STATUS_CMD = "status";
    String SAVE_CMD = "save";
    String RESTORE_CMD = "load";
    String CONNECT_ABR_CMD = "c";
    String CONNECT_CMD = "connect";
    String FILL_CMD = "fill";
    String EXIT_CMD = "exit";

    String OPEN_CMD_NR = "1";
    String OPEN_CMD = "open";
    String JOIN_CMD_NR = "2";
    String JOIN_CMD = "join";

    String CHANNEL_NAME_SERVER = "server";
    String CHANNEL_NAME_CLIENT = "client";

    static BoardCommandsImpl createBoardCommands(Game game) {
        return new BoardCommandsImpl(game);
    }

    static BoardCommandsImpl createBoardCommands(Game game, InputStream in, OutputStream out) {
        return new BoardCommandsImpl(game, in, out);
    }

    void runGame();
}
