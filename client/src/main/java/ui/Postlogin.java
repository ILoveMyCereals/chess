package ui;

import chess.ChessGame;
import net.WSServerFacade;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.ListGamesRequest;
import requests.LogoutRequest;
import results.CreateGameResult;
import results.JoinGameResult;
import results.ListGamesResult;
import results.LogoutResult;
import model.GameData;
import net.ServerFacade;
import websocket.commands.ConnectCommand;

import java.util.Scanner;

public class Postlogin {

    private ServerFacade serverFacade = new ServerFacade(8080);
    private String authToken;
    private String option = "0";

    public Postlogin(String authToken) {
        this.authToken = authToken;
    }

    public void loggedInUI() {
        while (option.equals("0")) {
            System.out.println("""
                    Please select an option and input the corresponding number to continue:
                    
                    1. Create new game
                    2. Join game
                    3. Observe game
                    4. List games
                    5. Help
                    6. Logout""");
            Scanner newScan = new Scanner(System.in);
            option = newScan.nextLine();

            if (option.equals("1")) {
                System.out.println("Please enter a name for the game you're creating ");
                String gameName = newScan.nextLine();

                CreateGameRequest req = new CreateGameRequest(gameName);

                try {
                    CreateGameResult res = serverFacade.sendCreateGameRequest(req, authToken);
                    System.out.println("You have created a new game with the following ID: " + res.gameID());
                    option = "0";
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    option = "0";
                }

            } else if (option.equals("2")) {
                System.out.println("Please enter the game ID (not the game name) of the game you wish to join ");
                String gameID = newScan.nextLine();

                System.out.println("Please enter the color you wish to play (BLACK or WHITE) ");
                String playerColor = newScan.nextLine();

                ListGamesRequest req1 = new ListGamesRequest();
                try {
                    ListGamesResult res1 = serverFacade.sendListGamesRequest(req1, authToken);
                    for (int counter = 0; counter < res1.games().size(); counter++) {
                        GameData game = res1.games().get(counter);
                        if (counter == Integer.parseInt(gameID)) {
                            GameData savedGame = res1.games().get(counter);
                            JoinGameRequest req = new JoinGameRequest(playerColor, savedGame.getGameID());
                            JoinGameResult res = serverFacade.sendJoinGameRequest(req, authToken);

                            InGame inGame = new InGame(savedGame, authToken);
                            WSServerFacade newFacade = new WSServerFacade(8080, inGame);

                            ConnectCommand connectCommand = new ConnectCommand(authToken, savedGame.getGameID());
                            newFacade.sendConnectCommand(connectCommand);
                            inGame.inGameUI(); //I think this has been implemented correctly
                            option = "0";
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    option = "0";
                }

            } else if (option.equals("3")) {
                System.out.println("Please enter the game ID (not the game name) of the game you wish to observe ");
                String gameID = newScan.nextLine();

                JoinGameRequest req = new JoinGameRequest(null, Integer.parseInt(gameID));

                try {
                    JoinGameResult res = serverFacade.sendJoinGameRequest(req, authToken);
                    ChessGame game = new ChessGame();
                    DrawChessBoard draw = new DrawChessBoard();
                    draw.drawChessBoard(game, "WHITE"); //I need to revisit this too
                    option = "0";
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    option = "0";

                }
            } else if (option.equals("4")) {
                ListGamesRequest req = new ListGamesRequest();

                try {
                    ListGamesResult res = serverFacade.sendListGamesRequest(req, authToken);
                    for (int counter = 0; counter < res.games().size(); counter++) {
                        GameData game = res.games().get(counter);
                        System.out.println(
                                "Game Name: " + game.getGameName() +
                                        "\nGame ID: " + counter +
                                        "\nWhite Team Player: " + game.getWhiteUsername() +
                                        "\nBlack Team Player: " + game.getBlackUsername()
                                );
                    }
                    option = "0";
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    option = "0";
                }
            } else if (option.equals("5")) {
                System.out.println("""
                        1. Create game -- Input a name for a new chess game you want to create
                        2. Join game -- Input the ID # for the chess game you wish to join and the color of the team you wish to play
                        3. Observe game -- Input the ID # for the chess game you wish to observe
                        4. List games -- Get a list of all current chess games
                        5. Help -- Get additional information about each of the options
                        6. Logout -- Log out of your account""");
                option = "0";
            } else if (option.equals("6")) {
                LogoutRequest req = new LogoutRequest();

                try {
                    LogoutResult res = serverFacade.sendLogoutRequest(req, authToken);
                    System.out.println("You successfully logged out");
                    authToken = null;
                    Prelogin prelogin = new Prelogin();
                    prelogin.preloginUI();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    option = "0";
                }
            } else {
                System.out.println("Invalid option");
                option = "0";
            }
        }
    }

}
