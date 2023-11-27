// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import chess.*;
import com.google.gson.*;
import dataAccess.DataAccessException;
import models.Game;
import java.lang.reflect.Type;


public class Main {
    public static void main(String[] args) {

    class PieceAdapter implements JsonDeserializer<ChessPiece> {
        @Override
        public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            System.out.println("hi");
            ChessPiece chessPiece = new Gson().fromJson(jsonElement, ChessPiece.class);
            System.out.println(chessPiece.toString());
            if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
                return new Gson().fromJson(jsonElement, RookImpl.class);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                return new Gson().fromJson(jsonElement, BishopImpl.class);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                return new Gson().fromJson(jsonElement, KnightImpl.class);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                return new Gson().fromJson(jsonElement, PawnImpl.class);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
                return new Gson().fromJson(jsonElement, KingImpl.class);
            } else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                return new Gson().fromJson(jsonElement, QueenImpl.class);
            }
            else {
                System.out.println("ERROR ASSIGNING PIECETYPE WHILE DESERIALIZING PIECE");
                return null;
            }
        }
    }

    class BoardAdapter implements JsonDeserializer<ChessBoard> {
        @Override
        public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//            GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapter(ChessPiece.class, new PieceAdapter());
//            Gson gson = builder.create();
            return new Gson().fromJson(jsonElement, BoardImpl.class);
        }
    }

//    class GameAdapter implements JsonDeserializer<ChessGame> {
//        @Override
//        public ChessGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
////            GsonBuilder builder = new GsonBuilder();
////            builder.registerTypeAdapter(ChessBoard.class, new BoardAdapter());
////            Gson gson = builder.create();
//            return jsonDeserializationContext.deserialize(jsonElement, GameImpl.class);
//        }
//    }

        Game game = null;
        try {
            game = new Game("gameONE");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(game.getGame().getBoard().toString());
        String gamestring = new Gson().toJson(game.getGame());

        var builder = new GsonBuilder();
//        builder.registerTypeAdapter(GameImpl.class, new GameAdapter());
        builder.registerTypeAdapter(BoardImpl.class, new BoardAdapter());
        builder.registerTypeAdapter(ChessPiece.class, new PieceAdapter());
        Gson gson = builder.create();

        ChessGame repeatgame = gson.fromJson(gamestring, GameImpl.class);
        System.out.println(repeatgame.getBoard().toString());

//        System.out.println(repeatgame.getBoard().toString());

    }

}