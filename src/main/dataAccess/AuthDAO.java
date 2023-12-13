package dataAccess;

import models.AuthToken;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
AuthToken Data Authentication Class. For connecting with the database for game information
 */
public class AuthDAO {
    static Database db = new Database();

    public AuthDAO() {}

    public void Insert(AuthToken authToken) throws DataAccessException
    {
        try {
            Find(authToken.getAuthToken());
            throw new DataAccessException("authToken is taken");
        } catch (DataAccessException e) {
            if (Objects.equals(e.getMessage(), "authToken is taken")) {
                throw new DataAccessException("authToken is taken");
            }
        }

        var conn = db.getConnection();

        try {
            //conn.setCatalog("chess");
            var insert = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?,?)");
            insert.setString(1, authToken.getAuthToken());
            insert.setString(2, authToken.getUsername());

            insert.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthToken Find(String authToken) throws DataAccessException
    {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM auth WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var token = rs.getString("authToken");
                var username = rs.getString("username");

                System.out.printf("authToken: %s, username: %s \n", token, username);

                AuthToken reToken = new AuthToken();
                reToken.setAuthToken(token);
                reToken.setUsername(username);
                db.returnConnection(conn);
                return reToken;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("AUTHTOKEN FIND ERROR");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public AuthToken FindU(String uname) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM auth WHERE username=?")) {
            preparedStatement.setString(1, uname);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var token = rs.getString("authToken");
                var username = rs.getString("username");

                System.out.printf("authToken: %s, username: %s \n", token, username);

                AuthToken reToken = new AuthToken();
                reToken.setAuthToken(token);
                reToken.setUsername(username);
                db.returnConnection(conn);
                return reToken;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("AUTHTOKEN FINDU ERROR");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public String FindUsername(String authToken) throws DataAccessException
    {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var username = rs.getString("username");

                System.out.printf("username: %s \n", username);

                db.returnConnection(conn);
                return username;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("AUTHTOKEN FINDUSERNAME ERROR");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public Collection<AuthToken> FindAll() throws DataAccessException
    {
        Collection<AuthToken> tokens = new HashSet<>();
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM auth")) {
            try (var rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    var username = rs.getString("username");
                    var token = rs.getString("authToken");

                    System.out.printf("authToken: %s, username: %s \n", token, username);

                    AuthToken reToken = new AuthToken();
                    reToken.setAuthToken(token);
                    reToken.setUsername(username);

                    tokens.add(reToken);
                }
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("empty database?");
        }
        db.returnConnection(conn);
        return tokens;
    }

    public void Remove(String authToken) throws DataAccessException
    {
        Find(authToken);
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE authToken=?")) {
            preparedStatement.setString(1,authToken);

            preparedStatement.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("failed to remove authToken");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public void Clear() throws DataAccessException
    {
        var conn = db.getConnection();
        try (var dropauth = conn.prepareStatement("DROP TABLE auth;")) {
            dropauth.executeUpdate();
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }

        var createAUTHTable = """
                    CREATE TABLE IF NOT EXISTS AUTH (
                    id INT NOT NULL AUTO_INCREMENT,
                    authToken VARCHAR(255) NOT NULL,
                    username VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                    )""";

        try (var createTableStatement = conn.prepareStatement(createAUTHTable)) {
            createTableStatement.executeUpdate();
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
        db.returnConnection(conn);
    }
}
