package webSocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Contact {
    Session session;
    int gameID;
    String username;

    public Contact(Session session, int gameID, String username) {
        this.session = session;
        this.gameID = gameID;
        this.username = username;
    }

    public void send(String message) throws IOException {
        session.getRemote().sendString(message);
    }
}
