package dataAccess;

import models.Game;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

/**
Game Data Authentication Class. For connecting with the database for game information
 */
public class GameDAO {
    /**
    Hash Set of all games being held in the server
     */
    static Collection<Game> games = new HashSet<>();

    /**
    A method for inserting a new game into the database.
    @throws data access exception
    @param Game to insert
     */
    public void Insert(Game game) throws DataAccessException {}

    /**
    A method for retrieving a specified game from the database by gameID.
    @throws data access exception
    @param gameID of Game to find
    @return Game that relates to gameID
     */
    public Game Find(int gameID) throws DataAccessException {
        return null;
    }

    /**
    A method for retrieving all games from the database
    @throws data access exception
    @return Collection of all Games in database
     */
    public Collection<Game> FindAll() throws DataAccessException {
        return null;
    }

    /**
    A method/methods for claiming a spot in the game. The player's username is provied and should be saved as either the whitePlayer or blackPlayer in the database.
    @throws data access exception
    @param username of current user
    @param gameID of game to claim a spot in
    @param color of which team you will join
     */
    public void ClaimSpot(String username, int gameID, String color) throws DataAccessException {} //TODO what data type for color?

    /**
    A method for updating a chessGame in the database. It should replace the chessGame string corresponding to a given gameID with a new chessGame string.
    @throws data access exception
    @param gameID of game to update
    @param chessGame string game of what the updated game should be
     */
    public void UpdateGame(int gameID, String game) throws DataAccessException {} //TODO what is a new chessGame string?

    /**
    A method for removing a game from the database
    @throws data access exception
    @param gameID of which game to remove
     */
    public void Remove(Game gameID) throws DataAccessException {}

    /**
    A method for clearing all data from the database
    @throws data access exception
     */
    public void Clear() throws DataAccessException {}

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


