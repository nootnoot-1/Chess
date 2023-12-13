package adapters;

import chess.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GameImplAdapter extends TypeAdapter<GameImpl> {

    @Override //making JSON
    public void write(JsonWriter writer, GameImpl gameimpl) throws IOException {
        //just use serialize method?
        String gamestring = gameimpl.serialize();
        writer.value(gamestring);
    }

    @Override //making object JSON
    public GameImpl read(JsonReader reader) throws IOException {
        //turn jsonreader into a string, then just use deserialize method in GAMEDAO?
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String gamestring = reader.nextString(); //maybe nextstring?
        GameImpl gameimpl = new GameImpl();
        BoardImpl board = new BoardImpl();

        if (gamestring.charAt(0) == 'W') {
            gameimpl.setTeamTurn(ChessGame.TeamColor.WHITE);
        } else {
            gameimpl.setTeamTurn(ChessGame.TeamColor.BLACK);
        }

        for (int i = 1; i < gamestring.length(); ++i) {
            PositionImpl position = new PositionImpl();
            position.setRow(gamestring.charAt(i) - 48);
            ++i;
            position.setColumn(gamestring.charAt(i) - 48);
            ++i;
            ChessPiece piece = null;
            if (gamestring.charAt(i) == 'K') {
                piece = new KingImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'Q') {
                piece = new QueenImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'B') {
                piece = new BishopImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'N') {
                piece = new KnightImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'R') {
                piece = new RookImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'P') {
                piece = new PawnImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'k') {
                piece = new KingImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'q') {
                piece = new QueenImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'b') {
                piece = new BishopImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'n') {
                piece = new KnightImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'r') {
                piece = new RookImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'p') {
                piece = new PawnImpl(ChessGame.TeamColor.BLACK);
            }
            board.addPiece(position, piece);
        }
        gameimpl.setBoard(board);
        return gameimpl;
    }
}
