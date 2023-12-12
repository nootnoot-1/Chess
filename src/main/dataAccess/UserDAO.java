package dataAccess;

import models.User;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
User Data Authentication Class. For connecting with the database for user information
 */
public class UserDAO {

    static Database db = new Database();

    public void Insert(User user) throws DataAccessException
    {
        try {
            Find(user.getUsername());
            throw new DataAccessException("username is taken");
        } catch (DataAccessException e) {
            if (Objects.equals(e.getMessage(), "username is taken")) {
                throw new DataAccessException("username is taken");
            }
        }

        var conn = db.getConnection();

        try {
            //conn.setCatalog("chess");
            var insert = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES (?,?,?)");
            insert.setString(1, user.getUsername());
            insert.setString(2, user.getPassword());
            insert.setString(3, user.getEmail());

            insert.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
    }

    public User Find(String uname) throws DataAccessException
    {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE username=?")) {
            preparedStatement.setString(1, uname);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var username = rs.getString("username");
                var password = rs.getString("password");
                var email = rs.getString("email");

                System.out.printf("username: %s, password: %s, email: %s", username, password, email);

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                db.returnConnection(conn);
                return user;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("ERROR: " +
                    "no user registered with that username");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public Collection<User> FindAll() throws DataAccessException
    {
        Collection<User> users = new HashSet<>();
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user")) {
            try (var rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    var username = rs.getString("username");
                    var password = rs.getString("password");
                    var email = rs.getString("email");

                    System.out.printf("username: %s, password: %s, email: %s", username, password, email);

                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("empty database?");
        }
        db.returnConnection(conn);
        return users;
    }

    public void Remove(User user) throws DataAccessException
    {
        Find(user.getUsername());
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE FROM user WHERE username=?")) {
            preparedStatement.setString(1,user.getUsername());

            preparedStatement.executeUpdate();

            db.returnConnection(conn);
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException("failed to remove user");
            //throw new DataAccessException(e.getMessage());
        }
    }

    public void Clear() throws DataAccessException
    {
        var conn = db.getConnection();
        try (var dropuser = conn.prepareStatement("DROP TABLE user;")) {
            dropuser.executeUpdate();
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }

        var createUSERTable = """
                    CREATE TABLE IF NOT EXISTS USER (
                    id INT NOT NULL AUTO_INCREMENT,
                    username VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                    )""";

        try (var createTableStatement = conn.prepareStatement(createUSERTable)) {
            createTableStatement.executeUpdate();
        } catch (SQLException e) {
            db.returnConnection(conn);
            throw new DataAccessException(e.getMessage());
        }
        db.returnConnection(conn);
    }




}
