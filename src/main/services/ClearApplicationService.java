package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import services.request.ClearApplicationRequest;
import services.response.ClearApplicationResponse;

/**
Service for HTTP request to clear the application
 */
public class ClearApplicationService {

    /**
    clears the application
    @return ClearApplicationResponse an object containing all response data
     */
    public ClearApplicationResponse clearApplication() {
        ClearApplicationResponse clearApplicationResponse = new ClearApplicationResponse();

        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();

        try {
            userDAO.Clear();
            authDAO.Clear();
            gameDAO.Clear();
        } catch (DataAccessException e) {
            clearApplicationResponse.setMessage("Error: issue clearing database");
        }

        return clearApplicationResponse;
    }
}
