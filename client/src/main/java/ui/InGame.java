package ui;

import chess.*;
import com.google.gson.Gson;
import model.GameData;
import net.WSServerFacade;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.ResignCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.Scanner;

public class InGame implements ServerMessageObserver {

    private String option = "0";
    private GameData game;
    private String authToken;
    private WSServerFacade facade = new WSServerFacade(8080, this);

    public InGame(GameData game, String authToken) {
        this.game = game;
        this.authToken = authToken;
    }

    public void inGameUI() {
        while (option.equals("0")) {
            System.out.println("""
                    Select an option to continue:
                    1. Make Move
                    2. Redraw Board
                    3. Highlight Legal Moves
                    4. Help
                    5. Leave Game
                    6. Resign""");

            Scanner newScan = new Scanner(System.in);
            option = newScan.nextLine();

            if (option.equals("1")) {
                System.out.println("Input the position of the piece you want to move");
                String startInput = newScan.nextLine();
                System.out.println("Input the position of the square to which you want to move the chosen piece");
                String endInput = newScan.nextLine();

                ChessPosition startPosition = getPositionFromInput(startInput);
                ChessPosition endPosition = getPositionFromInput(endInput);

                ChessBoard board = game.getGame().getBoard();
                ChessPiece piece = board.getPiece(startPosition);


                ChessMove move = new ChessMove(startPosition, endPosition, null); //How do I handle the promotion portion of the chessmove?

                MakeMoveCommand moveCommand = new MakeMoveCommand(authToken, game.getGameID(), move);

                facade.sendMoveCommand(moveCommand);
                option = "0";
                //send make move command
            }
            else if (option.equals("2")) {
                DrawChessBoard drawChessBoard = new DrawChessBoard();
                drawChessBoard.drawChessBoard(game.getGame(), "WHITE");
                option = "0";
                //redraw board
            }
            else if (option.equals("3")) {
                option = "0";
                //highlight legal moves
            }
            else if (option.equals("4")) {
                System.out.println("""
                    1. Make Move -- If it is your turn, move one of your chess pieces
                    2. Redraw Board -- Get an image of the current state of the chess board
                    3. Highlight Legal Moves -- Get an image of the board highlighting the valid moves that a given piece can make
                    4. Help -- Get additional information about each of the options
                    5. Leave Game -- Leave the game. If you are playing, your spot will be left open for someone to take
                    6. Resign -- Give up, ending the game with a loss
                        """);
                option = "0";
            }
            else if (option.equals("5")) {
                LeaveCommand leaveCommand = new LeaveCommand(authToken, game.getGameID());
                facade.sendLeaveCommand(leaveCommand);

                Postlogin postlogin = new Postlogin(authToken);
                //game = null;
                postlogin.loggedInUI();

                //send a leave game request
            }
            else if (option.equals("6")) {
                ResignCommand resignCommand = new ResignCommand(authToken, game.getGameID());
                facade.sendResignCommand(resignCommand);
                option = "0";
                //send a resign request
            }
            else {
                System.out.println("Error: invalid option");
                option = "0";
            }

            //WHEN THE USER PICKS AN OPTION, A USERGAMECOMMAND OBJECT WILL BE SENT TO THE SERVER FACADE (ALSO A WEBSOCKET CONNECTION?)
            //THIS CLASS WILL RECEIVE SERVER MESSAGE OBJECTS?
            //SO TO MAKE A CONNECTION, THIS CLASS CALLS A METHOD IN SERVER FACADE THAT SENDS A WEBSOCKET CONNECTION?
        }
    }

    private ChessPosition getPositionFromInput(String readablePosition) {
        Integer columnInt = null;
        Character[] columns = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        Character columnChar = readablePosition.charAt(0);
        Character rowChar = readablePosition.charAt(1);
        Integer rowInt = Integer.parseInt(rowChar.toString());

        for (int i = 0; i < columns.length; i++) {
            if (columnChar.equals(columns[i])) {
                columnInt = i + 1; //why is this line of code unreachable?
            }
        }
        ChessPosition newPosition = new ChessPosition(rowInt, columnInt);
        return newPosition;
    }

    public void notify(ServerMessage message) {
        ServerMessage.ServerMessageType messageType = message.getServerMessageType();

        if (message.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
            NotificationMessage notificationMessage = (NotificationMessage) message;
            System.out.println(notificationMessage.getMessage());

                    //I should be able to cast message to a NotificationMessage type object here
        } else if (message.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)) {
            LoadGameMessage loadGameMessage = (LoadGameMessage) message;
            ChessGame game = loadGameMessage.getGame();
            DrawChessBoard drawChessBoard = new DrawChessBoard();
            drawChessBoard.drawChessBoard(game, "WHITE");

        } else if (message.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)) {
            ErrorMessage errorMessage = (ErrorMessage) message;
            System.out.println(errorMessage.getErrorMessage());
        }

        //What do I have to make this method do?
        //Here is where I print output to the screen depending on the message, make sure I'm not just printing jsons
        //Things like printing out the message of a notification, etc.
        return;
    }
}
