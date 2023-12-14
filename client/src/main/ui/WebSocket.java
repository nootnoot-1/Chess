package ui;

import javax.websocket.*;
import java.net.URI;
import java.util.Objects;
import java.util.Scanner;

public class WebSocket extends Endpoint{

    public static void run() throws Exception{
        var ws = new WebSocket();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message you want to echo");
        while (true) { // this is where we do the work?
            System.out.print("[GAMIN] >>> ");
            String temp = scanner.nextLine();
            String[] input = parseInput(temp);

            if (Objects.equals(input[0], "quit") && input.length == 1) {
                break;
            }

            else if (Objects.equals(input[0], "help") && input.length == 1) {
                System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
                System.out.println("login <USERNAME> <PASSWORD> - to play chess");
                System.out.println("quit - playing chess");
                System.out.println("help - with possible commands");
            }
            ws.send(scanner.nextLine());
        }
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public Session session;

    public WebSocket() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message + "LALALALA");
            }
        });
    }


    private static String[] parseInput(String input) {
        String[] words = input.split("\\s+");
        return words;
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {System.out.println("WEBSOCKET FLAG");}
}
