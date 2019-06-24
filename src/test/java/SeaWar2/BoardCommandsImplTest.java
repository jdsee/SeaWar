package SeaWar2;

import org.junit.Test;
import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;
import seaWar2.board.*;
import seaWar2.view.BoardCommands;
import seaWar2.view.BoardViewConsole;

import java.io.*;

public class BoardCommandsImplTest {
    private Game game;

    private void createTestGame(InputStream in) {
        this.game = Game.createGame(in, System.out);
    }

    private LocalBoard getLocalBoard() {
        return this.game.getLocalBoard();
    }

    private RemoteBoard getRemoteBoard() {
        return this.game.getRemoteBoard();
    }

    private BoardViewConsole getBoardView() {
        return this.game.getBoardViewConsole();
    }

    private BoardCommands getBoardCommands() {
        return this.game.getBoardCommands();
    }

    @Test
    public void setShipsAndShowBoards_GoodTest01()
            throws StatusException, ShipNotAvailableException, ShipAlreadySetException, InvalidPositionException {
        this.createTestGame(System.in);
        LocalBoard localBoard = this.getLocalBoard();
        RemoteBoard remoteBoard = this.getRemoteBoard();
        BoardViewConsole view = this.getBoardView();

        view.printBoards(localBoard, remoteBoard);

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

        view.printBoards(localBoard, remoteBoard);

        game.setStatus(GameStatus.PASSIVE);
        localBoard.shoot(0, 4);

        view.printBoards(localBoard, remoteBoard);
    }

    @Test
    public void setShipsAndShootThem_GoodCae01() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF("set 5 a 1 h\n");
        dos.writeUTF("set 4 c 1 v\n");
        dos.writeUTF("set 4 c 3 v\n");
        dos.writeUTF("set 3 a 7 h\n");
        dos.writeUTF("set 3 c 5 h\n");
        dos.writeUTF("set 3 e 5 h\n");
        dos.writeUTF("set 2 h 1 h\n");
        dos.writeUTF("set 2 h 4 h\n");
        dos.writeUTF("set 2 j 1 h\n");
        dos.writeUTF("set 2 h 8 v\n");
        dos.writeUTF("start\n");
        dos.writeUTF("shoot a 1\n");

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream commandStream = new DataInputStream(bais);

