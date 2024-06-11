package requests;

public record JoinGameRequest(String playerColor, int gameID) implements Request {
}
