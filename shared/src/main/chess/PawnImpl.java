package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnImpl implements ChessPiece{
    ChessGame.TeamColor teamColor;

    public PawnImpl(ChessGame.TeamColor color) {
        teamColor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            whiteMove(board,myPosition,moves);
        } else {
            blackMove(board,myPosition,moves);
        }

        return moves;
    }

    private void whiteMove(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int column = myPosition.getColumn();
        int row = myPosition.getRow();

        //TODO: DOUBLE MOVE
        if (row == 2) {
            PositionImpl position = new PositionImpl(row + 1, column);
            PositionImpl position2 = new PositionImpl(row + 2, column);
            if (board.getPiece(position) == null && board.getPiece(position2) == null) {
                moveHelper(board,myPosition,position2,moves);
            }
        }

        //TODO: SINGLE MOVE
        if (row + 1 < 9){
            PositionImpl position = new PositionImpl(row + 1, column);
            if (board.getPiece(position) == null) {
                if (row + 1 < 8) {
                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                } else {
                    promotionAdder(myPosition,position,moves);
                }
            }
        }

        //TODO: CAPTURE
        if (row + 1 < 9 && column + 1 < 9) {
            PositionImpl position = new PositionImpl(row+1,column+1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (row + 1 < 8) {
                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                } else {
                    promotionAdder(myPosition,position,moves);
                }
            }
        }
        if (row + 1 < 9 && column - 1 > 0) {
            PositionImpl position = new PositionImpl(row+1,column-1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (row + 1 < 8) {
                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                } else {
                    promotionAdder(myPosition,position,moves);
                }
            }
        }
    }

    private void blackMove(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int column = myPosition.getColumn();
        int row = myPosition.getRow();

        //TODO: DOUBLE MOVE
        if (row == 7) {
            PositionImpl position = new PositionImpl(row - 1, column);
            PositionImpl position2 = new PositionImpl(row - 2, column);
            if (board.getPiece(position) == null && board.getPiece(position2) == null) {
                moveHelper(board,myPosition,position2,moves);
            }
        }

        //TODO: SINGLE MOVE
        if (row - 1 > 0){
            PositionImpl position = new PositionImpl(row - 1, column);
            if (board.getPiece(position) == null) {
                if (row - 1 > 1) {
                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                } else {
                    promotionAdder(myPosition,position,moves);
                }
            }
        }

        //TODO: CAPTURE
        if (row - 1 > 0 && column - 1 > 0) {
            PositionImpl position = new PositionImpl(row-1,column-1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (row - 1 > 1) {
                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                } else {
                    promotionAdder(myPosition,position,moves);
                }
            }
        }
        if (row - 1 > 0 && column + 1 < 9) {
            PositionImpl position = new PositionImpl(row-1,column+1);
            if (board.getPiece(position) != null && board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (row - 1 > 1) {
                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                } else {
                    promotionAdder(myPosition,position,moves);
                }
            }
        }
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

    private void promotionAdder(ChessPosition myPosition, PositionImpl position, Collection<ChessMove> moves){
        MoveImpl moveQ = new MoveImpl((PositionImpl) myPosition,position,PieceType.QUEEN);
        moves.add(moveQ);
        MoveImpl moveR = new MoveImpl((PositionImpl) myPosition,position,PieceType.ROOK);
        moves.add(moveR);
        MoveImpl moveB = new MoveImpl((PositionImpl) myPosition,position,PieceType.BISHOP);
        moves.add(moveB);
        MoveImpl moveK = new MoveImpl((PositionImpl) myPosition,position,PieceType.KNIGHT);
        moves.add(moveK);
    }
}
