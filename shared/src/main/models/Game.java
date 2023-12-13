package models;

import chess.BoardImpl;
import chess.GameImpl;

import java.util.Objects;

/**
Model class for Games, contains all necessary information a server will need to know about a specific game
 */
public class Game {
    /**
    numerical identifier for specific game
     */
    private int gameID;
    private static int gameIDIT;
    /**
    Username for the player playing white in this specific game
     */
    private String whiteUsername;
    /**
    Username for the player playing black in this specific game
     */
    private String blackUsername;
    /**
    String identifier for specific game
     */
    private String gameName;
    /**
    Game implementation object
     */
    private GameImpl game;

    /**
    Constructor for game class
     */
    public Game(String name) {
        gameName = name;
        game = new GameImpl();
        BoardImpl board = new BoardImpl();
        board.resetBoard();
        game.setBoard(board);
        gameID = gameIDIT + 1;
        gameIDIT++;
    }

//    public Game() {
//        game = new GameImpl();
//        BoardImpl board = new BoardImpl();
//        board.resetBoard();
//        game.setBoard(board);
//        gameID = gameIDIT + 1;
//        gameIDIT++;
//    }

    public static int getGameIDIT() {
        return gameIDIT;
    }

    public static void setGameIDIT(int gameIDIT) {
        Game.gameIDIT = gameIDIT;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Game temp)) return false;
        boolean flag = true;
        if (!Objects.equals(temp.getGame().serialize(), this.getGame().serialize())) {
            flag = false;
        }
        if (temp.getGameID() != gameID) {
            flag = false;
        }
        if (!Objects.equals(temp.getBlackUsername(), blackUsername)) {
            flag = false;
        }
        if (!Objects.equals(temp.getWhiteUsername(), whiteUsername)) {
            flag = false;
        }
        if (!Objects.equals(temp.getGameName(), gameName)) {
            flag = false;
        }
        return flag;
    }
}
