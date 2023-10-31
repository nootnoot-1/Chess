package dataAccess;

import chess.ChessGame;
import models.Game;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
Game Data Authentication Class. For connecting with the database for game information
 */
public class GameDAO {
    /**
    Hash Set of all games being held in the server
     */
    public static Collection<Game> games = new HashSet<>();

    /**
    A method for inserting a new game into the database.
    @throws data access exception
    @param Game to insert
     */
    public void Insert(Game game) //throws DataAccessException
    {
        if (!games.contains(game)) {
            games.add(game);
        }
    }

    /**
    A method for retrieving a specified game from the database by gameID.
    @throws data access exception
    @param gameID of Game to find
    @return Game that relates to gameID
     */
    public Game Find(int gameID) //throws DataAccessException
    {
        for (Game it : games) {
            if (it.getGameID() == gameID) {
                return it;
            }
        }
        return null;
    }

    public Game FindGN(String name) //throws DataAccessException
    {
        for (Game it : games) {
            if (Objects.equals(it.getGameName(), name)) {
                return it;
            }
        }
        return null;
    }

    /**
    A method for retrieving all games from the database
    @throws data access exception
    @return Collection of all Games in database
     */
    public Collection<Game> FindAll() //throws DataAccessException
    {
        return games;
    }

    /**
    A method/methods for claiming a spot in the game. The player's username is provied and should be saved as either the whitePlayer or blackPlayer in the database.
    @throws data access exception
    @param username of current user
    @param gameID of game to claim a spot in
    @param color of which team you will join
     */
    public void ClaimSpot(String username, int gameID, ChessGame.TeamColor color) throws DataAccessException //TODO what data type for color?
    {
        Game game = Find(gameID);

        if (color == ChessGame.TeamColor.BLACK) {
            if (game.getBlackUsername() != null) {
                throw new DataAccessException("Black spot already claimed");
            }
            game.setBlackUsername(username);
        }
        if (color == ChessGame.TeamColor.WHITE) {
            if (game.getWhiteUsername() != null) {
                throw new DataAccessException("White spot already claimed");
            }
            game.setWhiteUsername(username);
        }
    }

    /**
    A method for updating a chessGame in the database. It should replace the chessGame string corresponding to a given gameID with a new chessGame string.
    @throws data access exception
    @param gameID of game to update
    @param chessGame string game of what the updated game should be
     */
    public void UpdateGame(int gameID, Game game) //throws DataAccessException //TODO what is a new chessGame string?
    {
        for (Game it : games) {
            if (it.getGameID() == gameID) {
                it = game;
            }
        }
    }

    /**
    A method for removing a game from the database
    @throws data access exception
    @param gameID of which game to remove
     */
    public void Remove(int gameID) //throws DataAccessException
    {
        for (Game it : games) {
            if (it.getGameID() == gameID) {
                games.remove(it);
            }
        }
    }

    /**
    A method for clearing all data from the database
    @throws data access exception
     */
    public void Clear() //throws DataAccessException
    {
        games.clear();
    }

//    public void makeSQLCalls() throws SQLException {
//        try (var conn = getConnection() {
//
//        })
//    }
//
//    Connection getConnection() throws SQLException {
//        retrun DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306",
//
//        )
//    }

//    void configureDatabase() throws SQLException {
//        try (Connection conn = getConnection()) {
//            try (var createDBStatement = conn.prepareStatement(
//                    "CREATE DATABASE IF NOT EXISTS pet_store")) {
//                createDBStatement.executeUpdate();
//            }
//
//            conn.setCatalog("pet_store");
//
//            var createPetTable = """
//                    CREATE TABLE IF NOT EXISTS pet (
//                    id INT NOT NULL AUTO INCREMENT,
//                    name VARCHAR(255) NOT NULL,
//                    type VARCHAR(255) NOT NULL,
//                    PRIMARY KEY (id) )
//                    """;
//
//            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
//                createTableStatement.executeUpdate();
//            }
//        }
//    }
}


