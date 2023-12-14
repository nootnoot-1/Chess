package webSocket;

import adapters.GameImplAdapter;
import adapters.MoveAdapter;
import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {
    ConcurrentHashMap<String, Contact> contacts = new ConcurrentHashMap<>();
    GameDAO gameDAO = new GameDAO();
    AuthDAO authDAO = new AuthDAO();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
        gsonbuilder.registerTypeAdapter(ChessMove.class, new MoveAdapter());
        Gson gson = gsonbuilder.create();
        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        int gameID = command.getGameID();
        String authToken = command.getAuthString();
        ChessGame.TeamColor teamColor = command.getPlayerColor();
        ChessMove move = command.getMove();

        //CHECK AUTH TOKEN
        try {
            authDAO.Find(command.getAuthString());
        } catch (DataAccessException e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        if (command.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
            joinObserver(authToken, session, gameID);
        } else if (command.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER) {
            joinPlayer(authToken, session, gameID, teamColor);
        } else if (command.getCommandType() == UserGameCommand.CommandType.LEAVE) {
            leave(authToken, session, gameID, teamColor);
        } else if (command.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
            makeMove(authToken, session, gameID, move);
        }
    }

    private void joinObserver(String authToken, Session session, int gameID) throws Exception {
        String username = authDAO.FindUsername(authToken);

        // CHECK FOR GAME ID IN DATABASE
        try {
            gameDAO.Find(gameID);
        } catch (DataAccessException e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        Contact contact = new Contact(session, gameID, username);
        contacts.put(username,contact);

        ServerMessage loadgame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        Game game = gameDAO.Find(gameID);
        loadgame.setGame(game);

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
        Gson gson = gsonbuilder.create();
        session.getRemote().sendString(gson.toJson(loadgame));

        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(username + " has joined as an observer");

        for (Map.Entry<String, Contact> set : contacts.entrySet()) {
            if (!Objects.equals(set.getKey(), username)) {
                set.getValue().send(gson.toJson(notification));
            }
        }
    }
    private void joinPlayer(String authToken, Session session, int gameID, ChessGame.TeamColor teamColor) throws Exception {
        String username = authDAO.FindUsername(authToken);

        // CHECK FOR GAME ID IN DATABASE
        try {
            gameDAO.Find(gameID);
        } catch (DataAccessException e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        // CHECK FOR WRONG SPOT
        if ((teamColor == ChessGame.TeamColor.WHITE && !Objects.equals(gameDAO.Find(gameID).getWhiteUsername(), username)) || (teamColor == ChessGame.TeamColor.BLACK && !Objects.equals(gameDAO.Find(gameID).getBlackUsername(), username))) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("spot is already claimed");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        Contact contact = new Contact(session, gameID, username);
        contacts.put(username, contact);

        ServerMessage loadgame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        Game game = gameDAO.Find(gameID);
        loadgame.setGame(game);

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
        Gson gson = gsonbuilder.create();
        session.getRemote().sendString(gson.toJson(loadgame));

        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(username + " has joined as a player");

        for (Map.Entry<String, Contact> set : contacts.entrySet()) {
            if (!Objects.equals(set.getKey(), username)) {
                set.getValue().send(gson.toJson(notification));
            }
        }
    }
    private void leave(String authToken, Session session, int gameID, ChessGame.TeamColor teamColor) throws Exception {
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String username = authDAO.FindUsername(authToken);

        if (teamColor == ChessGame.TeamColor.WHITE) {
            gameDAO.LeaveSpot(gameID, ChessGame.TeamColor.WHITE);
            notification.setMessage(username + " has left the game");
            contacts.remove(username);
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            gameDAO.LeaveSpot(gameID, ChessGame.TeamColor.BLACK);
            notification.setMessage(username + " has left the game");
            contacts.remove(username);
        } else {
            notification.setMessage(username + " has stopped observing the game");
            contacts.remove(username);
        }
        for (Map.Entry<String, Contact> set : contacts.entrySet()) {
            if (!Objects.equals(set.getKey(), username)) {
                set.getValue().send(new Gson().toJson(notification));
            }
        }
    }
    private void makeMove(String authToken, Session session, int gameID, ChessMove move) throws Exception {
        String username = authDAO.FindUsername(authToken);

        // CHECK FOR GAME ID IN DATABASE
        try {
            gameDAO.Find(gameID);
        } catch (DataAccessException e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        //MAKE GSON FOR GAME
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
        gsonbuilder.registerTypeAdapter(ChessMove.class, new MoveAdapter());
        Gson gson = gsonbuilder.create();

        Game game = gameDAO.Find(gameID);
        game.getGame().makeMoveNEW(move);

        ServerMessage loadgame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        loadgame.setGame(game);
        session.getRemote().sendString(gson.toJson(loadgame));

        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(username + " made a move");

        for (Map.Entry<String, Contact> set : contacts.entrySet()) {
            if (!Objects.equals(set.getKey(), username)) {
                set.getValue().send(new Gson().toJson(notification));
            }
        }
    }
}
