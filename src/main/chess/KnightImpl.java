package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightImpl implements ChessPiece{
    ChessGame.TeamColor teamColor;

    public KnightImpl(ChessGame.TeamColor color) {
        teamColor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();

        if (row+2 < 9 && column+1 < 9) {
            PositionImpl position = new PositionImpl(row+2, column+1);
            moveHelper(board,myPosition,position,moves);
        }
        if (row+2 < 9 && column-1 > 0) {
            PositionImpl position = new PositionImpl(row+2, column-1);
            moveHelper(board,myPosition,position,moves);
        }
        if (row+1 < 9 && column+2 < 9) {
            PositionImpl position = new PositionImpl(row+1, column+2);
            moveHelper(board,myPosition,position,moves);
        }
        if (row+1 < 9 && column-2 > 0) {
            PositionImpl position = new PositionImpl(row+1, column-2);
            moveHelper(board,myPosition,position,moves);
        }
        if (row-2 > 0 && column+1 < 9) {
            PositionImpl position = new PositionImpl(row-2, column+1);
            moveHelper(board,myPosition,position,moves);
        }
        if (row-2 > 0 && column-1 > 0) {
            PositionImpl position = new PositionImpl(row-2, column-1);
            moveHelper(board,myPosition,position,moves);
        }
        if (row-1 > 0 && column+2 < 9) {
            PositionImpl position = new PositionImpl(row-1, column+2);
            moveHelper(board,myPosition,position,moves);
        }
        if (row-1 > 0 && column-2 > 0) {
            PositionImpl position = new PositionImpl(row-1, column-2);
            moveHelper(board,myPosition,position,moves);
        }

        return moves;
    }

    private void moveHelper(ChessBoard board, ChessPosition myPosition, PositionImpl position, Collection<ChessMove> moves) {
        if (board.getPiece(position) == null) {
            ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
            moves.add(move);
        } else if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
            //TODO: CAPTURE
            ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
            moves.add(move);
        }
    }
}
