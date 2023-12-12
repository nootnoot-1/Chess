package dataAccess;

import chess.*;
import models.Game;
import models.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
Game Data Authentication Class. For connecting with the database for game information
 */
public class GameDAO {

    static Database db = new Database();
    //TODO: change gameID storage

    public void Insert(Game game) throws DataAccessException
    {
        try {
            FindGN(game.getGameName());
            throw new DataAccessException("gamename is taken");
        } catch (DataAccessException e) {
            if (Objects.equals(e.getMessage(), "gamename is taken")) {
                throw new DataAccessException("gamename is taken");
            }
        }

        var conn = db.getConnection();

        try {
            //conn.setCatalog("chess");
            var insert = conn.prepareStatement("INSERT INTO game (gamename, gamestring, whiteusername, blackusername, gameID) VALUES (?,?,?,?,?)");
            insert.setString(1, game.getGameName());
            insert.setString(2, game.getGame().serialize());
            insert.setString(3, game.getWhiteUsername());
            insert.setString(4, game.getBlackUsername());
            insert.setInt(5, game.getGameID());

            insert.executeUpdate();
            //MAYBE???

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
    }

    public Game Find(int gameID) throws DataAccessException
    {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, gamestring, gamename, whiteusername, blackusername FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var gamename = rs.getString("gamename");
                var whiteusername = rs.getString("whiteusername");
                var blackusername = rs.getString("blackusername");
                var gamestring = rs.getString("gamestring");


                //System.out.printf("gamestring: %s \ngamename: %s \ngameID: %d \nwhiteusername: %s \nblackusername: %s", gamestring, gamename, gameID, whiteusername, blackusername);

                Game game = new Game("temp");
                game.setGameID(gameID);
                game.setGame(deserialize(gamestring));
                game.setGameName(gamename);
                game.setWhiteUsername(whiteusername);
                game.setBlackUsername(blackusername);
                db.returnConnection(conn);
                return game;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("no game with that gameID");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public Game FindGN(String name) throws DataAccessException
    {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, gamestring, gamename, whiteusername, blackusername FROM game WHERE gamename=?")) {
            preparedStatement.setString(1, name);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var gamestring = rs.getString("gamestring");
                var gameID = rs.getInt("gameID");
                var whiteusername = rs.getString("whiteusername");
                var blackusername = rs.getString("blackusername");

                System.out.printf("gamestring: %s, \ngamename: %s, \ngameID: %d \nwhiteusername: %s, \nblackusername: %s", gamestring, name, gameID, whiteusername, blackusername);

                Game game = new Game("temp");
                game.setGameID(gameID);
                game.setGame(deserialize(gamestring));
                game.setGameName(name);
                game.setWhiteUsername(whiteusername);
                game.setBlackUsername(blackusername);
                db.returnConnection(conn);
                return game;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("no game with that gamename");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public Collection<Game> FindAll() throws DataAccessException
    {
        Collection<Game> games = new HashSet<>();
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, gamestring, gamename, whiteusername, blackusername FROM game")) {
            try (var rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    var gamestring = rs.getString("gamestring");
                    var gamename = rs.getString("gamename");
                    var gameID = rs.getInt("gameID");
                    var whiteusername = rs.getString("whiteusername");
                    var blackusername = rs.getString("blackusername");

                    System.out.printf("gamestring: %s, \ngamename: %s, \ngameID: %d \nwhiteusername: %s, \nblackusername: %s", gamestring, gamename, gameID, whiteusername, blackusername);

                    Game game = new Game("temp");
                    game.setGameID(gameID);
                    game.setGame(deserialize(gamestring));
                    game.setGameName(gamename);
                    game.setWhiteUsername(whiteusername);
                    game.setBlackUsername(blackusername);

                    games.add(game);
                }
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("no game with that gameID");
            //throw new DataAccessException(e.getMessage());
        }
        db.returnConnection(conn);
        return games;
    }


    public void ClaimSpot(String username, int gameID, ChessGame.TeamColor color) throws DataAccessException //TODO what data type for color?
    {
        Find(gameID);
        var conn = db.getConnection();

        if (color == ChessGame.TeamColor.WHITE) {
            String taken;
            try (var query = conn.prepareStatement("SELECT whiteusername FROM game WHERE gameID=?")) {
                query.setInt(1,gameID);
                var rs = query.executeQuery();
                rs.next();
                taken = rs.getString("whiteusername");
            } catch (SQLException e) {
                db.returnConnection(conn);
                throw new DataAccessException(e.getMessage());
            }
            if (taken==null) {
                try (var preparedStatement = conn.prepareStatement("UPDATE game SET whiteusername=? WHERE gameID=?")) {
                    preparedStatement.setString(1,username);
                    preparedStatement.setInt(2,gameID);

                    preparedStatement.executeUpdate();

                    db.returnConnection(conn);
                } catch (SQLException e) {
                    db.returnConnection(conn);
                    throw new DataAccessException(e.getMessage());
                }
            } else {
                db.returnConnection(conn);
                throw new DataAccessException("Error: already taken");
            }
        } else {
            String taken;
            try (var query = conn.prepareStatement("SELECT blackusername FROM game WHERE gameID=?")) {
                query.setInt(1,gameID);
                var rs = query.executeQuery();
                rs.next();
                taken = rs.getString("blackusername");
            } catch (SQLException e) {
                db.returnConnection(conn);
                throw new DataAccessException(e.getMessage());
            }
            if (taken==null) {
                try (var preparedStatement = conn.prepareStatement("UPDATE game SET blackusername=? WHERE gameID=?")) {
                    preparedStatement.setString(1,username);
                    preparedStatement.setInt(2,gameID);

                    preparedStatement.executeUpdate();

                    db.returnConnection(conn);
                } catch (SQLException e) {
                    db.returnConnection(conn);
                    throw new DataAccessException(e.getMessage());
                }
            } else {
                db.returnConnection(conn);
                throw new DataAccessException("Error: already taken");
            }
        }


//        Game game = Find(gameID);
//
//        if (color == ChessGame.TeamColor.BLACK) {
//            if (game.getBlackUsername() != null) {
//                throw new DataAccessException("Black spot already claimed");
//            }
//            game.setBlackUsername(username);
//        }
//        if (color == ChessGame.TeamColor.WHITE) {
//            if (game.getWhiteUsername() != null) {
//                throw new DataAccessException("White spot already claimed");
//            }
//            game.setWhiteUsername(username);
//        }
    }

    public void UpdateGame(int gameID, Game game) throws DataAccessException //TODO what is a new chessGame string?
    {
        Find(gameID);
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("UPDATE game SET gamestring=? WHERE gameID=?")) {
            preparedStatement.setString(1,game.getGame().serialize());
            preparedStatement.setInt(2,gameID);

            preparedStatement.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }

    }

    public void Remove(int gameID) throws DataAccessException
    {
        Find(gameID);
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1,gameID);

            preparedStatement.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("failed to remove game");
        }
    }

    public void Clear() throws DataAccessException
    {
        Game.setGameIDIT(0);

        var conn = db.getConnection();
        try (var dropgame = conn.prepareStatement("DROP TABLE game;")) {
            dropgame.executeUpdate();
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }

        var createGAMETable = """
                    CREATE TABLE IF NOT EXISTS GAME (
                    ID INT NOT NULL AUTO_INCREMENT,
                    gameID INT NOT NULL,
                    gamename VARCHAR(255) NOT NULL,
                    whiteusername VARCHAR(255),
                    blackusername VARCHAR(255),
                    gamestring VARCHAR(255) NOT NULL,
                    PRIMARY KEY (ID)
                    )""";

        try (var createTableStatement = conn.prepareStatement(createGAMETable)) {
            createTableStatement.executeUpdate();
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
        db.returnConnection(conn);
    }


    public GameImpl deserialize(String gamestring) {
        GameImpl game = new GameImpl();
        BoardImpl board = new BoardImpl();

        if (gamestring.charAt(0) == 'W') {
            game.setTeamTurn(ChessGame.TeamColor.WHITE);
        } else {game.setTeamTurn(ChessGame.TeamColor.BLACK);}

        for (int i=1; i<gamestring.length(); ++i) {
            PositionImpl position = new PositionImpl();
            position.setRow(gamestring.charAt(i)-48); ++i;
            position.setColumn(gamestring.charAt(i)-48); ++i;
            ChessPiece piece = null;
            if (gamestring.charAt(i) == 'K') {
                piece = new KingImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'Q') {
                piece = new QueenImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'B') {
                piece = new BishopImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'N') {
                piece = new KnightImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'R') {
                piece = new RookImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'P') {
                piece = new PawnImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'k') {
                piece = new KingImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'q') {
                piece = new QueenImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'b') {
                piece = new BishopImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'n') {
                piece = new KnightImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'r') {
                piece = new RookImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'p') {
                piece = new PawnImpl(ChessGame.TeamColor.BLACK);
            }
            board.addPiece(position,piece);
        }
        game.setBoard(board);
        return game;
    }
}


