package Requests;

public record JoinGameRequest(String playerColor, int gameID) implements Request {
}
