package ui;

import adapters.GameImplAdapter;
import chess.ChessGame;
import chess.GameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Game;
import models.User;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.net.URI;
import java.util.Objects;
import java.util.Scanner;

public class WebSocket extends Endpoint{
    private static Game currentGame;
    private static ChessGame.TeamColor currentTurn;
    private static ChessGame.TeamColor teamColor;
    private final Printer printer = new Printer();

    public static void run(String teamcolor, String authToken, int gameID) throws Exception{
        var ws = new WebSocket();
        Scanner scanner = new Scanner(System.in);

        if (Objects.equals(teamcolor, "WHITE")) {
            teamColor = ChessGame.TeamColor.WHITE;
            gameViewClient(ws,scanner,authToken,gameID);
        } else if (Objects.equals(teamcolor, "BLACK")) {
            teamColor = ChessGame.TeamColor.BLACK;
            gameViewClient(ws,scanner,authToken,gameID);
        } else {
            observeClient(ws,scanner,authToken,gameID);
        }
    }

    private static void gameViewClient(WebSocket ws, Scanner scanner, String authToken, int gameID) throws Exception {
        UserGameCommand tempcommand = new UserGameCommand(authToken, gameID, UserGameCommand.CommandType.JOIN_PLAYER);
        tempcommand.setPlayerColor(teamColor);
        String tempjson = new Gson().toJson(tempcommand);
        ws.send(tempjson);
        boolean flag = false;
        while (true) { // this is where we do the work?
            if (flag) {
                System.out.print("[GAMEVIEW] >>> ");
            }
            flag = true;
            String temp = scanner.nextLine();
            String[] input = parseInput(temp);

            if (Objects.equals(input[0], "leave") && input.length == 1) {
                UserGameCommand command = new UserGameCommand(authToken, gameID, UserGameCommand.CommandType.LEAVE);
                command.setPlayerColor(teamColor);
                String json = new Gson().toJson(command);
                ws.send(json);
                return;
            }

            else if (Objects.equals(input[0], "help") && input.length == 1) {
                System.out.println("move <BOTH COORDINATES> - to make move EX: b7b6");
                System.out.println("highlight <COORDINATES>- specific piece's legal moves EX: b7"); //local op
                System.out.println("redraw - game board");
                System.out.println("leave - game view");
                System.out.println("resign - from game");
                System.out.println("help - with possible commands");
            }
            else if (Objects.equals(input[0], "redraw") && input.length == 1) {
                ws.send(tempjson);
                flag = false;
            } else {
                System.out.println("invalid input, type \"help\" for what you can do <3");
            }
            //ws.send(scanner.nextLine());
        }
    }

    private static void observeClient(WebSocket ws, Scanner scanner, String authToken, int gameID) throws Exception {
        UserGameCommand tempcommand = new UserGameCommand(authToken, gameID, UserGameCommand.CommandType.JOIN_OBSERVER);
        String tempjson = new Gson().toJson(tempcommand);
        ws.send(tempjson);
        boolean flag = false;
        while (true) { // this is where we do the work?
            if (flag) {
                System.out.print("[GAMEVIEW] >>> ");
            }
            flag = true;
            String temp = scanner.nextLine();
            String[] input = parseInput(temp);

            if (Objects.equals(input[0], "leave") && input.length == 1) {
                UserGameCommand command = new UserGameCommand(authToken, gameID, UserGameCommand.CommandType.LEAVE);
                command.setPlayerColor(teamColor);
                String json = new Gson().toJson(command);
                ws.send(json);
                return;
            }

            else if (Objects.equals(input[0], "help") && input.length == 1) {
                System.out.println("redraw - game board");
                System.out.println("leave - game view");
                System.out.println("help - with possible commands");
            } else if (Objects.equals(input[0], "redraw") && input.length == 1) {
                ws.send(tempjson);
                flag = false;
            }

            //ws.send(scanner.nextLine());
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
            public void onMessage(String message) { //this is also where we do the work
                GsonBuilder gsonbuilder = new GsonBuilder();
                gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
                Gson gson = gsonbuilder.create();
                ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);

                if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                    printer.printGame(serverMessage.getGame(), teamColor);
                    System.out.print("[GAMEVIEW] >>> ");
                }
                if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                    System.out.println(serverMessage.getMessage());
                    System.out.print("[GAMEVIEW] >>> ");
                }
                if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                    System.out.println(serverMessage.getMessage());
                    System.out.print("[GAMEVIEW] >>> ");
                }
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
