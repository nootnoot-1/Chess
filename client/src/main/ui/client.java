package ui;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import models.Game;
import request.*;
import response.ListGamesResponse;
import response.RegisterResponse;

import java.util.*;

public class client {
    private static String authToken;
    public static void main(String[] args) {
        System.out.println("WELCOME TO CHESS");
        ServerFacade server = new ServerFacade("http://localhost:8080");
        loggedoutClient(server);
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

    public static Gson createGsonDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // This line should only be needed if your board class is using a Map to store chess pieces instead of a 2D array.
        gsonBuilder.enableComplexMapKeySerialization();

        gsonBuilder.registerTypeAdapter(ChessGame.class,
                (JsonDeserializer<ChessGame>) (el, type, ctx) -> ctx.deserialize(el, GameImpl.class));

        gsonBuilder.registerTypeAdapter(ChessBoard.class,
                (JsonDeserializer<ChessBoard>) (el, type, ctx) -> ctx.deserialize(el, BoardImpl.class));

        gsonBuilder.registerTypeAdapter(ChessPiece.class,
                (JsonDeserializer<ChessPiece>) (el, type, ctx) -> ctx.deserialize(el, PieceImpl.class));

        gsonBuilder.registerTypeAdapter(ChessMove.class,
                (JsonDeserializer<ChessMove>) (el, type, ctx) -> ctx.deserialize(el, MoveImpl.class));

        gsonBuilder.registerTypeAdapter(ChessPosition.class,
                (JsonDeserializer<ChessPosition>) (el, type, ctx) -> ctx.deserialize(el, PositionImpl.class));

        gsonBuilder.registerTypeAdapter(PieceImpl.class,
                (JsonDeserializer<PieceImpl>) (el, type, ctx) -> {
                    PieceImpl chessPiece = null;
                    if (el.isJsonObject()) {
                        String pieceType = el.getAsJsonObject().get("type").getAsString();
                        switch (ChessPiece.PieceType.valueOf(pieceType)) {
                            case PAWN -> chessPiece = ctx.deserialize(el, PawnImpl.class);
                            case ROOK -> chessPiece = ctx.deserialize(el, RookImpl.class);
                            case KNIGHT -> chessPiece = ctx.deserialize(el, KnightImpl.class);
                            case BISHOP -> chessPiece = ctx.deserialize(el, BishopImpl.class);
                            case QUEEN -> chessPiece = ctx.deserialize(el, QueenImpl.class);
                            case KING -> chessPiece = ctx.deserialize(el, KingImpl.class);
                        }
                    }
                    return chessPiece;
                });

        return gsonBuilder.create();
    }

    class PieceImpl implements ChessPiece {
        ChessPiece.PieceType pieceType;
        ChessGame.TeamColor teamColor;

        public PieceImpl(PieceType pieceType, ChessGame.TeamColor teamColor) {
            this.pieceType = pieceType;
            this.teamColor = teamColor;
        }

        @Override
        public ChessGame.TeamColor getTeamColor() {
            return teamColor;
        }

        @Override
        public PieceType getPieceType() {
            return pieceType;
        }

        @Override
        public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
            return null;
        }
    }

}
