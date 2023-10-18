package services.request;

/*
Request class for the JoinGame service
 */
public class JoinGameRequest {
    /*
    string to determine what color the player will join as
     */
    private String playerColor;
    /*
    int to identify the game that is being joined
     */
    private int gameID;

    /*
    JoinGameRequest class constructor
     */
    public JoinGameRequest() {}

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
