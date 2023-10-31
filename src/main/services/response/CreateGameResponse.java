package services.response;

/**
Response class for the Create Game service
 */
public class CreateGameResponse {
    /**
    message for any errors that occur
     */
    private String message;

    /**
    authToken to verify user
     */
    private String authToken;

    /**
    int used to indentify specific game
     */
    private int gameID;

    /**
    Constructor for the CreateGameResponse class
     */
    public CreateGameResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
