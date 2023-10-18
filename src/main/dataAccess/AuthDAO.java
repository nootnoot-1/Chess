package dataAccess;

import models.AuthToken;

import java.util.Collection;
import java.util.HashSet;

/*
AuthToken Data Authentication Class. For connecting with the database for game information
 */
public class AuthDAO {
    /*
    Hash Set of all authTokens stored in the server
    */
    Collection<AuthToken> authTokens = new HashSet<>();

    /*
    A method for inserting a new authToken into the database.
    @throws data access exception
    @param authToken to insert
    */
    public void Insert(AuthToken authToken) throws DataAccessException {}

    /*
    A method for retrieving a specified authToken from the database by username.
    @throws data access exception
    @param username to find authToken for
    @return AuthToken that relates to given usernam
     */
    public AuthToken Find(String username) throws DataAccessException {
        return null;
    }

    /*
    A method for retrieving all authTokens from the database
    @throws data access exception
    @return all AuthTokens
     */
    public Collection<AuthToken> FindAll() throws DataAccessException {
        return null;
    }

    /*
    A method for removing a authToken from the database
    @throws data access exception
    @param authToken to remove
     */
    public void Remove(AuthToken authToken) throws DataAccessException {}

    /*
    A method for clearing all data from the database
    @throws data access exception
     */
    public void Clear() throws DataAccessException {}
}
