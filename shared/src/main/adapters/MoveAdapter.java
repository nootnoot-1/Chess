package adapters;

import chess.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

public class MoveAdapter extends TypeAdapter<MoveImpl> {
    @Override
    public void write(JsonWriter writer, MoveImpl chessMove) throws IOException {
        StringBuilder movestring = new StringBuilder();
        movestring.append(chessMove.getStartPosition().getRow());
        movestring.append(chessMove.getStartPosition().getColumn());
        movestring.append(chessMove.getEndPosition().getRow());
        movestring.append(chessMove.getEndPosition().getColumn());
        if (chessMove.getPromotionPiece() == null) {
            movestring.append('o');
        } else if (chessMove.getPromotionPiece() == ChessPiece.PieceType.KING) {
            movestring.append('K');
        } else if (chessMove.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
            movestring.append('Q');
        } else if (chessMove.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
            movestring.append('B');
        } else if (chessMove.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
            movestring.append('N');
        } else if (chessMove.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
            movestring.append('R');
        } else if (chessMove.getPromotionPiece() == ChessPiece.PieceType.PAWN) {
            movestring.append('P');
        }
        writer.value(movestring.toString());
    }

    @Override
    public MoveImpl read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String movestring = reader.nextString();
        PositionImpl startposition = new PositionImpl(movestring.charAt(0)-48,movestring.charAt(1)-48);
        PositionImpl endposition = new PositionImpl(movestring.charAt(2)-48,movestring.charAt(3)-48);
        if (movestring.charAt(4) == 'o') {
            return new MoveImpl(startposition,endposition,null);
        } else if (movestring.charAt(4) == 'K') {
            return new MoveImpl(startposition,endposition, ChessPiece.PieceType.KING);
        } else if (movestring.charAt(4) == 'Q') {
            return new MoveImpl(startposition,endposition, ChessPiece.PieceType.QUEEN);
        } else if (movestring.charAt(4) == 'B') {
            return new MoveImpl(startposition,endposition, ChessPiece.PieceType.BISHOP);
        } else if (movestring.charAt(4) == 'N') {
            return new MoveImpl(startposition,endposition, ChessPiece.PieceType.KNIGHT);
        } else if (movestring.charAt(4) == 'R') {
            return new MoveImpl(startposition,endposition, ChessPiece.PieceType.ROOK);
        } else if (movestring.charAt(4) == 'P') {
            return new MoveImpl(startposition,endposition, ChessPiece.PieceType.PAWN);
        }
        return null;
    }
}
