package seaWar2;

public interface ErrorMessages {
    String IO_STREAM_ERR = "Problem with In- or OutputStream: ";
    String OUT_OF_BOARD_ERR = "* The specified coordinates are out of the board! *";
    String NOT_YET_READY_ERROR = "* You can not start the game until all your ships are placed! *";
    String NOT_YET_CONNECTED_ERR = "* You are not yet connected to your opponent! Call connect first. *";
    String WRONG_SYNTAX_ERR = "* Parameters are missing or the syntax is wrong! *";
    String ILLEGAL_ARGUMENT_ERR = "The specified arguments are invalid. Please use the required syntax.";
}
