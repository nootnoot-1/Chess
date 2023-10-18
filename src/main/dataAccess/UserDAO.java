package dataAccess;

import models.User;

import java.util.Collection;
import java.util.HashSet;

/*
User Data Authentication Class. For connecting with the database for user information
 */
public class UserDAO {

    /*
    Hash Set of all users stored in the server
     */
    Collection<User> users = new HashSet<>();

    /*
    A method for inserting a new user into the database.
    @throws data access exception
    @param user to insert into database
    */
    public void Insert(User user) throws DataAccessException {}

    /*
    A method for retrieving a specified user from the database by username.
    @throws data access exception
    @param username of User to find
    @return User that relates to given username
     */
    public User Find(String username) throws DataAccessException {
        return null;
    }

    /*
    A method for retrieving all users from the database
    @throws data access exception
    @return Collection of all Users in the database
     */
    public Collection<User> FindAll() throws DataAccessException {
        return null;
    }

    /*
    A method for removing a user from the database
    @throws data access exception
    @param user to be removed from the database
     */
    public void Remove(User user) throws DataAccessException {}

    /*
    A method for clearing all data from the database
    @throws data access exception
     */
    public void Clear() throws DataAccessException {}
}
