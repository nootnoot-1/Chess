package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import services.request.RegisterRequest;
import services.response.RegisterResponse;

/**
Service for HTTP request to register a user to the database
 */
public class RegisterService {

    /**
    registers a user into the server database
    @param RegisterRequest r an object containing all request data
    @return RegisterResponse an object containing all response data
     */
    public RegisterResponse register(RegisterRequest r) throws DataAccessException {
        RegisterResponse registerResponse = new RegisterResponse();
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();

        //take care of any errors
        if (r.getPassword()==null || r.getUsername()==null || r.getEmail()==null) {
            registerResponse.setMessage("Error: bad request");
            return registerResponse;
        }
        if (userDAO.Find(r.getUsername())!=null) {
            registerResponse.setMessage("Error: already taken");
            return registerResponse;
        }

        //update database
        AuthToken authToken = new AuthToken();
        authToken.setUsername(r.getUsername());
        authDAO.Insert(authToken);

        User user = new User();
        user.setUsername(r.getUsername());
        user.setPassword(r.getPassword());
        user.setEmail(r.getEmail());

        userDAO.Insert(user);

        //fill in response
        registerResponse.setUsername(r.getUsername());
        registerResponse.setAuthToken(authToken.getAuthToken());

        //String s = "username: " + registerResponse.getUsername() + ", authToken: " + authToken.getAuthToken(); //have constructor in response that takes in

        return registerResponse;
    }

}


