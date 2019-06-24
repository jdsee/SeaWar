package seaWar2.communication;

import seaWar2.ErrorMessages;
import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;
import seaWar2.board.*;
import seaWar2.view.BoardViewConsole;

import java.io.*;
import java.util.Random;

public class SWPEngineImpl implements SWPEngine {
    private DataInputStream dis;
    private DataOutputStream dos;
    private final Game game;
    private final LocalBoard localBoard;
    private RemoteBoard remoteBoard;
    private boolean opponentIsReady;
    private BoardViewConsole view;

    public SWPEngineImpl(Game game) {
        this.game = game;
        this.localBoard = game.getLocalBoard();
        this.view = game.getBoardViewConsole();
    }

    @Override
    public void run() {
        while (this.game.getStatus() != GameStatus.GAME_OVER) {
            try {
                String cmd = this.dis.readUTF().trim();
                switch (cmd) {
                    case SHOT_CMD:
                        this.handleShotCmd();
                        break;
                    case RESULT_CMD:
                        this.handleResultCmd();
                        break;
                    case READY_CMD:
                        this.handleReadyCmd();
                        break;
                    case START_CMD:
                        this.handleStartCmd();
                        break;
                    case TALK_CMD:
                        this.handleTalkCmd();
                        break;
                    case CLOSE_CONNECTION_CMD:
                        this.handleCloseConnectionCmd();
                }
            } catch (IOException ioe) {
                System.err.println(ErrorMessages.IO_STREAM_ERR + ioe.getLocalizedMessage());
                this.closeConnection();
                System.exit(1);
            } catch (OutOfBoardException obe) {
                obe.printStackTrace();
            } catch (StatusException se) {
                se.printStackTrace();
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            }
        }
    }

    @Override
    public void handleConnection(InputStream in, OutputStream out, boolean determinesStart) {
        if (this.remoteBoard == null) {
            this.remoteBoard = game.getRemoteBoard();
        }
        this.dis = new DataInputStream(in);
        this.dos = new DataOutputStream(out);
        new Thread(this).start();
    }

    private void closeConnection() {
        try {
            this.dis.close();
        } catch (IOException ioe2) {
        }
        try {
            this.dos.close();
        } catch (IOException ioe2) {
        }
    }

    @Override
    public void sendCloseConnectionCmd() throws IOException {
        this.dos.writeUTF(CLOSE_CONNECTION_CMD);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        closeConnection();
    }

    private void handleCloseConnectionCmd() {
        System.out.println("Your opponent left the game. The connection has been closed.");
        this.closeConnection();
    }

    private int parseShotResultToInt(ShotResult res) throws IllegalArgumentException {
        switch (res) {
            case WATER:
                return 0;
            case HIT:
                return 1;
            case SUNK:
                return 2;
            case LOST:
                return 3;
            default:
                throw new IllegalArgumentException();
        }
    }

