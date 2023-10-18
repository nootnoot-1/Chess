package models;

import chess.GameImpl;

/*
Model class for Games, contains all necessary information a server will need to know about a specific game
 */
public class Game {
    /*
    numerical identifier for specific game
     */
    private int gameID;
    /*
    Username for the player playing white in this specific game
     */
    private String whiteUsername;
    /*
    Username for the player playing black in this specific game
     */
    private String blackUsername;
    /*
    String identifier for specific game
     */
    private String gameName;
    /*
    Game implementation object
     */
    private GameImpl game;

    /*
    Constructor for game class
     */
    public Game() {}

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public GameImpl getGame() {
        return game;
    }

    public void setGame(GameImpl game) {
        this.game = game;
    }
}
