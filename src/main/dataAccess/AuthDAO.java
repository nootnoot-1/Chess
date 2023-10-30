package dataAccess;

import models.AuthToken;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
AuthToken Data Authentication Class. For connecting with the database for game information
 */
public class AuthDAO {
    /**
    Hash Set of all authTokens stored in the server
    */
    public static Collection<AuthToken> authTokens = new HashSet<>();

    /**
    A method for inserting a new authToken into the database.
    @throws data access exception
    @param authToken to insert
    */
    public void Insert(AuthToken authToken) //throws DataAccessException
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
    public AuthToken Find(String authToken) //throws DataAccessException
    {
        for (AuthToken it : authTokens) {
            if (Objects.equals(it.getAuthToken(), authToken)) {
                return it;
            }
        }
        return null;
    }

    public AuthToken FindU(String username) {
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
    public Collection<AuthToken> FindAll() //throws DataAccessException
    {
        return authTokens;
    }

    /**
    A method for removing a authToken from the database
    @throws data access exception
    @param authToken to remove
     */
    public void Remove(String authToken) //throws DataAccessException
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
    public void Clear() //throws DataAccessException
    {
        authTokens.clear();
    }
}
