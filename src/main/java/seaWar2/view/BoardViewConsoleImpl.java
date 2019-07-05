package seaWar2.view;

import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;
import seaWar2.board.*;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author joschaseelig
 */
public class BoardViewConsoleImpl implements BoardViewConsole {
    Game game;
    PrintStream screen;

    public BoardViewConsoleImpl(Game game, OutputStream out) {
        this.game = game;
        this.screen = new PrintStream(out);
    }

    @Override
    public void printMessage(String message) {
        this.screen.println(message);
    }

    public void printPrompt() {
        screen.print("\n-> ");
    }

    @Override
    public void printBoards(LocalBoard local, RemoteBoard remote) {
        Field[][] localFields = local.getFields();
        FieldStatus[][] remoteFields = remote.getFieldStatus();

        this.printSeperator('·');
        this.screen.print("\n");
        if (this.game.getStatus() == GameStatus.ACTIVE) {
            this.screen.print(ANSI_BRIGHT_CYAN + ANSI_HIGH_INTENSITY + "            - YOU -          " + ANSI_RESET + "|");
            this.screen.print(ANSI_HIGH_INTENSITY + "       - YOUR RIVAL -" + ANSI_RESET);
        } else if (this.game.getStatus() == GameStatus.PASSIVE) {
            this.screen.print(ANSI_HIGH_INTENSITY + "            - YOU -          " + ANSI_RESET + "|");
            this.screen.print(ANSI_BRIGHT_CYAN + ANSI_HIGH_INTENSITY + "       - YOUR RIVAL -" + ANSI_RESET);
        } else {
            this.screen.print(ANSI_HIGH_INTENSITY + "            - YOU -          " + ANSI_RESET + "|");
            this.screen.print(ANSI_HIGH_INTENSITY + "       - YOUR RIVAL -" + ANSI_RESET);
        }

        this.screen.print("\n");
        this.printSeperator('–');
        this.screen.print("\n");

        this.printColumnIndices();
        this.screen.print("\n");
        this.printSeperator('–');
        this.screen.print("\n");

        for (int i = 0; i <= Board.MAX_ROW_INDEX; i++) {
            this.screen.print("| " + (char) (Board.MIN_ROW_CHAR + i) + " | ");
            for (int j = 0; j <= Board.MAX_COLUMN_INDEX; j++) {
                switch (localFields[i][j].getFieldStatus()) {
                    case WATER:
                        this.screen.print(ANSI_LOW_INTENSITY + WATER_SYMBOL + ANSI_RESET);
                        break;
                    case SHIP:
                        this.screen.print(SHIP_SYMBOL);
                        break;
                    case HIT:
                        this.screen.print(HIT_SHIP_SYMBOL);
                        break;
                    case SUNK_SHIP:
                        this.screen.print(ANSI_LOW_INTENSITY + HIT_SHIP_SYMBOL + ANSI_RESET);
                        break;
                    case SHOT_ON_WATER:
                        this.screen.print(SHOT_ON_WATER_SYMBOL);
                        break;
                }
                this.screen.print(" ");
            }
            this.screen.print(" | " + (char) (Board.MIN_ROW_CHAR + i) + " | ");
            for (int k = 0; k <= Board.MAX_COLUMN_INDEX; k++) {
                switch (remoteFields[i][k]) {
                    case WATER:
                        this.screen.print(WATER_SYMBOL);
                        break;
                    case UNKOWN:
                        this.screen.print(ANSI_LOW_INTENSITY + UNKNOWN_SYMBOL + ANSI_RESET);
                        break;
                    case HIT:
                        this.screen.print(HIT_SHIP_SYMBOL);
                        break;
                    case SUNK_SHIP:
                        this.screen.print(ANSI_LOW_INTENSITY + HIT_SHIP_SYMBOL + ANSI_RESET);
                        break;
                    case SHOT_ON_WATER:
                        this.screen.print(SHOT_ON_WATER_SYMBOL);
                        break;
                }
                this.screen.print(" ");
            }
            this.screen.print("| " + (char) (Board.MIN_ROW_CHAR + i) + " |");
            this.screen.print("\n");
        }
        this.printSeperator('–');
        this.screen.print("\n");
        this.printColumnIndices();
        this.screen.print("\n");
        this.printSeperator('–');
        this.screen.print("\n");
    }

    private void printColumnIndices() {
        this.screen.print("|   | ");
        for (int j = 0; j < 2; j++) {
            if (j == 1)
                this.screen.print("|   | ");
            for (int i = 1; i <= Board.MAX_COLUMN_INDEX + 1; i++)
                this.screen.print(i + " ");
        }
        this.screen.print("\b|   |");
    }

    private void printSeperator(char symbol) {
        int length = 57;
        for (int i = 0; i <= length; i++) {
            this.screen.print(symbol);
        }
    }

    private void printShipFleet() {

    }

    @Override
    public void printPreparationCommands() throws StatusException {
        this.game.checkStatus(GameStatus.PREPARING);
        screen.println("｜ set ship: [" + BoardCommands.SET_SHIP_ABR_CMD +
                "] ｜ remove ship: [" + BoardCommands.REMOVE_SHIP_ABR_CMD +
                "] ｜ connect: [" + BoardCommands.CONNECT_ABR_CMD + "]     ｜"
        );
        this.printSeperator('-');
    }

    @Override
    public void printPlayCommands() throws StatusException {
        this.game.checkStatus(GameStatus.PASSIVE, GameStatus.ACTIVE);
        screen.println(this.game.getStatus() + " ｜ shoot ship: [" + BoardCommands.SHOOT_ABR_2_CMD +
                "] ｜ chat: [" + BoardCommands.TALK_CMD +
                "] ｜ give up: [" + BoardCommands.GIVE_UP_CMD + "]"
        );
        this.printSeperator('-');
    }

    @Override
    public void printHeader() {
        screen.print(" ________  _______   ________  ___       __   ________  ________    _______     \n" +
                "|\\   ____\\|\\  ___ \\ |\\   __  \\|\\  \\     |\\  \\|\\   __  \\|\\   __  \\  /  ___  \\    \n" +
                "\\ \\  \\___|\\ \\   __/|\\ \\  \\|\\  \\ \\  \\    \\ \\  \\ \\  \\|\\  \\ \\  \\|\\  \\/__/|_/  /|   \n" +
                " \\ \\_____  \\ \\  \\_|/_\\ \\   __  \\ \\  \\  __\\ \\  \\ \\   __  \\ \\   _  _\\__|//  / /   \n" +
                "  \\|____|\\  \\ \\  \\_|\\ \\ \\  \\ \\  \\ \\  \\|\\__\\_\\  \\ \\  \\ \\  \\ \\  \\\\  \\|  /  /_/__  \n" +
                "    ____\\_\\  \\ \\_______\\ \\__\\ \\__\\ \\____________\\ \\__\\ \\__\\ \\__\\\\ _\\ |\\________\\\n" +
                "   |\\_________\\|_______|\\|__|\\|__|\\|____________|\\|__|\\|__|\\|__|\\|__| \\|_______|\n" +
                "   \\|_________|                                                                 \n\n");
    }

    @Override
    public void printShipGraphik() {
        screen.print("       __    __    __\n" +
                "                             |==|  |==|  |==|\n" +
                "                           __|__|__|__|__|__|_\n" +
                "                        __|___________________|___\n" +
                "                     __|__[]__[]__[]__[]__[]__[]__|___\n" +
                "                    |............................o.../\n" +
                "                    \\.............................../\n" +
                "               hjw_,~')_,~')_,~')_,~')_,~')_,~')_,~')/,~')_\n" +
                "\n");
    }
}
