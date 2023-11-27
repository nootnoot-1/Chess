package dataAccess;

import chess.ChessGame;
import models.Game;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
Game Data Authentication Class. For connecting with the database for game information
 */
public class GameDAO {

    static Database db = new Database();
    public static Collection<Game> games = new HashSet<>();

    public void Insert(Game game) throws DataAccessException
    {
        var conn = db.getConnection();

        try {
            //conn.setCatalog("chess");
            var insert = conn.prepareStatement("INSERT INTO game (gamename, game) VALUES (?,?)");
            insert.setString(1, game.getGameName());
            insert.setString(2, game.getGame().toString());

            insert.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
    }

    public Game Find(int gameID) throws DataAccessException
    {
        for (Game it : games) {
            if (it.getGameID() == gameID) {
                return it;
            }
        }
        return null;
    }

    public Game FindGN(String name) throws DataAccessException
    {
        for (Game it : games) {
            if (Objects.equals(it.getGameName(), name)) {
                return it;
            }
        }
        return null;
    }

    public Collection<Game> FindAll() throws DataAccessException
    {
        return games;
    }


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

    public void UpdateGame(int gameID, Game game) throws DataAccessException //TODO what is a new chessGame string?
    {
        for (Game it : games) {
            if (it.getGameID() == gameID) {
                it = game;
            }
        }
    }

    public void Remove(int gameID) throws DataAccessException
    {
        for (Game it : games) {
            if (it.getGameID() == gameID) {
                games.remove(it);
            }
        }
    }

    public void Clear() throws DataAccessException
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


