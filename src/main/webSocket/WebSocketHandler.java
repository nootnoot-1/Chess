package webSocket;

import adapters.GameImplAdapter;
import chess.GameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.GameDAO;
import models.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        GameDAO gameDAO = new GameDAO();

        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        if (command.getCommandType() == UserGameCommand.CommandType.JOIN_OBSERVER) {
            ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            Game game = gameDAO.Find(command.getGameID());
            serverMessage.setGame(game);
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
            Gson gson = gsonbuilder.create();
            session.getRemote().sendString(gson.toJson(serverMessage));
        }
    }
}
