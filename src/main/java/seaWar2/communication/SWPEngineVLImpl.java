package seaWar2.communication;

import seaWar2.ErrorMessages;
import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;
import seaWar2.board.LocalBoard;
import seaWar2.board.OutOfBoardException;
import seaWar2.board.ShotResult;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SWPEngineVLImpl implements SWPEngineVL {
    private DataInputStream dis;
    private DataOutputStream dos;

    private final Game game;
    private final LocalBoard localBoard;

    public SWPEngineVLImpl(Game game) {
        this.game = game;
        this.localBoard = game.getLocalBoard();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cmd = dis.readUTF();
                switch (cmd) {
                    case READY_REQUEST_CMD:
                        writeReadyRequestReactionPDU();
                        break;
                    case START_CMD:
                        readStartParameter();
                        break;
                    case SHOT_CMD:
                        readShotParameter();
                        break;
                    case GIVEUP_CMD:
                        //do giveup
                        break;
                }
            } catch (IOException ioe) {
                System.err.println(ErrorMessages.IO_STREAM_ERR + ioe.getLocalizedMessage());
                closeConnection();
            } catch (StatusException e) {
                e.printStackTrace();
            } catch (OutOfBoardException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleConnection(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        new Thread(this).start();
    }

    @Override
    public void closeConnection() {
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
    public void readShotParameter() throws IOException, StatusException, OutOfBoardException {
        String rowString = this.dis.readUTF();
        int row = parseRowStringToInt(rowString);
        int column = this.dis.readInt();

        try {
            ShotResult res = this.localBoard.shoot(row, column);
            writeResultPDU(res);
        } catch (StatusException e) {
            e.printStackTrace();
            throw new StatusException();
        } catch (OutOfBoardException e) {
            e.printStackTrace();
            //ignore
        }
    }

    private int parseRowStringToInt(String rowString) throws OutOfBoardException {
        switch (rowString) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "G":
                return 6;
            case "H":
                return 7;
            case "I":
                return 8;
            case "J":
                return 9;
            default:
                throw new OutOfBoardException();
        }
    }

    @Override
    public ShotResult readResultParameter() throws IOException {
        String resString = this.dis.readUTF();
        ShotResult res = parseStringToShotResult(resString);
        return res;

    }

    private ShotResult parseStringToShotResult(String resString) {
        switch (resString) {
            case WATER_STRING:
                return ShotResult.WATER;
            case HIT_STRING:
                return ShotResult.HIT;
            case SUNK_STRING:
                return ShotResult.SUNK;
            case LOST_STRING:
                return ShotResult.LOST;
            default:
                return null;
        }
    }

    @Override
    public void readStartParameter() throws IOException {
        game.setStatus(this.dis.readBoolean() ? GameStatus.PASSIVE : GameStatus.ACTIVE);
    }

    @Override
    public void writeStartPDU(boolean callerStarts) throws IOException {
        this.dos.writeUTF(START_CMD);
        this.dos.writeBoolean(callerStarts);
    }

    @Override
    public boolean writeReadyRequestPDU() throws IOException {
        this.dos.writeUTF(READY_REQUEST_CMD);
        return this.dis.readBoolean();
    }

    @Override
    public void writeReadyRequestReactionPDU() throws IOException {
        this.dos.writeBoolean(this.game.getStatus() == GameStatus.READY);
    }

    @Override
    public void writeShotPDU(int row, int column) throws IOException {
        this.dos.writeUTF(SHOT_CMD);
        this.dos.writeInt(row);
        this.dos.writeInt(column);
    }

    @Override
    public void writeResultPDU(ShotResult res) throws IOException {
        this.dos.writeUTF(RESULT_CMD);
        String resString = parseShotResultToString(res);
        this.dos.writeUTF(resString);
    }

    private String parseShotResultToString(ShotResult res) {
        switch (res) {
            case WATER:
                return WATER_STRING;
            case HIT:
                return HIT_STRING;
            case SUNK:
                return SUNK_STRING;
            case LOST:
                return LOST_STRING;
            default:
                return null;
        }
    }

    @Override
    public void writeGiveUpPDU() throws IOException {
        dos.writeUTF(GIVEUP_CMD);
    }
}