    private ShotResult parseIntToShotResult(int res) throws IllegalArgumentException {
        switch (res) {
            case 0:
                return ShotResult.WATER;
            case 1:
                return ShotResult.HIT;
            case 2:
                return ShotResult.SUNK;
            case 3:
                return ShotResult.LOST;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void sendShotCmd(int row, int column) throws IOException, StatusException, OutOfBoardException {
        this.checkStatus(GameStatus.ACTIVE);
        this.dos.writeUTF(SHOT_CMD);
        this.dos.writeInt(row);
        this.dos.writeInt(column);
    }

    @Override
    public void handleShotCmd() throws IOException, StatusException, OutOfBoardException {
        int row = this.dis.readInt();
        int column = this.dis.readInt();

        ShotResult res = this.localBoard.shoot(row, column);

        switch (res) {
            case WATER:
                this.game.setStatus(GameStatus.ACTIVE);
                this.view.printMessage("Your opponent hit the water. It's your turn now!");
                break;
            case HIT:
                //status remains PASSIVE
                this.view.printMessage("Your opponent hit one of your ships. It's still his turn!");
                break;
            case SUNK:
                //status remains PASSIVE
                this.view.printMessage("Your opponent sunk one of your ships. It's still his turn!");
                break;
            case LOST:
                //Calling player loses
                this.game.setStatus(GameStatus.GAME_OVER);
                this.view.printMessage("All of your ships are destroyed! YOU LOST!");
                break;
        }
        view.printBoards(localBoard, remoteBoard);
        sendResultCmd(row, column, res);
    }

    private void sendResultCmd(int row, int column, ShotResult res) throws IOException {
        this.dos.writeUTF(RESULT_CMD);
        this.dos.writeInt(row);
        this.dos.writeInt(column);
        int resInt = parseShotResultToInt(res);
        this.dos.writeInt(resInt);
    }

    private void handleResultCmd() throws IOException, OutOfBoardException, IllegalArgumentException {
        int row = this.dis.readInt();
        int column = this.dis.readInt();

        int resInt = this.dis.readInt();
        ShotResult res = this.parseIntToShotResult(resInt);

        switch (res) {
            case WATER:
                this.remoteBoard.setFieldStatus(row, column, FieldStatus.SHOT_ON_WATER);
                this.game.setStatus(GameStatus.PASSIVE);
                this.view.printMessage("You hit the water. It's your opponent's turn now!");
                break;
            case HIT:
                this.view.printMessage("You hit a ship! Shoot again!");
                this.remoteBoard.setFieldStatus(row, column, FieldStatus.HIT);
                break;  //status remains ACTIVE
            case SUNK:
                this.view.printMessage("You hit a ship and sunk it! Shoot again!");
                this.remoteBoard.setFieldStatus(row, column, FieldStatus.HIT);
                break;  //status remains ACTIVE
            case LOST:
                //Calling player wins
                this.game.setStatus(GameStatus.GAME_OVER);
                this.view.printMessage("All of your opponents ships are destroyed! YOU WON!");
                break;
        }
        view.printBoards(localBoard, remoteBoard);
    }

    private void checkStatus(GameStatus required) throws StatusException {
        if (this.game.getStatus() != required) {
            throw new StatusException();
        }
    }

    @Override
    public void sendReadyCmd() throws IOException, StatusException {
        this.checkStatus(GameStatus.READY);
        this.dos.writeUTF(READY_CMD);
        if (this.opponentIsReady) {
            boolean callerStarts = new Random().nextBoolean();
            this.game.setStatus(callerStarts ? GameStatus.ACTIVE : GameStatus.PASSIVE);
            this.sendStartCmd(callerStarts);
        } else {
            System.out.println("\nWait until your opponent is ready");
            while (!this.opponentIsReady) {
                try {
                    System.out.print(".");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //
                }
            }
        }
    }

    private void handleReadyCmd() {
        this.opponentIsReady = true;
    }

    private void sendStartCmd(boolean callerStarts) throws IOException {
        this.dos.writeUTF(START_CMD);
        this.dos.writeBoolean(callerStarts);

        this.notifyBeginUser(callerStarts);
    }

    private void handleStartCmd() throws IOException {
        boolean callerStarts = this.dis.readBoolean();
        this.game.setStatus(callerStarts ? GameStatus.PASSIVE : GameStatus.ACTIVE);

        this.notifyBeginUser(!callerStarts);
    }

    private void notifyBeginUser(boolean callerStarts) {
        String startMessage = (callerStarts ? "You start the game!" : "Your opponent starts the game!");
        this.view.printMessage("\nLet's go! -> " + startMessage);
    }

    @Override
    public boolean isConnected() {
        return this.dis != null || this.dos != null;
    }

    @Override
    public synchronized void sendMessage(String message) throws IOException {
        this.dos.writeUTF(TALK_CMD);
        this.dos.writeUTF(message);
    }

    private void handleTalkCmd() throws IOException {
        view.printMessage("\nYour opponent: "+this.dis.readUTF()+"\n");
    }
}