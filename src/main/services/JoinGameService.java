package services;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import request.JoinGameRequest;
import response.JoinGameResponse;

/**
Service for HTTP request to join a game
 */
public class JoinGameService {

    /**
    joins a game
    @return JoinGameResponse an object containing all response data
     */
    public JoinGameResponse joinGame(JoinGameRequest r, String authToken) {
        JoinGameResponse joinGameResponse = new JoinGameResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            authDAO.Find(authToken);
        } catch (DataAccessException e) {
            joinGameResponse.setMessage("Error: unauthorized");
            return joinGameResponse;
        }
        if (r.getGameID() < 1) {
            joinGameResponse.setMessage("Error: bad request");
            return joinGameResponse;
        }

        if (r.getPlayerColor() != null) {
            try {
                gameDAO.ClaimSpot(authDAO.FindUsername(authToken), r.getGameID(), ChessGame.TeamColor.valueOf(r.getPlayerColor()));
            } catch (DataAccessException e) {
                joinGameResponse.setMessage(e.getMessage());
                return joinGameResponse;
            }
        }

//        try {
//            Game game = gameDAO.Find(r.getGameID());
//            if (Objects.equals(r.getPlayerColor(), "BLACK")) {
//                if (game.getBlackUsername() != null) {
//                    joinGameResponse.setMessage("Error: already taken");
//                    return joinGameResponse;
//                }
//                game.setBlackUsername(authDAO.Find(authToken).getUsername());
//            }
//            if (Objects.equals(r.getPlayerColor(), "WHITE")) {
//                if (game.getWhiteUsername() != null) {
//                    joinGameResponse.setMessage("Error: already taken");
//                    return joinGameResponse;
//                }
//                game.setWhiteUsername(authDAO.Find(authToken).getUsername());
//            }
//        } catch (DataAccessException e) {
//            joinGameResponse.setMessage(e.getMessage());
//            return joinGameResponse;
//        }

        return joinGameResponse;
    }

}
