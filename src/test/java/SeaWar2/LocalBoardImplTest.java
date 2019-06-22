package SeaWar2;

import org.junit.Assert;
import org.junit.Test;
import seaWar2.Game;
import seaWar2.GameStatus;
import seaWar2.StatusException;
import seaWar2.board.*;

public class LocalBoardImplTest {

    Game createGame() {
        return Game.createGame();
    }
    LocalBoard createLocalBoard(Game game) {
        return LocalBoard.createLocalBoard(game);
    }


    @Test
    public void createBoard_GoodCaseTest() {
        Game game = this.createGame();
        Assert.assertEquals(GameStatus.PREPARING, game.getStatus());
    }

    @Test
    public void getShip_GoodCaseTest01() throws ShipNotAvailableException, StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        Ship ship = board.getUnsetShip(5);

        Assert.assertNotNull(ship);
        Assert.assertEquals(5, ship.getLength());
        Assert.assertFalse(ship.isPlaced());
    }

    @Test
    public void getShip_GoodCaseTest02()
            throws ShipNotAvailableException, StatusException, InvalidPositionException, ShipAlreadySetException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship1 = board.getUnsetShip(4);
        board.setShip(ship1, 0, 0, true);
        Ship ship2 = board.getUnsetShip(4);

        Assert.assertNotNull(ship1);
        Assert.assertEquals(4, ship1.getLength());
        Assert.assertTrue(ship1.isPlaced());

        Assert.assertNotNull(ship2);
        Assert.assertEquals(4, ship2.getLength());
        Assert.assertFalse(ship2.isPlaced());
    }

    @Test
    public void getShip_GoodCaseTest03()
            throws ShipNotAvailableException, StatusException, InvalidPositionException, ShipAlreadySetException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        int row = 0;
        for (int i = 0; i < 3; i++) {
            Ship ship = board.getUnsetShip(3);
            board.setShip(ship, row, 0, true);
            Assert.assertNotNull(ship);
            Assert.assertEquals(3, ship.getLength());
            Assert.assertTrue(ship.isPlaced());
            row += 2;
        }
    }

    @Test
    public void getShip_GoodCaseTest04()
            throws ShipNotAvailableException, StatusException, InvalidPositionException, ShipAlreadySetException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        int row = 0;
        for (int i = 0; i < 4; i++) {
            Ship ship = board.getUnsetShip(2);
            board.setShip(ship, row, 0, true);
            Assert.assertNotNull(ship);
            Assert.assertEquals(2, ship.getLength());
            Assert.assertTrue(ship.isPlaced());
            row += 2;
        }
    }

    @Test(expected = ShipNotAvailableException.class)
    public void getShip_BadCaseTest01() throws ShipNotAvailableException, StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        board.getUnsetShip(1);
    }

    @Test(expected = ShipNotAvailableException.class)
    public void getShip_BadCaseTest02() throws ShipNotAvailableException, StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        board.getUnsetShip(6);
    }

    @Test(expected = ShipNotAvailableException.class)
    public void getShip_BadCaseTest03()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        for (int i = 0; i < 2; i++)
            board.setShip(board.getUnsetShip(5), 0, i, true);
    }

    @Test(expected = ShipNotAvailableException.class)
    public void getShip_BadCaseTest04()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        int row = 0;
        for (int i = 0; i < 3; i++) {
            board.setShip(board.getUnsetShip(4), row, 0, true);
            row += 2;
        }
    }

    @Test(expected = ShipNotAvailableException.class)
    public void getShip_BadCaseTest05()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        int row = 0;
        for (int i = 0; i < 4; i++) {
            board.setShip(board.getUnsetShip(3), row, 0, true);
            row += 2;
        }
    }

    @Test(expected = ShipNotAvailableException.class)
    public void getShip_BadCaseTest06()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        int row = 0;
        for (int i = 0; i < 5; i++) {
            board.setShip(board.getUnsetShip(2), row, 0, true);
            row += 2;
        }
    }

    @Test
    public void generalShipPlacement_GoodCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship1 = board.getUnsetShip(4);
        board.setShip(ship1, 0, 0, true);

        Ship ship2 = board.getUnsetShip(4);
        board.setShip(ship2, 2, 0, true);

        Assert.assertTrue(ship1.isPlaced());
        Assert.assertTrue(ship2.isPlaced());
    }

    @Test(expected = ShipAlreadySetException.class)
    public void generalShipPlacement_BadCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        Ship ship = board.getUnsetShip(4);

        board.setShip(ship, 0, 0, true);
        board.setShip(ship, 2, 0, true);
    }

    @Test
    public void removeShip_GoodCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, EmptyFieldException,
            ShipAlreadySetException, StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(5);
        Assert.assertFalse(ship.isPlaced());

        board.setShip(ship, 0, 0, true);
        Assert.assertTrue(ship.isPlaced());

        board.unsetShip(0, 3);
        Assert.assertFalse(ship.isPlaced());

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(0, 0));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(1, 0));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(2, 0));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(3, 0));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(4, 0));

        board.setShip(board.getUnsetShip(5), 0, 0, true);

        Assert.assertTrue(ship.isPlaced());
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 0));
    }

    @Test
    public void removeShip_BadCaseTest01() throws OutOfBoardException, EmptyFieldException, StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        board.unsetShip(4, 2);
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(4, 2));

    }

    @Test(expected = OutOfBoardException.class)
    public void removeShip_BadCaseTest02() throws OutOfBoardException, EmptyFieldException, StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        board.unsetShip(42, 42);
    }

    @Test
    public void horizontalShipPlacement_GoodCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(3);
        board.setShip(ship, 7, 4, true);

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(7, 4));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(7, 5));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(7, 6));

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(7, 3));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(7, 7));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(6, 4));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(8, 4));
    }

    @Test
    public void horizontalShipPlacement_BorderCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(4);
        board.setShip(ship, 0, 0, true);    //fitting to upper-left corner

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 0));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 1));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 2));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 3));

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(0, 4));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(1, 3));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(1, 0));
    }

    @Test
    public void horizontalShipPlacement_BorderCaseTest02()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(4);
        board.setShip(ship, 9, 0, true);    //fitting to bottom-left corner

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(9, 0));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(9, 1));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(9, 2));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(9, 3));

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(9, 4));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(8, 3));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(8, 0));
    }

    @Test
    public void verticalShipPlacement_BorderCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(4);
        board.setShip(ship, 0, 9, false);   //fitting to upper-right corner

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(1, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(2, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(3, 9));

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(0, 8));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(4, 9));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(3, 8));
    }

    @Test
    public void verticalShipPlacement_BorderCaseTest02()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(5);
        board.setShip(ship, 5, 9, false);   //fitting to bottom-right corner

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(5, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(6, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(7, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(8, 9));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(9, 9));

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(4, 9));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(5, 8));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(9, 8));
    }

    @Test
    public void verticalShipPlacement_GoodCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(3);
        board.setShip(ship, 7, 4, false);

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(7, 4));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(8, 4));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(9, 4));

        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(6, 4));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(7, 3));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(7, 5));
        Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(9, 3));
    }

    @Test(expected = OutOfBoardException.class)
    public void verticalShipPlacement_BadCase01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(2);
        board.setShip(ship, 10, 9, false);
    }

    @Test(expected = OutOfBoardException.class)
    public void verticalShipPlacement_BadCase02()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(5);
        board.setShip(ship, 7, 4, false);
    }

    @Test(expected = InvalidPositionException.class)
    public void verticalShipPlacement_BadCase03()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship legalShip = board.getUnsetShip(3);
        board.setShip(legalShip, 4, 9, false);
        Ship illegalShip = board.getUnsetShip(5);
        board.setShip(illegalShip, 2, 8, false);
    }

    @Test(expected = OutOfBoardException.class)
    public void verticalShipPlacement_BadCase04()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship legalShip = board.getUnsetShip(3);
        board.setShip(legalShip, 4, 9, false);
        Ship illegalShip = board.getUnsetShip(5);
        board.setShip(illegalShip, 6, 8, false);
    }

    @Test(expected = OutOfBoardException.class)
    public void horizontalShipPlacement_BadCase01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(2);
        board.setShip(ship, 0, 9, true);
    }

    @Test(expected = OutOfBoardException.class)
    public void horizontalShipPlacement_BadCase02()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(2);
        board.setShip(ship, -1, 7, true);
    }

    @Test(expected = InvalidPositionException.class)
    public void horizontalShipPlacement_BadCase03()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship legalShip = board.getUnsetShip(3);
        board.setShip(legalShip, 4, 4, true);
        Ship illegalShip = board.getUnsetShip(3);
        board.setShip(illegalShip, 4, 4, true);
    }

    @Test(expected = InvalidPositionException.class)
    public void horizontalShipPlacement_BadCase04()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship legalShip = board.getUnsetShip(3);
        board.setShip(legalShip, 4, 4, true);
        Ship illegalShip = board.getUnsetShip(4);
        board.setShip(illegalShip, 2, 5, false);
    }

    @Test(expected = InvalidPositionException.class)
    public void horizontalShipPlacement_BadCase05()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship legalShip = board.getUnsetShip(3);
        board.setShip(legalShip, 4, 4, true);
        Ship illegalShip = board.getUnsetShip(4);
        board.setShip(illegalShip, 3, 2, true);
    }

    @Test(expected = OutOfBoardException.class)
    public void horizontalShipPlacement_BadCase06()
            throws ShipAlreadySetException, InvalidPositionException, StatusException, ShipNotAvailableException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Ship ship = board.getUnsetShip(3);
        board.setShip(ship, 4, 8, true);
    }

    @Test
    public void getReadyToPlay_GoodCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        board.setShip(board.getUnsetShip(5), 0, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 5, true);
        board.setShip(board.getUnsetShip(3), 4, 0, true);
        board.setShip(board.getUnsetShip(3), 4, 4, true);
        board.setShip(board.getUnsetShip(3), 6, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 3, true);
        board.setShip(board.getUnsetShip(2), 8, 6, true);
        board.setShip(board.getUnsetShip(2), 0, 6, true);


        Assert.assertTrue(board.isReady());
    }

    @Test
    public void getReadyToPlay_GoodCaseTest02() {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Assert.assertFalse(board.isReady());
        Assert.assertEquals(GameStatus.PREPARING, game.getStatus());
    }

    @Test
    public void getReadyToPlay_GoodCaseTest03()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        board.setShip(board.getUnsetShip(5), 0, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 5, true);
        board.setShip(board.getUnsetShip(3), 4, 0, true);

        Assert.assertFalse(board.isReady());
        Assert.assertEquals(GameStatus.PREPARING, game.getStatus());
    }

    @Test
    public void getReadyToPlay_BorderCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, ShipAlreadySetException,
            StatusException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        board.setShip(board.getUnsetShip(5), 0, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 5, true);
        board.setShip(board.getUnsetShip(3), 4, 0, true);
        board.setShip(board.getUnsetShip(3), 4, 4, true);
        board.setShip(board.getUnsetShip(3), 6, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 3, true);
        board.setShip(board.getUnsetShip(2), 8, 6, true);

        Assert.assertFalse(board.isReady());
        Assert.assertEquals(GameStatus.PREPARING, game.getStatus());
    }

    @Test
    public void shootShip_GoodCaseTest01()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, StatusException,
            ShipAlreadySetException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        board.setShip(board.getUnsetShip(5), 0, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 5, true);
        board.setShip(board.getUnsetShip(3), 4, 0, true);
        board.setShip(board.getUnsetShip(3), 4, 4, true);
        board.setShip(board.getUnsetShip(3), 6, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 3, true);
        board.setShip(board.getUnsetShip(2), 8, 6, true);
        board.setShip(board.getUnsetShip(2), 0, 6, true);

        game.setStatus(GameStatus.ACTIVE);

        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 0));
        Assert.assertEquals(ShotResult.HIT, board.shoot(0, 0));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(0, 3));
        Assert.assertEquals(ShotResult.HIT, board.shoot(0, 3));
        Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(4, 6));
        Assert.assertEquals(ShotResult.HIT, board.shoot(4, 6));

        Assert.assertEquals(ShotResult.HIT, board.shoot(0, 6));
        Assert.assertEquals(ShotResult.SUNK, board.shoot(0, 7));

        Assert.assertEquals(GameStatus.ACTIVE, game.getStatus());

        Assert.assertEquals(ShotResult.WATER, board.shoot(0, 0));
        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(0, 3));
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(4, 6));
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(0, 6));
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(0, 7));

        Assert.assertEquals(FieldStatus.HIT, board.getFieldStatus(0, 0));
        Assert.assertEquals(FieldStatus.HIT, board.getFieldStatus(0, 3));
        Assert.assertEquals(FieldStatus.HIT, board.getFieldStatus(4, 6));
        Assert.assertEquals(FieldStatus.HIT, board.getFieldStatus(0, 6));
        Assert.assertEquals(FieldStatus.HIT, board.getFieldStatus(0, 7));
    }

    @Test
    public void shootShip_GoodCaseTest02()
            throws ShipNotAvailableException, InvalidPositionException, OutOfBoardException, StatusException,
            ShipAlreadySetException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        Assert.assertEquals(GameStatus.PREPARING, game.getStatus());

        board.setShip(board.getUnsetShip(5), 0, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 5, true);
        board.setShip(board.getUnsetShip(3), 4, 0, true);
        board.setShip(board.getUnsetShip(3), 4, 4, true);
        board.setShip(board.getUnsetShip(3), 6, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 3, true);
        board.setShip(board.getUnsetShip(2), 8, 6, true);
        board.setShip(board.getUnsetShip(2), 0, 6, true);

        Assert.assertTrue(board.isReady());

        //start command in ui should do something like this
        game.setStatus(GameStatus.PASSIVE);
        //-> PASSIVE means that the following shots will actually represent an attack of the enemy

        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());

        Assert.assertEquals(ShotResult.HIT, board.shoot(8, 4));
        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());

        Assert.assertEquals(ShotResult.WATER, board.shoot(8, 4));
        Assert.assertEquals(GameStatus.ACTIVE, game.getStatus());
    }

    @Test
    public void shootWater_GoodCaseTest01() throws StatusException, OutOfBoardException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        game.setStatus(GameStatus.ACTIVE);

        Assert.assertEquals(ShotResult.WATER, board.shoot(4, 2));
        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(0, 0));
        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(0, 9));
        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(9, 0));
        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());
        game.setStatus(GameStatus.ACTIVE);
        Assert.assertEquals(ShotResult.WATER, board.shoot(9, 9));

        Assert.assertEquals(GameStatus.PASSIVE, game.getStatus());
    }

    @Test(expected = OutOfBoardException.class)
    public void shootWater_BadCaseTest01() throws StatusException, OutOfBoardException {
        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        game.setStatus(GameStatus.ACTIVE);

        board.shoot(10, 2);
    }

    @Test(expected = StatusException.class)
    public void shootWater_BadCaseTest02() throws StatusException, OutOfBoardException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);
        game.setStatus(GameStatus.PASSIVE);

        board.shoot(10, 2);
    }

    @Test
    public void loseGame_GoodCaseTest01()
            throws ShipNotAvailableException, OutOfBoardException, InvalidPositionException, ShipAlreadySetException,
            StatusException {

        Game game = this.createGame();
        LocalBoard board = this.createLocalBoard(game);

        board.setShip(board.getUnsetShip(5), 0, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 0, true);
        board.setShip(board.getUnsetShip(4), 2, 5, true);
        board.setShip(board.getUnsetShip(3), 4, 0, true);
        board.setShip(board.getUnsetShip(3), 4, 4, true);
        board.setShip(board.getUnsetShip(3), 6, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 0, true);
        board.setShip(board.getUnsetShip(2), 8, 3, true);
        board.setShip(board.getUnsetShip(2), 8, 6, true);
        board.setShip(board.getUnsetShip(2), 0, 6, true);

        game.setStatus(GameStatus.ACTIVE);

        for (int i = 0; i < 4; i++)
            board.shoot(0, i);
        Assert.assertEquals(ShotResult.SUNK, board.shoot(0, 4));

        for (int i = 0; i < 3; i++)
            board.shoot(2, i);
        Assert.assertEquals(ShotResult.SUNK, board.shoot(2, 3));

        for (int i = 5; i < 8; i++)
            board.shoot(2, i);
        Assert.assertEquals(ShotResult.SUNK, board.shoot(2, 8));

        for (int i = 0; i < 2; i++)
            board.shoot(4, i);
        Assert.assertEquals(ShotResult.SUNK, board.shoot(4, 2));

        for (int i = 4; i < 6; i++)
            board.shoot(4, i);
        Assert.assertEquals(ShotResult.SUNK, board.shoot(4, 6));

        for (int i = 0; i < 2; i++)
            board.shoot(6, i);
        Assert.assertEquals(ShotResult.SUNK, board.shoot(6, 2));

        Assert.assertEquals(ShotResult.HIT, board.shoot(8, 0));
        Assert.assertEquals(ShotResult.SUNK, board.shoot(8, 1));

        Assert.assertEquals(ShotResult.HIT, board.shoot(8, 3));
        Assert.assertEquals(ShotResult.SUNK, board.shoot(8, 4));

        Assert.assertEquals(ShotResult.HIT, board.shoot(8, 6));
        Assert.assertEquals(ShotResult.SUNK, board.shoot(8, 7));

        Assert.assertEquals(ShotResult.HIT, board.shoot(0, 6));
        Assert.assertEquals(ShotResult.LOST, board.shoot(0, 7));
    }
}