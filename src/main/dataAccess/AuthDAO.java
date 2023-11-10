package dataAccess;

import models.AuthToken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
AuthToken Data Authentication Class. For connecting with the database for game information
 */
public class AuthDAO {
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "admin");
    }
    /**
    Hash Set of all authTokens stored in the server
    */
    public static Collection<AuthToken> authTokens = new HashSet<>();

    /**
    A method for inserting a new authToken into the database.
    @throws data access exception
    @param authToken to insert
    */
    public void Insert(AuthToken authToken) throws DataAccessException
    {
        if (!authTokens.contains(authToken)) {
            authTokens.add(authToken);
        }
    }

    /**
    A method for retrieving a specified authToken from the database by username.
    @throws data access exception
    @param username to find authToken for
    @return AuthToken that relates to given usernam
     */
    public AuthToken Find(String authToken) throws DataAccessException
    {
        for (AuthToken it : authTokens) {
            if (Objects.equals(it.getAuthToken(), authToken)) {
                return it;
            }
        }
        return null;

//        try(var conn = getConnection()) {
//            conn.setCatalog("chess");
//            var makeauthToken = """
//                    INSERT INTO authTokens (username) VALUES (?)
//                    """;
//
//
//
//        } catch (SQLException e) {
//            throw new DataAccessException(e.getMessage());
//        }

    }

    public AuthToken FindU(String username) throws DataAccessException {
        for (AuthToken it : authTokens) {
            if (Objects.equals(it.getUsername(), username)) {
                return it;
            }
        }
        return null;
    }

    /**
    A method for retrieving all authTokens from the database
    @throws data access exception
    @return all AuthTokens
     */
    public Collection<AuthToken> FindAll() throws DataAccessException
    {
        return authTokens;
    }

    /**
    A method for removing a authToken from the database
    @throws data access exception
    @param authToken to remove
     */
    public void Remove(String authToken) throws DataAccessException
    {
        for (AuthToken it : authTokens) {
            if (Objects.equals(it.getAuthToken(), authToken)) {
                authTokens.remove(it);
            }
        }
    }

    /**
    A method for clearing all data from the database
    @throws data access exception
     */
    public void Clear() throws DataAccessException
    {
        authTokens.clear();
    }
}