        createTestGame(commandStream);
        BoardCommands boardCommands = this.getBoardCommands();
        boardCommands.runGame();

    }

    @Test
    public void connectAndDetermineBeginner_GoodCase01() {
        new Thread(() -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);

                dos.writeUTF("connect\n");
                dos.writeUTF("testSocketA\n");
                dos.writeUTF("y\n");
                dos.writeUTF("8080\n");
                dos.writeUTF("set 5 a 1 h\n");
                dos.writeUTF("set 4 c 1 v\n");
                dos.writeUTF("set 4 c 3 v\n");
                dos.writeUTF("set 3 a 7 h\n");
                dos.writeUTF("set 3 c 5 h\n");
                dos.writeUTF("set 3 e 5 h\n");
                dos.writeUTF("set 2 h 1 h\n");
                dos.writeUTF("set 2 h 4 h\n");
                dos.writeUTF("set 2 j 1 h\n");
                dos.writeUTF("set 2 h 8 v\n");
                dos.writeUTF("start\n");
                dos.writeUTF("wait\n");
                dos.writeUTF("status\n");

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                DataInputStream commandStream = new DataInputStream(bais);

                this.createTestGame(commandStream);
                BoardCommands boardCommands = this.getBoardCommands();
                boardCommands.runGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            ByteArrayOutputStream baosB = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baosB);

            dos.writeUTF("connect\n");
            dos.writeUTF("testSocketB\n");
            dos.writeUTF("n\n");
            dos.writeUTF("8080\n");
            dos.writeUTF("set 5 a 1 h\n");
            dos.writeUTF("set 4 c 1 v\n");
            dos.writeUTF("set 4 c 3 v\n");
            dos.writeUTF("set 3 a 7 h\n");
            dos.writeUTF("set 3 c 5 h\n");
            dos.writeUTF("set 3 e 5 h\n");
            dos.writeUTF("set 2 h 1 h\n");
            dos.writeUTF("set 2 h 4 h\n");
            dos.writeUTF("set 2 j 1 h\n");
            dos.writeUTF("set 2 h 8 v\n");
            dos.writeUTF("start\n");
            dos.writeUTF("wait\n");
            dos.writeUTF("status\n");

            ByteArrayInputStream baisB = new ByteArrayInputStream(baosB.toByteArray());
            DataInputStream commandStreamB = new DataInputStream(baisB);

            Game game = Game.createGame(commandStreamB, System.out);
            game.getBoardCommands().runGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void setShipsAndShootThem_GoodCase02() {
        new Thread(() -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);

                dos.writeUTF("connect\n");
                dos.writeUTF("testSocketA\n");
                dos.writeUTF("y\n");
                dos.writeUTF("8080\n");
                dos.writeUTF("set 5 a 1 h\n");
                dos.writeUTF("set 4 c 1 v\n");
                dos.writeUTF("set 4 c 3 v\n");
                dos.writeUTF("set 3 a 7 h\n");
                dos.writeUTF("set 3 c 5 h\n");
                dos.writeUTF("set 3 e 5 h\n");
                dos.writeUTF("set 2 h 1 h\n");
                dos.writeUTF("set 2 h 4 h\n");
                dos.writeUTF("set 2 j 1 h\n");
                dos.writeUTF("set 2 h 8 v\n");
                dos.writeUTF("wait\n");
                dos.writeUTF("start\n");
                dos.writeUTF("status\n");
                dos.writeUTF("shoot a 1\n");
                dos.writeUTF("print\n");
                dos.writeUTF("wait\n");
                dos.writeUTF("shoot a 2\n");
                dos.writeUTF("wait\n");
                dos.writeUTF("shoot a 3\n");
                dos.writeUTF("wait\n");
                dos.writeUTF("shoot b 2\n");

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                DataInputStream commandStream = new DataInputStream(bais);

                this.createTestGame(commandStream);
                BoardCommands boardCommands = this.getBoardCommands();
                boardCommands.runGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            ByteArrayOutputStream baosB = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baosB);

            dos.writeUTF("connect\n");
            dos.writeUTF("testSocketB\n");
            dos.writeUTF("n\n");
            dos.writeUTF("8080\n");
            dos.writeUTF("set 5 a 1 h\n");
            dos.writeUTF("set 4 c 1 v\n");
            dos.writeUTF("set 4 c 3 v\n");
            dos.writeUTF("set 3 a 7 h\n");
            dos.writeUTF("set 3 c 5 h\n");
            dos.writeUTF("set 3 e 5 h\n");
            dos.writeUTF("set 2 h 1 h\n");
            dos.writeUTF("set 2 h 4 h\n");
            dos.writeUTF("set 2 j 1 h\n");
            dos.writeUTF("set 2 h 8 v\n");
            dos.writeUTF("start\n");
            dos.writeUTF("wait\n");
            dos.writeUTF("status\n");
            dos.writeUTF("shoot a 1\n");
            dos.writeUTF("wait\n");
            dos.writeUTF("shoot a 2\n");
            dos.writeUTF("wait\n");
            dos.writeUTF("shoot a 3\n");
            dos.writeUTF("wait\n");
            dos.writeUTF("shoot b 2\n");

            ByteArrayInputStream baisB = new ByteArrayInputStream(baosB.toByteArray());
            DataInputStream commandStreamB = new DataInputStream(baisB);

            Game game = Game.createGame(commandStreamB, System.out);
            game.getBoardCommands().runGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void setShipsAndShootThem_BadCase01() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF("shot\n");
        dos.writeUTF("set\n");
        dos.writeUTF("set bndio3hi2d d31niooninde 3\n");
        dos.writeUTF("set 4 a 0 h\n");
        dos.writeUTF("set a 1 4 h\n");
        dos.writeUTF("set 5 c 42 h\n");
        dos.writeUTF("set 7 c 1 v\n");
        dos.writeUTF("set 2 K 1 v\n");
        dos.writeUTF("start\n");

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream commandStream = new DataInputStream(bais);

        createTestGame(commandStream);
        BoardCommands boardCommands = this.getBoardCommands();
        boardCommands.runGame();
    }


    @Test
    public void connectPlayersAndStartGame_GoodCase01() {
        Thread playerA = new Thread(() -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);

                dos.writeUTF("connect\n");
                dos.writeUTF("testSocketA\n");
                dos.writeUTF("y\n");
                dos.writeUTF("8080\n");
                dos.writeUTF("\n");
                dos.writeUTF("set 5 a 1 h\n");
                dos.writeUTF("remove a 3\n");
                dos.writeUTF("set 5 a 1 h\n");
                dos.writeUTF("set 4 c 1 v\n");
                dos.writeUTF("set 4 c 3 v\n");
                dos.writeUTF("set 3 a 7 h\n");
                dos.writeUTF("set 3 c 5 h\n");
                dos.writeUTF("set 3 e 5 h\n");
                dos.writeUTF("set 2 h 1 h\n");
                dos.writeUTF("set 2 h 4 h\n");
                dos.writeUTF("set 2 j 1 h\n");
                dos.writeUTF("set 2 h 8 v\n");
                dos.writeUTF("start\n");

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                DataInputStream commandStream = new DataInputStream(bais);

                this.createTestGame(commandStream);
                BoardCommands boardCommands = this.getBoardCommands();
                boardCommands.runGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread playerB = new Thread(() -> {
            try {
                ByteArrayOutputStream baosB = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baosB);

                dos.writeUTF("connect\n");
                dos.writeUTF("testSocketB\n");
                dos.writeUTF("n\n");
                dos.writeUTF("8080\n");
                dos.writeUTF("\n");
                dos.writeUTF("set 5 a 1 h\n");
                dos.writeUTF("set 5 a 1 h\n");
                dos.writeUTF("set 4 c 1 v\n");
                dos.writeUTF("set 4 c 3 v\n");
                dos.writeUTF("set 3 a 7 h\n");
                dos.writeUTF("set 3 c 5 h\n");
                dos.writeUTF("set 3 e 5 h\n");
                dos.writeUTF("set 2 h 1 h\n");
                dos.writeUTF("set 2 h 4 h\n");
                dos.writeUTF("set 2 j 1 h\n");
                dos.writeUTF("set 2 h 8 v\n");
                dos.writeUTF("start\n");

                ByteArrayInputStream baisB = new ByteArrayInputStream(baosB.toByteArray());
                DataInputStream commandStreamB = new DataInputStream(baisB);

                Game game = Game.createGame(commandStreamB, System.out);
                game.getBoardCommands().runGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        playerA.start();
        playerB.start();
    }
}