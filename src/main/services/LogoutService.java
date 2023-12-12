package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import services.response.LogoutResponse;

/**
Service for HTTP request to log out
 */
public class LogoutService {

    /**
    logs user out of the service
    @return LogoutResponse an object containing all response data
     */
    public LogoutResponse logout(String authToken) {
        LogoutResponse logoutResponse = new LogoutResponse();
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            authDAO.Find(authToken);
        } catch (DataAccessException e) {
            logoutResponse.setMessage("Error: unauthorized");
            return logoutResponse;
        }

        try {
            authDAO.Remove(authToken);
        } catch (DataAccessException e) {
            logoutResponse.setMessage(e.getMessage());
            return logoutResponse;
        }

        logoutResponse.setAuthToken(authToken);

        return logoutResponse;
    }
}
