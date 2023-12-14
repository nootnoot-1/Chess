package webSocket;

import adapters.GameImplAdapter;
import chess.ChessGame;
import chess.GameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.AuthDAO;
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
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    Collection<Contact> contacts = new HashSet<>();
    GameDAO gameDAO = new GameDAO();
    AuthDAO authDAO = new AuthDAO();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        if (command.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
            String username = authDAO.Find(command.getAuthString()).getUsername();
            Contact contact = new Contact(session, command.getGameID(), username);
            contacts.add(contact);

            ServerMessage loadgame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            Game game = gameDAO.Find(command.getGameID());
            loadgame.setGame(game);

            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
            Gson gson = gsonbuilder.create();
            session.getRemote().sendString(gson.toJson(loadgame));

            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            notification.setMessage(username + " has joined as an observer");

            for (Contact it : contacts) {
                if (!Objects.equals(it.username, username)) {
                    it.send(gson.toJson(notification));
                }
            }
        }

        else if (command.getCommandType() == UserGameCommand.CommandType.JOIN_PLAYER) {
            String username = authDAO.Find(command.getAuthString()).getUsername();
            if ((command.getPlayerColor() == ChessGame.TeamColor.WHITE && !Objects.equals(gameDAO.Find(command.getGameID()).getWhiteUsername(), username)) || (command.getPlayerColor() == ChessGame.TeamColor.BLACK && !Objects.equals(gameDAO.Find(command.getGameID()).getBlackUsername(), username))) {
                ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage("spot is already claimed");
                session.getRemote().sendString(new Gson().toJson(error));
            } else if (gameDAO.Find(command.getGameID())==null) {

            }

            else {
                Contact contact = new Contact(session, command.getGameID(), username);
                contacts.add(contact);

                ServerMessage loadgame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                Game game = gameDAO.Find(command.getGameID());
                loadgame.setGame(game);

                GsonBuilder gsonbuilder = new GsonBuilder();
                gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
                Gson gson = gsonbuilder.create();
                session.getRemote().sendString(gson.toJson(loadgame));

                ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                notification.setMessage(username + " has joined as a player");

                for (Contact it : contacts) {
                    if (!Objects.equals(it.username, username)) {
                        it.send(gson.toJson(notification));
                    }
                }
            }
        }
    }
}
