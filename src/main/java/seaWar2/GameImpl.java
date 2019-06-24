package seaWar2;

import seaWar2.board.*;
import seaWar2.communication.SWPEngine;
import seaWar2.communication.TCPChannel;
import seaWar2.view.BoardCommands;
import seaWar2.view.BoardViewConsole;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author joschaseelig
 * @see seaWar2.Game
 */
public class GameImpl implements Game {

    private final BoardViewConsole view;
    private final BoardCommands boardCommands;
    private final LocalBoard localBoard;
    private final RemoteBoard remoteBoard;
    private final SWPEngine swpEngine;
    private TCPChannel channel;
    private GameStatus status;

    GameImpl() {
        this(System.in, System.out);
    }

    GameImpl(InputStream in, OutputStream out) {
        this.status = GameStatus.PREPARING;
        this.localBoard = LocalBoard.createLocalBoard(this);
        this.view = BoardViewConsole.createBoardViewConsole(this, out);
        this.swpEngine = SWPEngine.createSWPEngine(this);
        this.remoteBoard = RemoteBoard.createRemoteBoard(this);
        this.boardCommands = BoardCommands.createBoardCommands(this, in, out);
    }

    public static void main(String[] args) {
        Game.createGame().startGame();
    }

    @Override
    public void startGame() {
        try {
            localBoard.setShip(localBoard.getUnsetShip(5), 0, 0, true);
            localBoard.setShip(localBoard.getUnsetShip(4), 2, 0, true);
            localBoard.setShip(localBoard.getUnsetShip(4), 2, 9, false);
            localBoard.setShip(localBoard.getUnsetShip(3), 4, 0, true);
            localBoard.setShip(localBoard.getUnsetShip(3), 4, 4, true);
            localBoard.setShip(localBoard.getUnsetShip(3), 6, 0, true);
            localBoard.setShip(localBoard.getUnsetShip(2), 8, 0, true);
            localBoard.setShip(localBoard.getUnsetShip(2), 8, 3, true);
            localBoard.setShip(localBoard.getUnsetShip(2), 8, 6, true);
            localBoard.setShip(localBoard.getUnsetShip(2), 0, 6, true);
        } catch (InvalidPositionException e) {
            e.printStackTrace();
        } catch (StatusException e) {
            e.printStackTrace();
        } catch (ShipAlreadySetException e) {
            e.printStackTrace();
        } catch (ShipNotAvailableException e) {
            e.printStackTrace();
        }
        view.printBoards(localBoard, remoteBoard);

        this.boardCommands.runGame();
    }

    @Override
    public LocalBoard getLocalBoard() {
        return this.localBoard;
    }

    @Override
    public RemoteBoard getRemoteBoard() {
        return this.remoteBoard;
    }

    @Override
    public SWPEngine getSWPEngine() {
        return this.swpEngine;
    }

    @Override
    public BoardViewConsole getBoardViewConsole() {
        return this.view;
    }

    @Override
    public BoardCommands getBoardCommands() {
        return this.boardCommands;
    }

    @Override
    public GameStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @Override
    public TCPChannel getTCPChannel() {
        return this.channel;
    }

    @Override
    public void setTCPChannel(TCPChannel channel) {
        this.channel = channel;
    }
}
