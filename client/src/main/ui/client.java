package ui;

import adapters.GameImplAdapter;
import chess.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import models.Game;
import request.*;
import response.ListGamesResponse;
import response.RegisterResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class client {
    private static String authToken;
    public static void main(String[] args) {
        System.out.println("WELCOME TO CHESS");
        ServerFacade server = new ServerFacade("http://localhost:8080");
        loggedoutClient(server);

//        GameImpl gameimpl = new GameImpl();
//        BoardImpl board = new BoardImpl();
//        board.resetBoard();
//        gameimpl.setBoard(board);
//        Game game = new Game("GANA");
//
//        GsonBuilder gsonbuilder = new GsonBuilder();
//        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
//        Gson gson = gsonbuilder.create();
//
//        String json = gson.toJson(game);
//        Game newgame = gson.fromJson(json, Game.class);
    }
    private static void loggedoutClient(ServerFacade server) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[LOGGED_OUT] >>> ");
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

            else if (Objects.equals(input[0], "register") && input.length == 4) {
                RegisterRequest request = new RegisterRequest();
                request.setUsername(input[1]);
                request.setPassword(input[2]);
                request.setEmail(input[3]);
                try {
                    authToken = server.register(request).getAuthToken();
                    System.out.println("user registered and logged in");
                    loggedinClient(server);
                    break;
                } catch (ServerFacade.ResponseException e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (Objects.equals(input[0], "login") && input.length == 3) {
                LoginRequest request = new LoginRequest();
                request.setUsername(input[1]);
                request.setPassword(input[2]);
                try {
                    authToken = server.login(request).getAuthToken();
                    System.out.println("user logged in");
                    loggedinClient(server);
                    break;
                } catch (ServerFacade.ResponseException e) {
                    System.out.println(e.getMessage());
                }
            }

            else {
                System.out.println("invalid input, type \"help\" for what you can do <3");
            }
        }
    }
    private static void loggedinClient(ServerFacade server) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[LOGGED_IN] >>> ");
            String temp = scanner.nextLine();
            String[] input = parseInput(temp);

            if (Objects.equals(input[0], "quit") && input.length == 1) {
                try {
                    server.logout(authToken);
                    System.out.println("user logged out");
                    authToken = null;
                    break;
                } catch (ServerFacade.ResponseException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }

            else if (Objects.equals(input[0], "help") && input.length == 1) {
                System.out.println("join <ID> [WHITE|BLACK|<empty>] - a game");
                System.out.println("observe <ID> - a game");
                System.out.println("create <NAME> - a game");
                System.out.println("list - games");
                System.out.println("logout - when you are done");
                System.out.println("quit - playing chess");
                System.out.println("help - with possible commands");
            }

            else if (Objects.equals(input[0], "logout") && input.length == 1) {
                try {
                    server.logout(authToken);
                    System.out.println("user logged out");
                    authToken = null;
                    loggedoutClient(server);
                    break;
                } catch (ServerFacade.ResponseException e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (Objects.equals(input[0], "list") && input.length == 1) {
                try {
                    ListGamesRequest request = new ListGamesRequest();
                    request.setAuthToken(authToken);
                    ListGamesResponse response = server.listGames(request);
                    Collection<Game> games = response.getGames();
                    if (games != null) {
                        for (Game it : games) {
                            System.out.println(it.getGameName() + " " + it.getGameID());
                        }
                    }
                } catch (ServerFacade.ResponseException e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (Objects.equals(input[0], "create") && input.length == 2) {
                CreateGameRequest request = new CreateGameRequest();
                request.setGameName(input[1]);
                request.setAuthToken(authToken);
                try {
                    server.createGame(request);
                    System.out.println("game created");
                } catch (ServerFacade.ResponseException e) {
                    System.out.println(e.getMessage());
                }
            }

            else {
                System.out.println("invalid input, type \"help\" for what you can do <3");
            }
        }
    }
    private static String[] parseInput(String input) {
        String[] words = input.split("\\s+");
        return words;
    }

//    //maybe switch some GameImpl to ChessGame?
//    public static class GameImplAdapter extends TypeAdapter<GameImpl> {
//
//        @Override //making JSON
//        public void write(JsonWriter jsonWriter, GameImpl gameimpl) throws IOException {
//            //just use serialize method?
//            String gamestring = gameimpl.serialize();
//            jsonWriter.value(gamestring);
//        }
//
//        @Override //making object JSON
//        public GameImpl read(JsonReader reader) throws IOException {
//            //turn jsonreader into a string, then just use deserialize method in GAMEDAO?
//            if (reader.peek() == JsonToken.NULL) {
//                reader.nextNull();
//                return null;
//            }
//            String gamestring = reader.nextString(); //maybe nextstring?
//            GameImpl gameimpl = new GameImpl();
//            BoardImpl board = new BoardImpl();
//
//            if (gamestring.charAt(0) == 'W') {
//                gameimpl.setTeamTurn(ChessGame.TeamColor.WHITE);
//            } else {gameimpl.setTeamTurn(ChessGame.TeamColor.BLACK);}
//
//            for (int i=1; i<gamestring.length(); ++i) {
//                PositionImpl position = new PositionImpl();
//                position.setRow(gamestring.charAt(i)-48); ++i;
//                position.setColumn(gamestring.charAt(i)-48); ++i;
//                ChessPiece piece = null;
//                if (gamestring.charAt(i) == 'K') {
//                    piece = new KingImpl(ChessGame.TeamColor.WHITE);
//                } else if (gamestring.charAt(i) == 'Q') {
//                    piece = new QueenImpl(ChessGame.TeamColor.WHITE);
//                } else if (gamestring.charAt(i) == 'B') {
//                    piece = new BishopImpl(ChessGame.TeamColor.WHITE);
//                } else if (gamestring.charAt(i) == 'N') {
//                    piece = new KnightImpl(ChessGame.TeamColor.WHITE);
//                } else if (gamestring.charAt(i) == 'R') {
//                    piece = new RookImpl(ChessGame.TeamColor.WHITE);
//                } else if (gamestring.charAt(i) == 'P') {
//                    piece = new PawnImpl(ChessGame.TeamColor.WHITE);
//                } else if (gamestring.charAt(i) == 'k') {
//                    piece = new KingImpl(ChessGame.TeamColor.BLACK);
//                } else if (gamestring.charAt(i) == 'q') {
//                    piece = new QueenImpl(ChessGame.TeamColor.BLACK);
//                } else if (gamestring.charAt(i) == 'b') {
//                    piece = new BishopImpl(ChessGame.TeamColor.BLACK);
//                } else if (gamestring.charAt(i) == 'n') {
//                    piece = new KnightImpl(ChessGame.TeamColor.BLACK);
//                } else if (gamestring.charAt(i) == 'r') {
//                    piece = new RookImpl(ChessGame.TeamColor.BLACK);
//                } else if (gamestring.charAt(i) == 'p') {
//                    piece = new PawnImpl(ChessGame.TeamColor.BLACK);
//                }
//                board.addPiece(position,piece);
//            }
//            gameimpl.setBoard(board);
//            return gameimpl;
//        }
//    }

}
