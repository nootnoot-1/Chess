package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import models.Game;
import services.request.JoinGameRequest;
import services.response.JoinGameResponse;

import java.util.Objects;

/**
Service for HTTP request to join a game
 */
public class JoinGameService {

    /**
    joins a game
    @param JoinGameRequest r an object containing all request data
    @return JoinGameResponse an object containing all response data
     */
    public JoinGameResponse joinGame(JoinGameRequest r, String authToken) {
        JoinGameResponse joinGameResponse = new JoinGameResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        if (authDAO.Find(authToken) == null) {
            joinGameResponse.setMessage("Error: unauthorized");
            return joinGameResponse;
        }
        if (r.getGameID() < 999) {
            joinGameResponse.setMessage("Error: bad request");
            return joinGameResponse;
        }

        Game game = gameDAO.Find(r.getGameID());

        if (Objects.equals(r.getPlayerColor(), "BLACK")) {
            if (game.getBlackUsername() != null) {
                joinGameResponse.setMessage("Error: already taken");
                return joinGameResponse;
            }
            game.setBlackUsername(authDAO.Find(authToken).getUsername());
        }
        if (Objects.equals(r.getPlayerColor(), "WHITE")) {
            if (game.getWhiteUsername() != null) {
                joinGameResponse.setMessage("Error: already taken");
                return joinGameResponse;
            }
            game.setWhiteUsername(authDAO.Find(authToken).getUsername());
        }

        return joinGameResponse;
    }

}
