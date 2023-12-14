package response;

import models.Game;

/**
Response class for the JoinGame service
 */
public class JoinGameResponse {
    /**
    message for any errors that occur
     */
    private String message;
    /**
    string to determine what color the player will join as
     */
    private String playerColor;
    /**
    int to identify the game that is being joined
     */
    private String gameID;

    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
    JoinGameResponse class constructor
     */
    public JoinGameResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
