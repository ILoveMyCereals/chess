package dataaccess;

import chess.ChessGame;
import model.GameData;
import java.util.ArrayList;
import java.util.Random;

public class MemoryGameDAO implements GameDAO {

    public MemoryGameDAO(ArrayList<GameData> games) {
        this.games = games;
    }

    private ArrayList<GameData> games = new ArrayList<GameData>();

    public ArrayList<GameData> listGames(String authToken) {
        return null;
    }

    public Integer createGame() {
        Random rand = new Random();
        int newGameID = rand.nextInt(1000);
        games.add(new GameData(newGameID, null, null, null, new ChessGame()));
        return newGameID;
    }

    public GameData getGame(Integer ID) {
        for (GameData game : games) {
            if (game.getGameID() == ID) {
                return game;
            }
        }
        return null;
    }

    public boolean setTeamUser(GameData game, String username, String teamColor) {
        if (teamColor.equals("BLACK") && game.getBlackUsername() == null) {
            game.setBlackUsername(username);
            return true;
        }
        else if (teamColor.equals("WHITE") && game.getWhiteUsername() == null) {
            game.setWhiteUsername(username);
            return true;
        }
    }
}
